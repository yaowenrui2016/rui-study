package indi.rui.study.something;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

/**
 * @author: yaowr
 * @create: 2021-10-18
 */
@Slf4j
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
            log.error("sleep exception", e);
        }
    }

    public static void TimedRun(Runnable r, int millis) {
        while (true) {
            sleep(millis);
            r.run();
        }
    }
}
