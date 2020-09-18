package indi.rui.study;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 同步队列演示
 *
 * @author: yaowr
 * @create: 2020-09-17
 */
@Slf4j
public class SynchronizeQueueDemo {

    private static final SynchronousQueue<Integer> queue = new SynchronousQueue(true);

    public static void main(String[] args) {
        // 演示1.单个生产者和单个消费者先后到达情形
//        test1();
        // 演示2.多个生产者和多个消费者的同时放入并拉取的情况
//        test2();
        // 演示3.使用同步队列的线程池
        test3();
    }

    /**
     * 演示1.单个生产者和单个消费者先后到达情形
     * 其中生产者调用阻塞地放入方法，直到有相应的消费者拉取，否则生产者一直阻塞
     * 同样的，消费者调用阻塞地拉取方法，直到有相应的生产者放入，否则消费者一直阻塞
     */
    private static void test2() {
        new Thread(() -> {
            try {
                log.info("开始放入...");
                Integer e = 1;
                queue.put(e);
                log.info("放入 {}", e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "producer").start();

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                log.info("开始拉取...");
                Integer e = queue.take();
                log.info("拉取 {}", e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "consumer").start();
    }

    /**
     * 演示2.多个生产者和多个消费者的同时放入并拉取的情况
     */
    private static void test1() {
        CyclicBarrier barrier = new CyclicBarrier(4, () -> {
            log.info("Barrier Action Do Something...");
        });

        AtomicInteger num = new AtomicInteger(0);

        new Thread(new Producer(barrier, num), "producer-1").start();
        new Thread(new Producer(barrier, num), "producer-2").start();
        new Thread(new Consumer(barrier), "consumer-1").start();
        new Thread(new Consumer(barrier), "consumer-2").start();
    }

    @Slf4j
    public static class Producer implements Runnable {

        private CyclicBarrier barrier;
        private AtomicInteger num;

        public Producer(CyclicBarrier barrier, AtomicInteger num) {
            this.barrier = barrier;
            this.num = num;
        }

        @Override
        public void run() {
            try {
                barrier.await();
                log.info("开始入队列...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 4; i++) {
                try {
                    Integer obj = num.getAndIncrement();
                    queue.put(obj);
                    log.info("put {}", obj);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Slf4j
    public static class Consumer implements Runnable {

        private CyclicBarrier barrier;

        public Consumer(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                barrier.await();
                log.info("开始拉取...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 4; i++) {
                try {
                    Integer e = queue.take();
                    log.info("take {}", e);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 同步队列线程池，线程池满的拒绝策略是阻塞当前线程继续放入队列
     */
    private static ThreadPoolExecutor syncQueueExecutor = new ThreadPoolExecutor(1, 1
            , 0, TimeUnit.SECONDS, new SynchronousQueue<>(true)
            , (r) -> new Thread(r, "kafka-executor"), (r, executor) -> {
        try {
            executor.getQueue().put(r);
        } catch (InterruptedException e) {
            log.error("队列满阻塞放入时被打断了~", e);
        }
    });

    /**
     * 演示3.使用同步队列的线程池
     */
    private static void test3() {
        log.info("开始放入第1个任务...");
        syncQueueExecutor.execute(new Task(1));
        log.info("开始放入第2个任务...");
        syncQueueExecutor.execute(new Task(2));
        log.info("开始放入第3个任务...");
        syncQueueExecutor.execute(new Task(3));
        log.info("开始放入第4个任务...");
        syncQueueExecutor.execute(new Task(4));
        log.info("任务已完成!");
    }

    private static class Task implements Runnable {

        int num;

        public Task(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            log.info("执行任务{}... [耗时3秒]", num);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("任务{}执行完比！", num);
        }
    }
}
