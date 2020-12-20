package indi.rui.study.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.CyclicBarrier;

/**
 * @author: yaowr
 * @create: 2020-12-18
 */
@Slf4j
public class T20201218_SocketDemo {

    private static final String[] requests = new String[]{
            "Hello, I am UserA, Coffee please: Java中的引用的定义很传统：如果reference类型的数据中存储的数值代表的是另外一块内存的起始地址，就称这块内存代表着一个引用。 这种定义很纯粹，但是太过狭隘，一个对象在这种定义下只有被引用或者没有被引用两种状态，对于如何描述一些“食之无味，弃之可惜”的对象就显得无能为力。 我们希望能描述这样一类对象：当内存空间还足够时，则能保留在内存之中；如果内存空间在进行垃圾收集后还是非常紧张，则可以抛弃这些对象。 很多系统的缓存功能都符合这样的应用场景。",
            "Another Message: 强引用是使用最普遍的引用。如果一个对象具有强引用，那垃圾收器绝不会回收它。当内存空间不足，Java虚拟机宁愿抛出OutOfM moryError错误，使程序异常终止，也不会靠随意回收具有强引用 对象来解决内存不足的问题。"
    };

    public static void main(String[] args) throws IOException {
        int count = 20;
        CyclicBarrier barrier = new CyclicBarrier(count);
        for (int i = 0; i < count; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                    // 连接服务端Socket
                    Socket socket = new Socket((String) null, 8888);
                    OutputStream out = socket.getOutputStream();
                    InputStream in = socket.getInputStream();
                    for (int j = 0; j < requests.length; j++) {
                        // 延迟1秒
                        Util.delay(1);
                        // 写入请求
                        Util.write(out, requests[j]);
                        log.info("Send Request {}: {}", j + 1, requests[j]);
                        // 读取响应
                        String response = Util.read(in);
                        log.info("Receive Response: {}", response);
                    }
                    socket.close();
                } catch (Exception e) {
                    log.error("请求服务端错误", e);
                }
            }).start();
        }
    }
}
