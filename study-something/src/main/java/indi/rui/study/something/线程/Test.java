package indi.rui.study.something.线程;

/**
 * @author: yaowr
 * @create: 2022-11-18
 */
public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            Thread t = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                }
            }, "my-thread-" + i);
            t.setDaemon(true);
            t.start();
        }
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        int total = Thread.activeCount();
        Thread[] threads = new Thread[total];
        Thread.currentThread().getThreadGroup().enumerate(threads);
        for (int i = 0; i < threads.length; i++) {
            System.out.println("线程" + threads[i].getId() + "[" + threads[i].getName() + "]在运行");
        }
    }
}
