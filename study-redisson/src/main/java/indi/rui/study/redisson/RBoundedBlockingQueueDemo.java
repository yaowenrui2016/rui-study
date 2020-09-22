package indi.rui.study.redisson;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RBoundedBlockingQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Redisson 有界阻塞队列演示
 *
 * @author: yaowr
 * @create: 2020-09-22
 */
@Slf4j
public class RBoundedBlockingQueueDemo {

    public static final int QUEUE_CAP = 100;

    static RedissonClient redisson;

    static RBoundedBlockingQueue<Integer> queue;

    static {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://study.rui.ubuntu:6379").setDatabase(0);
        redisson = Redisson.create(config);

        queue = redisson.getBoundedBlockingQueue("JobQueue");
        queue.trySetCapacity(QUEUE_CAP);
    }

    /**
     * 演示1.使用 Redisson 提供的有界阻塞队列，进行入队列与出对列操作
     * 使用 RateLimiter 控制入队列和出对列的速率，正常运行一段时间后模
     * 拟异常场景，手动将 redis 的 key 'redisson-bqs' 删除，运行中的
     * 程序立即抛出期望的异常：org.redisson.client.RedisException: ERR Error running script (call to f_d404aba30301167784efa4539108cd4cc9b948c4): @user_script:1: user_script:1: Capacity of queue redisson_bqs:{JobQueue} has not been set . channel: [id: 0xdcde841c, L:/192.168.174.1:55153 - R:study.rui.ubuntu/192.168.174.128:6379] command: (EVAL), params: [local value = redis.call('get', KEYS[1]); assert(value ~= false, 'Capacity of queue ' .. KEYS[1] .. ..., 2, redisson_bqs:{JobQueue}, JobQueue, 1, PooledUnsafeDirectByteBuf(ridx: 0, widx: 2, cap: 256)]
     *
     * @param args
     */
    public static void main(String[] args) {
        RateLimiter rateLimiter = RateLimiter.create(1);
        AtomicInteger proNum = new AtomicInteger(0);
        CompletableFuture.runAsync(() -> {
            for (; ; ) {
                rateLimiter.acquire();
                int i = proNum.getAndIncrement();
                new Thread(() -> {
                    try {
                        log.info("开始放入...{}", i);
                        queue.put(i);
                        log.info("放入成功：{}", i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, "producer-" + i).start();
            }
        });

        AtomicInteger conNum = new AtomicInteger(0);
        for (; ; ) {
            int j = conNum.getAndIncrement();
            rateLimiter.acquire();
            new Thread(() -> {
                log.info("开始取出...");
                try {
                    Integer i = queue.take();
                    log.info("取出成功：{}", i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "consumer-" + j).start();
        }
    }
}
