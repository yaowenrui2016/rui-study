package indi.rui.study.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * @author: yaowr
 * @create: 2020-12-18
 */
@Slf4j
public class Util {

    public static void writeRequest(OutputStream out) throws IOException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            String input = sc.next() + "\n";
            out.write(input.getBytes());
            out.flush();
        }
    }

    public static void delay(int seconds) {
        try {
            for (int i = 0; i < seconds; i++) {
                Thread.sleep(1000);
                log.info("Delayed {} sec", i + 1);
            }
        } catch (InterruptedException e) {
            log.warn("Interrupted", e);
        }
    }

    public static String read(InputStream in) throws IOException {
        int fir = in.read();
        if (fir == -1) {
            return null;
        }
        int sec = in.read();
        int len = (fir << 8) + sec;
        log.info("Binary String for len={}, is: {}", len, Integer.toBinaryString(len));
        byte[] buf = new byte[len];
        in.read(buf);
        return new String(buf, "utf-8");
    }

    public static void write(OutputStream out, String content) throws IOException {
        byte[] data1 = content.getBytes(Charset.forName("utf8"));
        byte[] length = Util.getLengthBytes(data1.length);
        out.write(length);
        out.write(data1);
        out.flush();
    }

    public static byte[] getLengthBytes(int len) {
        log.info("Binary String for len={}, is: {}", len, Integer.toBinaryString(len));
        byte[] length = new byte[2];
        length[1] = (byte) (len & 0xff);
        length[0] = (byte) ((len >> 8) & 0xff);
        log.info("Binary String for 'length byte[]' is: [{}, {}]", Integer.toBinaryString(length[0]), Integer.toBinaryString(length[1]));
        return length;
    }

}
