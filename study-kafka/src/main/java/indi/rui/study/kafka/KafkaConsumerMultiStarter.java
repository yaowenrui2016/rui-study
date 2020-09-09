package indi.rui.study.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.LogContext;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static indi.rui.study.kafka.Constant.KAFKA_BOOTSTRAP_SERVER;

/**
 * @author: yaowr
 * @create: 2020-09-01
 */
public class KafkaConsumerMultiStarter {

    private static final Logger log;

    static {
        LogContext logContext = new LogContext("[日志上下文] ");
        log = logContext.logger(KafkaConsumerMultiStarter.class);
    }

    public static final Properties PROPS;

    static {
        PROPS = new Properties();
        PROPS.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVER);
        PROPS.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        PROPS.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        PROPS.put(ConsumerConfig.GROUP_ID_CONFIG, "yao_test_consumer" + System.currentTimeMillis());
        PROPS.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        PROPS.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);
        PROPS.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 2000);
        PROPS.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 6000);
        PROPS.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        PROPS.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
        PROPS.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 20000);
        List<String> interceptors =  new ArrayList<>();
        interceptors.add("indi.rui.study.kafka.MyConsumerInterceptor");
        PROPS.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptors);
    }

    private static final Pattern PATTERN = Pattern.compile("^.*?\\((.*)\\).*$");

    public static void main(String[] args) {
        for (int i = 0; i < 4; i++) {
            new Thread(KafkaConsumerMultiStarter::start, "kafka-consumer-" + i).start();
        }
        log.info("启动成功!");
    }

    private static void start() {
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(PROPS);
        kafkaConsumer.subscribe(Pattern.compile("yao_test_.*"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                log.info("Kafka重平衡正在发生...");
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

            }
        });

        try {
            while (true) {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(3));
                if (records.isEmpty()) {
                    continue;
                }
                Iterator<ConsumerRecord<String, String>> iterator = records.iterator();
                for (; iterator.hasNext(); ) {
                    ConsumerRecord<String, String> record = iterator.next();
                    String message = record.value();
                    log.info("接收到kafka消息：partition = {}, offset = {}, value = {}", record.partition(), record.offset(),message);
                    Matcher matcher = PATTERN.matcher(message);
                    if (matcher.find()) {
                        try {
                            Thread.sleep(Long.valueOf(matcher.group(1)) * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Throwable th) {
            log.error("消费失败");
        } finally {
            log.error("消费者退出~");
        }
    }
}
