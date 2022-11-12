package indi.rui.study.socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author: yaowr
 * @create: 2022-10-30
 */
public class Client {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("127.0.0.1", 8080));
        OutputStream out = socket.getOutputStream();
        out.write("hello world\r\n".getBytes());
        out.write("\r\n".getBytes());
        out.flush();
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
        socket.close();
    }
}
