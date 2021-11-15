package indi.rui.study.something.nettyusage;

import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import static indi.rui.study.something.nettyusage.HttpNettyUtils.headsToString;

/**
 * @author: yaowr
 * @create: 2021-11-11
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private HttpRequest req;

    private StringBuffer buf = new StringBuffer();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            this.req = (HttpRequest) msg;
            // 请求信息
            buf.append(req.protocolVersion()).append(" ")
                    .append(req.method()).append(" ").append(req.uri()).append("\n");
            if (!req.headers().isEmpty()) {
                for (String headerName : req.headers().names()) {
                    buf.append(headerName).append(": ")
                            .append(headsToString(req.headers().getAll(headerName)))
                            .append("\n");
                }
            }
        } else if (msg instanceof HttpContent) {
            // 请求内容
            HttpContent content = (HttpContent) msg;
            String body = content.content().toString(CharsetUtil.UTF_8);
            buf.append(body);
            UserInfo userInfo = JSONObject.parseObject(body, UserInfo.class);
            String responseStr = "你好, " + userInfo.getUsername() + "，登录成功！";
            // 处理响应
            boolean isKeepAlive = HttpUtil.isKeepAlive(req);
            FullHttpResponse response = new DefaultFullHttpResponse(req.protocolVersion(), HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(responseStr.getBytes("utf-8")));

            response.headers()
                    .set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN)
                    .set(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());

            if (isKeepAlive) {
                if (!req.protocolVersion().isKeepAliveDefault()) {
                    response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                }
            } else {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            }

            ChannelFuture f = ctx.write(response);

            log.info("request: \n{}", buf.toString());
            if (isKeepAlive) {
                f.addListener(ChannelFutureListener.CLOSE);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("出错了", cause);
        ctx.close();
    }
}
