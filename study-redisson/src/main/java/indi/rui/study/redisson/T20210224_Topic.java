package indi.rui.study.redisson;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author: yaowr
 * @create: 2021-02-24
 */
@Slf4j
public class T20210224_Topic {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://study.rui.ubuntu:6379");
        RedissonClient redisson = Redisson.create(config);
        RTopic topic = redisson.getTopic("test-Topic");

        for (int i = 0; i < 4; i++) {
            topic.addListener(String.class, ((channel, msg) -> {
                log.info("Received: {}", msg);
            }));
        }

        for (int i = 0; i < 8; i++) {
            topic.publish("This is a message " + i);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
