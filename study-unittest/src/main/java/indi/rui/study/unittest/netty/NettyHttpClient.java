package indi.rui.study.unittest.netty;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

import java.net.URI;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
public class NettyHttpClient {

    public static HttpResult post(String url, JSON body) throws Exception {
        // 解析URL
        URI uri = new URI(url);
        String schema = uri.getScheme() == null ? "http" : uri.getScheme();
        String host = uri.getHost() == null ? "127.0.0.1" : uri.getHost();
        int port = uri.getPort();
        if (port == -1) {
            if ("http".equalsIgnoreCase(schema)) {
                port = 80;
            } else if ("https".equalsIgnoreCase(schema)) {
                port = 443;
            }
        }
        if (!"http".equalsIgnoreCase(schema) && !"https".equalsIgnoreCase(schema)) {
            throw new RuntimeException("Only HTTP(S) is supported");
        }
        // 是否是HTTPS
        final boolean ssl = "https".equalsIgnoreCase(schema);
        final SslContext sslCtx;
        if (ssl) {
            sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }
        // 执行结果
        HttpResult result = new HttpResult();
        // 连接服务
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            if (sslCtx != null) {
                                pipeline.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            pipeline.addLast(new HttpClientCodec());
                            pipeline.addLast(new HttpChannelHandler(result));
                        }
                    });
            Channel ch = b.connect(host, port).sync().channel();
            // 发起请求
            ByteBuf content = Unpooled.wrappedBuffer(body.toJSONString().getBytes());
            HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
                    uri.getRawPath(), content);
            request.headers()
                    .set(HttpHeaderNames.HOST, host)
                    .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE)
                    .set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP + "," + HttpHeaderValues.DEFLATE)
                    .set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
            ch.writeAndFlush(request);

            // 等待channel关闭
            ch.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
        return result;
    }
}
