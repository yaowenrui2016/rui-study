package indi.rui.study.common.conf;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.io.IOException;

/**
 * Redis配置
 *
 * @author: yaowr
 * @create: 2020-01-31
 */
@Configuration
public class RedisConfig {

    @Bean
    @ConditionalOnProperty(name = "spring.redis")
    @ConditionalOnMissingBean(RedisConnectionFactory.class)
    public LettuceConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort()));
    }

    @Bean
    @ConditionalOnBean(RedisConnectionFactory.class)
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redissonClient() throws IOException {
        return Redisson.create();
    }
}
