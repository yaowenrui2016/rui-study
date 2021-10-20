package indi.rui.study.something;

import java.util.Random;

/**
 * @author: yaowr
 * @create: 2021-10-18
 */
public class ThreadHelper {
    private static Random random = new Random(System.currentTimeMillis());

    public static void randSleep(int bound) {
        sleep(random.nextInt(bound));
    }

    public static void betweenSleep(int min, int max) {
        sleep(random.nextInt(max - min) + min);
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
