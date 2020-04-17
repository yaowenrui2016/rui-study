package indi.rui.study.redisson;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

/**
 * @author: yaowr
 * @create: 2020-03-03
 */
@Component
public class AppBeanAware implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("redisTemplate")) {
            RedisTemplate redisTemplate = (RedisTemplate)bean;
            redisTemplate.setKeySerializer(RedisSerializer.string());
            redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        }
        return bean;
    }
}
