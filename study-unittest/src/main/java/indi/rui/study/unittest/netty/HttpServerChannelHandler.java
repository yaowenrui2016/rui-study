package indi.rui.study.unittest.netty;

import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.dto.UserInfo;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-11-15
 */
@Slf4j
public class HttpServerChannelHandler extends ChannelInboundHandlerAdapter {

    private FullHttpRequest req;

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            this.req = (FullHttpRequest) msg;
            DefaultFullHttpResponse response;
            String uri = req.uri();
            if (uri.equals("/")) {
                response = new DefaultFullHttpResponse(req.protocolVersion(), HttpResponseStatus.FOUND);
                String queryString = "user=" + URLEncoder.encode("{\"username\":\"yaowr\", \"timestamp\":" + System.currentTimeMillis() + "}", "utf-8") + "&wd={ROG}";
                String location = "/web/data?" + queryString;
                response.headers().set(HttpHeaderNames.LOCATION, location);
            } else {
                String responseStr;
                if (uri.startsWith("/web/data")) {
                    QueryStringDecoder qsd = new QueryStringDecoder(uri);
                    Map<String, List<String>> parameters = qsd.parameters();
                    List<String> users = parameters.get("user");
                    responseStr = "你好，" + Arrays.toString(users.toArray());
                } else {
                    // 请求内容
                    HttpContent content = (HttpContent) msg;
                    String body = content.content().toString(CharsetUtil.UTF_8);
                    if (body != null && body.length() > 0) {
                        UserInfo userInfo = JSONObject.parseObject(body, UserInfo.class);
                        responseStr = "你好, " + userInfo.getUserName() + "，登录成功！";
                    } else {
                        responseStr = "你好, [anonymous]登录成功！";
                    }
                }
                response = new DefaultFullHttpResponse(req.protocolVersion(), HttpResponseStatus.OK,
                        Unpooled.wrappedBuffer(responseStr.getBytes("utf-8")));
                response.headers()
                        .set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN + ";charset=utf-8")
                        .set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
            }
            // 处理响应
            boolean isKeepAlive = HttpUtil.isKeepAlive(req);
            if (isKeepAlive) {
                if (!req.protocolVersion().isKeepAliveDefault()) {
                    response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                }
            } else {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            }
            ChannelFuture f = ctx.write(response);
            if (isKeepAlive) {
                f.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("channel exception", cause);
        ctx.close();
    }
}
