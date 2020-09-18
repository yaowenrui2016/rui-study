package indi.rui.study;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * 演示Completable的基本使用
 *
 * @author: yaowr
 * @create: 2020-09-18
 */
@Slf4j
public class CompletableDemo {

    static final AtomicInteger threadNum = new AtomicInteger(0);

    static ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 0
            , TimeUnit.SECONDS, new LinkedBlockingQueue<>(4), (r) -> new Thread(r, "my-executor-" + threadNum.getAndIncrement())
            , (r, executor) -> {
        try {
            executor.getQueue().offer(r, 5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    });

    /**
     * 工作者数量
     */
    static final int WORKER_AMOUNT = 4;

    /**
     * 任务数量
     */
    static final int TASK_AMOUNT = 4;

    static Random random = new Random(System.currentTimeMillis());


    public static void main(String[] args) {
//        test1();
//        test2();
//        test3();
        test4();
    }

    /**
     * 演示1.使用runAsync返回的future也能实现阻塞当前线程
     */
    private static void test1() {
        log.info("开始异步执行...");
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            log.info("执行任务中... [耗时3秒]");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("任务执行完毕！");
        }, executor);
        try {
            log.info("获取执行结果...");
            CompletableFuture.allOf(future).get();
            future.get(4, TimeUnit.SECONDS);
            log.info("获取执行结果成功!");
        } catch (InterruptedException e) {
            log.error("获取执行结果[被打断]", e);
        } catch (ExecutionException e) {
            log.error("获取执行结果[执行异常]", e);
        } catch (TimeoutException e) {
            log.error("获取执行结果[超时]", e);
        }
    }

    /**
     * 演示2.多个runAsync返回的future阻塞当前线程
     * allOf: 阻塞当前线程，直到所有线程执行完才返回
     * anyOf: 阻塞当前线程，直到任意线程执行完就返回
     */
    private static void test2() {
        ArrayList<CompletableFuture> futures = new ArrayList<>(WORKER_AMOUNT);
        for (int i = 0; i < WORKER_AMOUNT; i++) {
            log.info("任务{}开始异步执行...", i);
            CompletableFuture<Void> future = CompletableFuture.runAsync(new Worker(i), executor);
            futures.add(future);
        }
        try {
            log.info("获取执行结果...");
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get(10, TimeUnit.SECONDS);
            log.info("获取执行结果成功!");
        } catch (InterruptedException e) {
            log.error("获取执行结果[被打断]", e);
        } catch (ExecutionException e) {
            log.error("获取执行结果[执行异常]", e);
        } catch (TimeoutException e) {
            log.error("获取执行结果[超时]", e);
        }
    }

    static class Worker implements Runnable {
        int num;

        public Worker(int num) {
            this.num = num;
        }

        @Override
        public void run() {
            int spend = random.nextInt(4);
            log.info("任务{}执行任务中... [耗时{}秒]", num, spend);
            try {
                Thread.sleep(spend * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("任务{}执行完毕！", num);
        }
    }

    /**
     * 演示3.使用anyOf获取最先执行完的结果
     */
    private static void test3() {
        CompletableFuture[] futures = new CompletableFuture[TASK_AMOUNT];
        for (int i = 0; i < TASK_AMOUNT; i++) {
            log.info("任务{}开始异步执行...", i);
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Task(i), executor);
            futures[i] = (future);
        }
        try {
            log.info("获取执行结果...");
            int result = (Integer) CompletableFuture.anyOf(futures).get(10, TimeUnit.SECONDS);
            log.info("获取执行结果成功! 结果：{}", result);
        } catch (InterruptedException e) {
            log.error("获取执行结果[被打断]", e);
        } catch (ExecutionException e) {
            log.error("获取执行结果[执行异常]", e);
        } catch (TimeoutException e) {
            log.error("获取执行结果[超时]", e);
        }
    }

    /**
     * 演示4.使用whenComplete注册回调函数
     */
    private static void test4() {
        CompletableFuture[] futures = new CompletableFuture[TASK_AMOUNT];
        for (int i = 0; i < TASK_AMOUNT; i++) {
            log.info("任务{}开始异步执行...", i);
            CompletableFuture<Integer> future = CompletableFuture.supplyAsync(new Task(i), executor);
            future.whenComplete((result, exception) -> {
                log.info("获取执行结果成功! 结果：{}", result);
            });
            futures[i] = (future);
        }
        /*
         * allOf的whenComplete回调函数的result为null
         * anyOf的whenComplete回掉函数的result为最先执行完的线程
         */
//        CompletableFuture.anyOf(futures).whenComplete((r, exception) -> {
//            log.info("获取执行结果成功! 结果：{}", r);
//        });
    }


    static class Task implements Supplier<Integer> {
        int num;

        Task(int num) {
            this.num = num;
        }

        @Override
        public Integer get() {
            int spend = random.nextInt(4);
            log.info("任务{}执行任务中... [耗时{}秒]", num, spend);
            try {
                Thread.sleep(spend * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("任务{}执行完毕！", num);
            return num;
        }
    }
}
