package indi.rui.study.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

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

    static {

        PROPS.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVER);
        PROPS.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        PROPS.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        PROPS.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        PROPS.put(ProducerConfig.ACKS_CONFIG, "1");
        PROPS.put(ProducerConfig.RETRIES_CONFIG, 1);
    }

    public static void main(String[] args) {
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(PROPS);
        for (int i = 0; i < 100000; i++) {
            String message = "THIS IS A TEST MESSAGE(" + i + ")!";
            ProducerRecord<String, String> record = new ProducerRecord<>("yao_test_1",
                    System.currentTimeMillis() + "", message);
            Future<RecordMetadata> future = kafkaProducer.send(record);
            try {
                future.get(200, TimeUnit.MILLISECONDS);
                log.info("发送Kafka消息成功: {}", record);
            } catch (InterruptedException e) {
                log.error("发送Kafka消息时，线程被打断...");
            } catch (ExecutionException e) {
                log.error("发送Kafka消息时，执行异常...");
            } catch (TimeoutException e) {
                log.error("发送Kafka消息时，超时了...");
            }
        }
    }
}
