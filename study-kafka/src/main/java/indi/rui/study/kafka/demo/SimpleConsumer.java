package indi.rui.study.kafka.demo;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.LogContext;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

import static indi.rui.study.kafka.Constant.KAFKA_BOOTSTRAP_SERVER;

/**
 * @author: yaowr
 * @create: 2023-02-06
 */
public class SimpleConsumer {

    private static final Logger log;

    static {
        LogContext logContext = new LogContext("[日志上下文] ");
        log = logContext.logger(SimpleConsumer.class);
    }

    public static final Properties PROPS;

    static {
        PROPS = new Properties();
        PROPS.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVER);
        PROPS.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        PROPS.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        PROPS.put(ConsumerConfig.GROUP_ID_CONFIG, "yao_test_consumer");
        PROPS.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
//        PROPS.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 500);
        PROPS.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 2000);
        PROPS.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 6000);
        PROPS.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        PROPS.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 20);
        PROPS.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10000);
    }

    public static void main(String[] args) {
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(PROPS);
        kafkaConsumer.subscribe(Collections.singleton("yao_test"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                log.info("Kafka重平衡正在发生...");
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

            }
        });

        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMinutes(180));
            if (records.isEmpty()) {
                continue;
            }
            Iterator<ConsumerRecord<String, String>> iterator = records.iterator();
            for (; iterator.hasNext(); ) {
                ConsumerRecord<String, String> record = iterator.next();
                log.info("接收到kafka消息：{}", record.value());
            }
        }
    }
}
