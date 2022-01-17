package indi.rui.study.something.Redisson的RTopic;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.concurrent.TimeUnit;

/**
 * @author: yaowr
 * @create: 2022-01-13
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://study.rui.ubuntu:6379")
                .setDatabase(3);
        RedissonClient redissonClient = Redisson.create(config);
        RTopic rTopic = redissonClient.getTopic("test:topic:199");
        for (int i = 0; i < 10; i++) {
            final int num = i;
            new Thread(() -> {
                rTopic.addListener(String.class, (topic, msg) -> {
                    log.info("Worker {} receive message: ({}){}", num, topic, msg);
                    RLock rLock = redissonClient.getLock("test:lock");
                    while (true) {
                        try {
                            if (rLock.tryLock(5, TimeUnit.SECONDS)) {
                                log.info("Worker {} fetched lock, sleep 2 seconds!", num);
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    log.warn("interrupted!");
                                }
                                break;
                            }
                        } catch (InterruptedException e) {
                            log.warn("Worker {} continue fetching lock...", num);
                        } finally {
                            if (rLock.isLocked()) {
                                rLock.unlock();
                                log.info("Worker {} release lock!", num);
                            }
                        }
                    }
                });
            }, "worker-" + i).start();
        }
        int count = 1;
        while (true) {
            rTopic.publish("Hello world(" + count++ + ")!");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {

            }
        }
    }
}
