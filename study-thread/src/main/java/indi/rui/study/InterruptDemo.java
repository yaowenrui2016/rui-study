package indi.rui.study;

/**
 * interrupt要点总结：打断信号一旦被catch后将重置为false
 * @author: yaowr
 * @create: 2019-11-05
 */
public class InterruptDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            int count = 0;
            for (;;) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println("["+Thread.currentThread().getName()+"] Interrupted：" + Thread.currentThread().isInterrupted() + "------catch");
                    break;
                }
                System.out.println("["+Thread.currentThread().getName()+"] Interrupted：" + Thread.currentThread().isInterrupted() + "------" + (++count));
            }
            System.out.println("["+Thread.currentThread().getName()+"] Interrupted：" + Thread.currentThread().isInterrupted() + "------end");
        }, "t1");

        Thread t2 = new Thread(() -> {
            try {
                t1.join();
            } catch (InterruptedException e) {
                System.out.println("["+Thread.currentThread().getName()+"] Interrupted：" + Thread.currentThread().isInterrupted() + "------catch");
            }
            t1.interrupt();
            System.out.println("["+Thread.currentThread().getName()+"] Interrupted：" + Thread.currentThread().isInterrupted() + "------end");
        }, "t2");

        Thread t3 = new Thread(() -> {
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                System.out.println("["+Thread.currentThread().getName()+"] Interrupted：" + Thread.currentThread().isInterrupted() + "------catch");
            }
            t2.interrupt();
            System.out.println("["+Thread.currentThread().getName()+"] Interrupted：" + Thread.currentThread().isInterrupted() + "------end");
        }, "t3");

        t1.start();
        t2.start();
        t3.start();

        System.out.println("["+Thread.currentThread().getName()+"] Interrupted：" + Thread.currentThread().isInterrupted() + "------end");
    }
}
