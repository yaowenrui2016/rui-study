package indi.rui.study.something.VerificationCode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * @author: yaowr
 * @create: 2022-12-12
 */
public class Main {


    private static Random random = new Random(System.currentTimeMillis());

    private static Set<Character> chars = new HashSet<>();

    public static String generate() {
        char[] charSeq = new char[4];
        for (int i = 0; i < charSeq.length; i++) {
            char ch = (char) random.nextInt(62);
            if (ch >= 0 && ch <= 9) {
                charSeq[i] = (char) (ch + 48);
            } else if (ch >= 10 && ch <= 35) {
                charSeq[i] = (char) (ch + 55);
            } else {
                charSeq[i] = (char) (ch + 61);
            }
            chars.add(charSeq[i]);
        }
        return new String(charSeq);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println(generate());
        }
        System.out.println(chars.size() + ":" + Arrays.toString(chars.toArray()));
    }
}
