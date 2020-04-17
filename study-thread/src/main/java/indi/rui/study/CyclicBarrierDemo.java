package indi.rui.study;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

import static indi.rui.study.ParkDemo.myPrint;

/**
 * @author: yaowr
 * @create: 2019-11-05
 */
public class CyclicBarrierDemo {

    private static AtomicInteger stock = new AtomicInteger(0);

    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(8);

        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                for (;;) {
                    int s = stock.get();
                    if (s % 2 == 0) {
                        if (stock.compareAndSet(s, s+1)) {
                            myPrint("生产成功-" + s);
                        }
                    } else {
                        LockSupport.park();
                    }
                }
            }, "producer-" + i).start();
        }

        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                for (;;) {
                    int s = stock.get();
                    if (s % 2 == 1) {
                        if (stock.compareAndSet(s, s+1)) {
                            myPrint("消费成功-" + s);
                        }
                    } else {
                        LockSupport.park();
                    }
                }
            }, "consumer-" + i).start();
        }

    }
}
