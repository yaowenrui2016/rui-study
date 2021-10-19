package indi.rui.study.something.细粒度锁;

import java.util.Random;

/**
 * @author: yaowr
 * @create: 2021-10-19
 */
public class Test {

    private Random random = new Random(System.currentTimeMillis());

    private WeakHashLock lock = new WeakHashLock();

    public void process() {
        new Thread(() -> {

        }).start();
    }

    private void before() {
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
