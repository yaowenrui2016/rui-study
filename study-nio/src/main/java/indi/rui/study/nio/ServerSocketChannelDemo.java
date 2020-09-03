package indi.rui.study.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @author: yaowr
 * @create: 2020-09-03
 */
@Slf4j
public class ServerSocketChannelDemo {

    public static void main(String[] args) {
        Selector selector;
        try {
            selector = Selector.open();
            ServerSocketChannel server = ServerSocketChannel.open();
            server.bind(new InetSocketAddress(5678));
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);

            while (true) {
                if (selector.select(5000) == 0) {
                    log.info("没有就绪的操作~");
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                for (; iterator.hasNext(); ) {
                    SelectionKey key = iterator.next();
                    if (key.isAcceptable()) {
                        // 处理进来的连接
                        handleAccept(key);
                    } else if (key.isReadable()) {
                        // 处理读数据
                        handleRead(key);
                    } else {
                        log.info("其它就绪的操作：{}", key.interestOps());
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }

    private static void handleAccept(SelectionKey key) {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        try {
            SocketChannel socketChannel = server.accept();
            socketChannel.configureBlocking(false);
            socketChannel.register(key.selector(), SelectionKey.OP_READ);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRead(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            StringBuilder builder = new StringBuilder();
            int len;
            while ((len = channel.read(buffer)) > 0) {
                // 切换buffer为读模式
                buffer.flip();
                builder.append(new String(buffer.array(), 0, len, StandardCharsets.UTF_8));
                buffer.clear();
            }
            // 判断如果读取到数据，则进行相应处理
            if (builder.length() > 0) {
                log.info("接收到({}): {}", builder.length(), builder.toString());
                // 告知已收到
                buffer.put("您好，有什么可以帮您?".getBytes(StandardCharsets.UTF_8));
                buffer.flip();
                channel.write(buffer);
            } else {
                log.info("Channel 仍然 readable...");
                channel.close();
            }
        } catch (IOException e) {
            log.error("读失败~", e);
            try {
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
