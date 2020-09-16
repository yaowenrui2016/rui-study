package indi.rui.study.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.redisson.Redisson;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static indi.rui.study.kafka.Constant.KAFKA_BOOTSTRAP_SERVER;

/**
 * @author: yaowr
 * @create: 2020-09-01
 */
@Slf4j
public class KafkaProducerStarter {

    public static final Properties PROPS = new Properties();
    public static final RedissonClient REDISSON;

    static {

        PROPS.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVER);
        PROPS.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        PROPS.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        PROPS.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        PROPS.put(ProducerConfig.ACKS_CONFIG, "all");
        PROPS.put(ProducerConfig.RETRIES_CONFIG, 1);
    }

    static {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://study.rui.ubuntu:6379")
                .setDatabase(0);
        REDISSON = Redisson.create(config);
    }

    public static void main(String[] args) {
        RRateLimiter limiter = REDISSON.getRateLimiter("rui-study:study-kafka:RateLimiter");
        if (!limiter.delete()) {
            log.warn("[RRateLimiter] delete() 失败~");
        } else {
            log.info("[RRateLimiter] delete() 成功！");
        }
        if (!limiter.trySetRate(RateType.OVERALL, 1, 1, RateIntervalUnit.SECONDS)) {
            log.warn("[RRateLimiter] setRate() 失败~");
        } else {
            log.info("[RRateLimiter] setRate() 成功！");
        }
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(PROPS);
        for (int i = 0; i < 10; i++) {
            if (!limiter.tryAcquire(3, TimeUnit.SECONDS)) {
                continue;
            }
            String message = "THIS IS A TEST MESSAGE(" + i + ")!";
            ProducerRecord<String, String> record = new ProducerRecord<>("yao_test_3",
                    0, "testKey", message);
            Future<RecordMetadata> future = kafkaProducer.send(record);
            try {
                RecordMetadata recordMetadata = future.get(200, TimeUnit.MILLISECONDS);
                log.info("发送Kafka消息成功: {}", record);
            } catch (InterruptedException e) {
                log.error("发送Kafka消息时，线程被打断...");
            } catch (ExecutionException e) {
                log.error("发送Kafka消息时，执行异常...");
            } catch (TimeoutException e) {
                log.error("发送Kafka消息时，超时了...");
            }
        }
        REDISSON.shutdown();
    }
}
