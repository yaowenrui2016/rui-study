package indi.rui.study.kafka.simple;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;

/**
 * @author: yaowr
 * @create: 2021-06-04
 */
@Component
public class SimpleConsumer implements ApplicationListener<ApplicationReadyEvent> {

    private static final Properties PROPS;

    static {
        PROPS = new Properties();
        PROPS.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "study.rui.ubuntu:9092");
        PROPS.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        PROPS.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        PROPS.put(ConsumerConfig.GROUP_ID_CONFIG, "study-kafka-simple");
        PROPS.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//        PROPS.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 500);
        PROPS.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 2000);
        PROPS.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 6000);
        PROPS.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        PROPS.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5);
        PROPS.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 3000);
    }

    @Autowired
    private ISimpleService service;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        start();
    }

    private void start() {
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(PROPS);
        kafkaConsumer.subscribe(Collections.singleton("test-0604"));
        for (; ; ) {
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
            if (consumerRecords.isEmpty()) {
                continue;
            }
            Iterator<ConsumerRecord<String, String>> iterator = consumerRecords.iterator();
            while (iterator.hasNext()) {
                ConsumerRecord<String, String> consumerRecord = iterator.next();
                service.consume(consumerRecord.value());
            }
        }
    }
}
