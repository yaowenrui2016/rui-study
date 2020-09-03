package indi.rui.study;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @author: yaowr
 * @create: 2020-09-03
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(4);
        for (int i = 0; i < 5; i++) {
            try {
                if (semaphore.tryAcquire(2, 3, TimeUnit.SECONDS)) {

                    System.out.println(Thread.currentThread().getName() + "获取到信号!");
                } else {

                    System.out.println(Thread.currentThread().getName() + "未获取到信号~");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
