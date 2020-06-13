package indi.rui.study.redisson.common;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.io.IOException;

/**
 * @author: yaowr
 * @create: 2020-06-13
 */
@Configuration
public class RedissonConfig {

    @Bean
    public RedissonClient redissonClient() throws IOException {
        Config config = Config.fromYAML(ClassLoader.getSystemResourceAsStream("redisson-config.yml"));
        return Redisson.create(config);
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort()));
    }
}
