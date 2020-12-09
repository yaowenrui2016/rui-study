package indi.rui.study.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import static indi.rui.study.kafka.CommonUtil.printTopicPartition;
import static indi.rui.study.kafka.Constant.KAFKA_BOOTSTRAP_SERVER;
import static indi.rui.study.kafka.Constant.MSG_CONTENT;

/**
 * 演示自动位移重置
 *
 * @author: yaowr
 * @create: 2020-09-24
 */
@Slf4j
public class OffsetResetDemo {

    private static final String TOPIC = "rui_study_offset_reset_demo_kafka_topic";

    private static final String GROUP_ID = "rui_study_offset_reset_demo_kafka_consumer";

    private static final Properties CONSUMER_PROPS;

    static {
        CONSUMER_PROPS = new Properties();
        CONSUMER_PROPS.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVER);
        CONSUMER_PROPS.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        CONSUMER_PROPS.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        CONSUMER_PROPS.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        CONSUMER_PROPS.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        CONSUMER_PROPS.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 2000);
        CONSUMER_PROPS.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 6000);
//        CONSUMER_PROPS.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        CONSUMER_PROPS.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5);
        CONSUMER_PROPS.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 20000);
    }

    private static final Properties PRODUCER_PROPS;

    static {
        PRODUCER_PROPS = new Properties();
        PRODUCER_PROPS.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVER);
        PRODUCER_PROPS.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        PRODUCER_PROPS.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        PRODUCER_PROPS.put(ProducerConfig.ACKS_CONFIG, "all");
    }

    private static void startProducer() {
        log.info("开始发送消息...");
        Map<String, AtomicInteger> counters = new HashMap<>();
        KafkaProducer<String, String> producer = new KafkaProducer<>(PRODUCER_PROPS);
        for (int i = 0; i < 20; i++) {
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddHHmm_ss_SSS"));
            AtomicInteger counter = counters.get(time);
            if (Objects.isNull(counter)) {
                counter = new AtomicInteger(0);
                counters.put(time, counter);
            }
            String num = time + "::" + counter.getAndIncrement();
            String value = MSG_CONTENT + num;
            ProducerRecord<String, String> record1 = new ProducerRecord<>(TOPIC, num, value);
            Future<RecordMetadata> future = producer.send(record1, (metadata, exception) -> {
                if (Objects.isNull(exception)) {
                    log.info("发送消息成功! topic = {}, partition = {}, offset ={}, num = {}", metadata.topic(), metadata.partition(), metadata.offset(), num);
                } else {
                    log.error("发送消息失败~ num = {}", num, exception);
                }
            });
            try {
                future.get(3, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        log.info("发送消息完成!");
    }

    private static final Properties ADMIN_PROPS;

    static {
        ADMIN_PROPS = new Properties();
        ADMIN_PROPS.put("bootstrap.servers", KAFKA_BOOTSTRAP_SERVER);
        ADMIN_PROPS.put("request.timeout.ms", 600000);
    }

    /**
     * 创建 Topic
     */
    public static void createTopics() {
        try (AdminClient client = AdminClient.create(ADMIN_PROPS)) {
            List<NewTopic> topics = Collections.singletonList(new NewTopic(TOPIC, 1, (short) 1));
            log.info("开始创建 topics...");
            CreateTopicsResult result = client.createTopics(topics);
            result.all().get(10, TimeUnit.SECONDS);
            log.info("创建成功! topics = {}", Arrays.toString(topics.toArray()));
        } catch (Throwable th) {
            log.error("创建 topics 出错了", th);
        }
    }

    /**
     * 删除 Topic
     */
    public static void deleteTopic() {
        try (AdminClient client = AdminClient.create(ADMIN_PROPS)) {
            List<String> topics = Collections.singletonList(TOPIC);
            log.info("开始删除 topics...");
            DeleteTopicsResult result = client.deleteTopics(topics);
            result.all().get(10, TimeUnit.SECONDS);
            log.info("删除成功! topics = {}", Arrays.toString(topics.toArray()));
        } catch (Throwable th) {
            log.error("删除 topics 出错了", th);
        }
    }


    /**
     * 演示1.在未调用 {@link KafkaConsumer#poll(Duration)} 或者 {@link KafkaConsumer#poll(long)}
     * 的情况下，获取当前消费者被分配到的主题分区
     * 结论：未调用 poll 时获取不到，，调用了 poll 才能获取到
     */
    private static void test1() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(CONSUMER_PROPS);
        consumer.subscribe(Collections.singleton(TOPIC));
        consumer.poll(0);
        Set<TopicPartition> tps = consumer.assignment();
        log.info("当前分配到的主题分区: {}", printTopicPartition(tps));
    }


    /**
     * 演示2.不同重置位移策略演示，通过设置 {@link ConsumerConfig.AUTO_OFFSET_RESET_CONFIG}，默认是 latest
     * 设为 latest：先送20条消息，然后启动消费者，获取当前消费者主题分区位移为20，对应最新主题分区位移
     * 设为 earliest：总是从最早存在的主题分区位移开始，因为日志可能被删除
     */
    private static void test2() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(CONSUMER_PROPS);
        consumer.subscribe(Collections.singleton(TOPIC));
        consumer.poll(0);
        Set<TopicPartition> tps = consumer.assignment();
        for (TopicPartition tp : tps) {
            log.info("当前主题分区位移: tp = {}, offset = {}", tp, consumer.position(tp));
        }
    }

    public static void main(String[] args) {
//        createTopics();
//        deleteTopic();
//        startProducer();
//        test1();
        test2();
    }

}
