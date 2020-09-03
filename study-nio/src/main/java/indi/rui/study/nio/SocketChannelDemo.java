package indi.rui.study.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

/**
 * @author: yaowr
 * @create: 2020-09-03
 */
@Slf4j
public class SocketChannelDemo {
    public static void main(String[] args) {
        SocketChannel client;
        try {
            Selector selector = Selector.open();
            client = SocketChannel.open(new InetSocketAddress("127.0.0.1", 5678));
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);

            ByteBuffer buffer = ByteBuffer.allocate(1024);
            buffer.put("你好啊，我是客户端!".getBytes(StandardCharsets.UTF_8));
            // 切换读模式
            buffer.flip();
            client.write(buffer);
            while (true) {
                if (selector.select(5000) == 0) {
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isReadable()) {
                        handleRead(selectionKey);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleRead(SelectionKey selectionKey) {
        SocketChannel channel;
        try {
            channel = (SocketChannel) selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            StringBuilder builder = new StringBuilder();
            int len;
            while ((len = channel.read(buffer)) > 0) {
                // 切换buffer为读模式
                buffer.flip();
                builder.append(new String(buffer.array(), 0, len, StandardCharsets.UTF_8));
            }
            // 判断如果读取到数据，则进行相应处理
            if (builder.length() > 0) {
                // 读取数据
                log.info("服务端响应({})：{}", builder.length(), builder.toString());
//                // 关闭通道
//                channel.close();
//                log.info("关闭通道");
            } else {
                log.info("Channel 仍然 readable...");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
