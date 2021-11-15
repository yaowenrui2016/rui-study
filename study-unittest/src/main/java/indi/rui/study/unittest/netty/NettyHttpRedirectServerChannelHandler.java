package indi.rui.study.unittest.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
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
public class NettyHttpRedirectServerChannelHandler extends ChannelInboundHandlerAdapter {

    private HttpRequest req;

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            req = (HttpRequest) msg;
            DefaultFullHttpResponse response;
            String uri = req.uri();
            if (!uri.startsWith("/web/data")) {
                response = new DefaultFullHttpResponse(req.protocolVersion(), HttpResponseStatus.FOUND);
                String queryString = "user=" + URLEncoder.encode("{\"username\":\"yaowr\", \"timestamp\":" + System.currentTimeMillis() + "}", "utf-8") + "&wd={ROG}";
                String location = "/web/data?" + queryString;
                response.headers().set(HttpHeaderNames.LOCATION, location);
            } else {
                QueryStringDecoder qsd = new QueryStringDecoder(uri);
                Map<String, List<String>> parameters = qsd.parameters();
                List<String> users = parameters.get("user");
                ByteBuf buf = Unpooled.wrappedBuffer(("你好，" + Arrays.toString(users.toArray())).getBytes("utf-8"));
                response = new DefaultFullHttpResponse(req.protocolVersion(), HttpResponseStatus.OK, buf);
                response.headers().set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN + ";charset=utf-8");
                response.headers().set(HttpHeaderNames.CONTENT_LENGTH, buf.readableBytes());
            }
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
            ctx.write(response);
        } else if (msg instanceof HttpContent) {
            log.info("content is coming");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("channel exception", cause);
        ctx.close();
    }
}
