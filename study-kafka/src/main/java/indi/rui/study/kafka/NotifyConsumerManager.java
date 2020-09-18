package indi.rui.study.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import static indi.rui.study.kafka.Constant.KAFKA_BOOTSTRAP_SERVER;

/**
 * @author: yaowr
 * @create: 2020-07-23
 */
@Slf4j
@Service
public class NotifyConsumerManager implements InitializingBean {

    public static final Properties PROPS;

    static {
        PROPS = new Properties();
        PROPS.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVER);
        PROPS.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        PROPS.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        PROPS.put(ConsumerConfig.GROUP_ID_CONFIG, "notify");
        PROPS.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//        PROPS.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 500);
        PROPS.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 2000);
        PROPS.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 6000);
        PROPS.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        PROPS.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 20);
        PROPS.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10000);
    }

    @Value("${notify.consumer.amount:5}")
    private int consumerAmount;

    @Value("${notify.consumer.topics:}")
    private String topics;

    @Override
    public void afterPropertiesSet() throws Exception {
        final List<String> topicList = Arrays.asList(topics.split(","));
        for (int i = 0; i < consumerAmount; i++) {
            new Thread(() -> start(topicList), "notify-kafka-consumer-" + i).start();
        }
    }


    private void start(List<String> topics) {
        KafkaConsumer<String, Object> consumer = new KafkaConsumer<>(PROPS);
//        consumer.subscribe(Arrays.asList("notify_sysmsg_0", "notify_sysmsg_1", "notify_sysmsg_2", "notify_sysmsg_3", "notify_sysmsg_4"));
        consumer.subscribe(Pattern.compile("yao_test_.*"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                log.info("Kafka重平衡正在发生...");
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

            }
        });
        int rePoll = 3;
        log.info("消费者启动!");
        outloop:
        while (true) {
            try {
                ConsumerRecords<String, Object> records = consumer.poll(Duration.ofMillis(100));
                if (records.isEmpty()) {
                    continue;
                }
                log.info("本次从{}分区拉取到消息数量：{}", Arrays.toString(records.partitions().stream().map(tp -> "(" + tp.topic() + "->" + tp.partition() + ")").toArray()), records.count());
                for (ConsumerRecord<String, Object> record : records) {
                    try {
                        log.info("消费消息：topic = {}, partition = {}, offset = {}, value = {}", record.topic(), record.partition(), record.offset(), record.value());
                        // ...do some process
                        if (Thread.currentThread().getName().contains("4")) {
                            Thread.sleep(12000);
                            log.error("消息处理异常，线程退出");
                            break outloop;
                        } else {
                            consumer.commitSync();
                            log.info("消息处理完成，并提交偏移量：offset = {}", record.offset());
                        }
                    } catch (Exception e) {
                        log.error("消息消费发生异常", e);
                    }
                }
            } catch (Exception e) {
                log.error("拉取消息异常", e);
                if (rePoll > 0) {
                    log.warn("沉睡3秒再进行拉取...剩余次数:{}", --rePoll);
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ex) {

                    }
                } else {
                    break;
                }
            }
        }
        log.info("消费者停止!");
    }
}
