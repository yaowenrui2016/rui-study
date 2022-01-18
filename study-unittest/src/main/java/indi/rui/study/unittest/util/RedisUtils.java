package indi.rui.study.unittest.util;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author: yaowr
 * @create: 2022-01-17
 */
public class RedisUtils {

    public static RedissonClient getRedis(String address, int database) {
        Config config = new Config();
        config.useSingleServer().setAddress(address)
                .setDatabase(database);
        return Redisson.create(config);
    }
}
