package indi.rui.study.something.nettyusage;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.util.concurrent.PromiseNotifier;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * @author: yaowr
 * @create: 2021-11-11
 */
@Slf4j
public class Client {

    private static final String URL = System.getProperty("url", "http://127.0.0.1:8848");

    public static void main(String[] args) throws Exception {
        // 解析URL
        URI uri = new URI(URL);
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
            log.error("Only HTTP(S) is supported");
            return;
        }
        // 是否是HTTPS
        final boolean ssl = "https".equalsIgnoreCase(schema);
        final SslContext sslCtx;
        if (ssl) {
            sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        EventLoopGroup group = new NioEventLoopGroup();
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
                            pipeline.addLast("ClientHandler", new ClientHandler());
                        }
                    });
            Channel ch = b.connect(host, port).sync().channel();

            // 请求内容
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername("yaowr");
            userInfo.setPassword("1234");
            ByteBuf content = Unpooled.wrappedBuffer(
                    JSONObject.toJSONString(userInfo).getBytes());

            // 完整HTTP请求
            HttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.POST,
                    "/user", content);
            request.headers()
                    .set(HttpHeaderNames.HOST, host)
                    .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE)
                    .set(HttpHeaderNames.ACCEPT_ENCODING, HttpHeaderValues.GZIP + "," + HttpHeaderValues.DEFLATE)
                    .set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ch.writeAndFlush(request).addListener(future -> {
                if (future.isSuccess()) {
                    log.info("write and flush [success]");
                } else if (future.isCancelled()) {
                    log.info("write and flush [cancelled]");
                } else if (future.isDone()) {
                    log.info("write and flush [done]");
                }
                future.addListener(new PromiseNotifier<>());
            }).sync();

            log.info("write done");

//        ch.write(new Date());
//
//        ch.write("呵呵");
//
//        List<Integer> list = Arrays.asList(1, 2, 3, 4);
//        ch.writeAndFlush(list);

            // 等待服务端关闭连接
            ch.closeFuture().sync();
        } finally {
            group.shutdownGracefully();
            log.info("Client exit");
        }
    }
}
