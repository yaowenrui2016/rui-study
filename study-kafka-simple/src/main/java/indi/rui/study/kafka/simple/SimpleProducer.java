package indi.rui.study.kafka.simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * @author: yaowr
 * @create: 2021-06-04
 */
@Component
public class SimpleProducer {

    public static final Properties PROPS = new Properties();

    static {

        PROPS.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "study.rui.ubuntu:9092");
        PROPS.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        PROPS.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        PROPS.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        PROPS.put(ProducerConfig.ACKS_CONFIG, "all");
        PROPS.put(ProducerConfig.RETRIES_CONFIG, 1);
    }

    private KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(PROPS);

    public void send(String message) {
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("test-0604", message, message);
        kafkaProducer.send(producerRecord);
    }
}
