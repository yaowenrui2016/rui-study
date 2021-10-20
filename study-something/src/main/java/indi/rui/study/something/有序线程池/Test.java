package indi.rui.study.something.有序线程池;

import indi.rui.study.something.ThreadHelper;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: yaowr
 * @create: 2021-10-19
 */
public class Test {

    private ThreadPoolExecutor normalPool = new ThreadPoolExecutor(
            16, 16,
            0, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(1000), new ThreadPoolExecutor.AbortPolicy());

    // 初始化有序线程池
    private SequentialThreadPool<Integer> seqPool = new SequentialThreadPool<>(normalPool);

    private ConcurrentMap<Integer, Integer> checkMap = new ConcurrentHashMap<>();
    private AtomicInteger count = new AtomicInteger(0);

    public void process() {
        int taskNum = 1000;
        for (int i = 0; i < taskNum; i++) {
            int key = i % 8;
            int value = i;
            // 使用有序线程池执行任务
            seqPool.executeSerial(key, () -> {
                doRun(key, value);
            });
            // 使用普通线程池执行任务
//            normalPool.execute(() -> {
//                doRun(key, value);
//            });
        }
        // 启动监控线程监控有序线程池的当前并发任务数
        new Thread(() -> {
            while (true) {
                ThreadHelper.sleep(3000);
                seqPool.monitor();
            }
        }).start();
        // 查看处理完所有任务的耗时
        long beginTime = System.currentTimeMillis();
        boolean finish = false;
        while (true) {
            int result = count.get();
            if (!finish && result == taskNum) {
                finish = true;
                long endTime = System.currentTimeMillis();
                System.out.println("count: " + count.get() + ", spendTime: " + (endTime - beginTime));
            }
        }
    }

    /**
     * 模拟执行任务，比对处理过程是否有序
     *
     * @param key
     * @param value
     */
    private void doRun(int key, int value) {
        ThreadHelper.betweenSleep(20, 70);
        Integer previous = checkMap.put(key, value);
        if (previous != null && value < previous) {
            System.out.println("error: " + key + "_" + value);
        }
        count.incrementAndGet();
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.process();
    }
}
