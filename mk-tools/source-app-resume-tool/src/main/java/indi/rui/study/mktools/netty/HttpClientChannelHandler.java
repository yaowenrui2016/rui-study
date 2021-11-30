package indi.rui.study.mktools.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class HttpClientChannelHandler extends ChannelInboundHandlerAdapter {

    private HttpResult result;

    private StringBuffer buf = new StringBuffer();

    public HttpClientChannelHandler(HttpResult result) {
        this.result = result;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        result.setContent(buf.toString());
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse res = (HttpResponse) msg;
            result.setVersion(res.protocolVersion().text());
            result.setStatus(res.status().code());
            if (!res.headers().isEmpty()) {
                result.setHeaders(new HashMap<>());
                for (String headerName : res.headers().names()) {
                    result.getHeaders().put(headerName, res.headers().getAll(headerName));
                }
            }
        } else if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            buf.append(content.content().toString(CharsetUtil.UTF_8));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("{} error", ctx.name(), cause);
        ctx.close();
    }
}
