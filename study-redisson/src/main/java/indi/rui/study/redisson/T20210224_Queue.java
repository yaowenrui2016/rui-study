package indi.rui.study.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.ExpiredObjectListener;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @author: yaowr
 * @create: 2021-02-24
 */
@Slf4j
public class T20210224_Queue {

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://study.rui.ubuntu:6379");
        RedissonClient redisson = Redisson.create(config);
        RBlockingQueue rQueue = redisson.getBlockingQueue("test-queue");
        rQueue.expire(10, TimeUnit.SECONDS);
        new Thread(() -> {
            for (int i = 0; i < 8; i++) {
                rQueue.add("This is a message " + i);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "producer").start();
        new Thread(() -> {
            rQueue.addListener((ExpiredObjectListener) name -> {
                log.info("过期了: {}", name);
            });
        }, "listener").start();
        new Thread(() -> {
            Object msg = null;
            for (; ; ) {
                try {
                    msg = rQueue.poll(2, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("Received: {}", msg);
            }
        }, "consumer-1").start();
        new Thread(() -> {
            Object msg = null;
            for (; ; ) {
                try {
                    msg = rQueue.poll(2, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("Received: {}", msg);
            }
        }, "consumer-2").start();
    }

}
