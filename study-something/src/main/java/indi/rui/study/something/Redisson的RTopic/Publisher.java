package indi.rui.study.something.Redisson的RTopic;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author: yaowr
 * @create: 2022-01-17
 */
@Slf4j
public class Publisher {
    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://10.10.2.78:6379")
                .setPassword("password")
                .setDatabase(3);
        RedissonClient redissonClient = Redisson.create(config);
        RTopic rTopic = redissonClient.getTopic("test:topic:199");

        while (true) {
            try {
                rTopic.publish("Hello world!" + System.currentTimeMillis());
                log.info("send success");
//                Thread.sleep(50);
            } catch (Throwable throwable) {
            }
        }
    }
}
