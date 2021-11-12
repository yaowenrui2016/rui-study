package indi.rui.study.something.nettyusage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

import static indi.rui.study.something.nettyusage.HttpNettyUtils.headsToString;

/**
 * @author: yaowr
 * @create: 2021-11-11
 */
@Slf4j
public class ClientHandler extends ChannelInboundHandlerAdapter {


    private StringBuffer buf = new StringBuffer();

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
        log.info("{} read complete: \n{}", ctx.name(), buf.toString());
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse res = (HttpResponse) msg;
            buf.append(res.protocolVersion()).append(" ").append(res.status()).append("\n");
            if (!res.headers().isEmpty()) {
                for (String headerName : res.headers().names()) {
                    buf.append(headerName).append(": ")
                            .append(headsToString(res.headers().getAll(headerName)))
                            .append("\n");
                }
            }
        } else if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            buf.append("\n").append(content.content().toString(CharsetUtil.UTF_8));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("出错了", cause);
        ctx.close();
    }
}
