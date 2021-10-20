package indi.rui.study.something.细粒度锁;

import indi.rui.study.something.ThreadHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: yaowr
 * @create: 2021-10-19
 */
public class Test {

    private WeakHashLock<Integer> hashLock = new WeakHashLock<>();

    private List<Integer> valueList;

    public Test() {
        valueList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            valueList.add(i, 0);
        }
    }

    public void process() {
        List<Thread> threads = new ArrayList<>();
        // 启动线程
        for (int i = 0; i < 1000; i++) {
            // 为每个worker设置key的值
            Thread thread = new Thread(new Worker(i % valueList.size()));
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (Integer value : valueList) {
            System.out.println(value);
        }
    }

    private class Worker implements Runnable {
        private int key;

        Worker(int key) {
            this.key = key;
        }

        @Override
        public void run() {
            // 加锁：哈希锁
            ReentrantLock lock = hashLock.getLock(key);
            lock.lock();
            try {
                // 模拟并发访问int变量，将value递增
                int tmp = valueList.get(key);

                ThreadHelper.randSleep(2);
                valueList.set(key, tmp + 1);
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.process();
    }
}
