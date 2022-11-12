package indi.rui.study.socket;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author: yaowr
 * @create: 2022-10-30
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        ExecutorService executors = new ThreadPoolExecutor(8, 8,
                0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000),
                new ThreadPoolExecutor.DiscardPolicy());
        while (true) {
            Socket socket = serverSocket.accept();
            executors.execute(() -> {
                try {
                    System.out.println(socket.toString());
                    InputStream in = socket.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    String line;
                    int count = 0;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(count++ + ":" + line);
                        if (line.equals("")) {
                            break;
                        }
                    }
                    System.out.println("-----");
                    OutputStream out = socket.getOutputStream();
                    out.write("HTTP/1.1 200 OK\r\nContent-type:tex/plain;charset=utf-8\r\n\r\n".getBytes());
                    out.flush();
//                    out.close();
//                    socket.close();
                } catch (IOException e) {
                    throw new RuntimeException("服务异常");
                }
            });
        }
    }
}
