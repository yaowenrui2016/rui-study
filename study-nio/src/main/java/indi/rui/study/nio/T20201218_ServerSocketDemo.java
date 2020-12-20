package indi.rui.study.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: yaowr
 * @create: 2020-12-18
 */
@Slf4j
public class T20201218_ServerSocketDemo {

    private static AtomicInteger threadIndex = new AtomicInteger();

    private static ThreadPoolExecutor threadPool = new ThreadPoolExecutor(4, 4,
            0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10),
            (r) -> new Thread(r, "Server-" + threadIndex.getAndIncrement()),
            new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        while (true) {
            log.info("接入中...");
            Socket socket = serverSocket.accept();
            String hostAndPort = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();
            log.info("接入新连接: {}", hostAndPort);
            threadPool.execute(() -> {
                for (int num = 1; ; num++) {
                    try {
                        if (socket.isClosed()) {
                            log.info("Socket[{}] 已关闭",  hostAndPort);
                            break;
                        }
                        // 读取请求
                        InputStream in = socket.getInputStream();
                        String request = Util.read(in);
                        if (request == null) {
                            break;
                        }
                        log.info("Receive Request {}: {}", num, request);
                        // 模拟请求处理过程，延迟2秒
                        Util.delay(2);
                        // 写入响应
                        OutputStream out = socket.getOutputStream();
                        String response = "我是服务端，已收到你的请求，这是响应结果_" + num;
                        Util.write(out, response);
                        log.info("Send Response: {}", response);
                    } catch (IOException e) {
                        log.error("服务端处理错误", e);
                    }
                }
            });
        }
    }
}
