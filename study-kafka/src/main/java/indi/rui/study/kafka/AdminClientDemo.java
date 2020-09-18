package indi.rui.study.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.requests.DescribeLogDirsResponse;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static indi.rui.study.kafka.Constant.KAFKA_BOOTSTRAP_SERVER;

/**
 * @author: yaowr
 * @create: 2020-09-16
 */
@Slf4j
public class AdminClientDemo {


    private static final String TOPIC_1 = "admin_test_create_topic_1";
    private static final String TOPIC_2 = "admin_test_create_topic_2";

    private static final String GROUP_ID = "yao_admin_test_consumer";

    private static final String MSG_CONTENT = "这是一条测试消息";

    private static final String CONSUMER_THREAD_NAME = "my-kafka-consumer";

    private static final Integer BROKER_ID = 1;

    private static Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
    private static boolean commitFlag = true;


    //============= 管理员 ================//
    private static final Properties ADMIN_PROPS;

    static {
        ADMIN_PROPS = new Properties();
        ADMIN_PROPS.put("bootstrap.servers", KAFKA_BOOTSTRAP_SERVER);
        ADMIN_PROPS.put("request.timeout.ms", 600000);
    }

    public static void main(String[] args) {
        // 演示1.创建 Topics
//        createTopics();
        // 发送消息
//        startProducer();
        // 启动消费者
//        startConsumer();
        // 演示2.查询消费者组位移
//        queryConsumerGroupOffset();
        // 演示3.删除 Topics
        deleteTopic();
        // 演示4.查询 Broker 的磁盘占用
//        describeLogDirs();
    }

    /**
     * 演示1.创建 Topic
     */
    public static void createTopics() {
        try (AdminClient client = AdminClient.create(ADMIN_PROPS)) {
            List<NewTopic> topics = Arrays.asList(
                    new NewTopic(TOPIC_1, 1, (short) 1)
                    , new NewTopic(TOPIC_2, 10, (short) 1)
            );
            log.info("开始创建 topics: {}", Arrays.toString(topics.toArray()));
            CreateTopicsResult result = client.createTopics(topics);
            result.all().get(10, TimeUnit.SECONDS);
        } catch (Throwable th) {
            log.error("创建 Topics 出错了", th);
        }
    }

    /**
     * 演示2.查询消费者组位移
     */
    public static void queryConsumerGroupOffset() {
        try (AdminClient client = AdminClient.create(ADMIN_PROPS)) {
            log.info("开始查询消费者位移...");
            ListConsumerGroupOffsetsResult result = client.listConsumerGroupOffsets(GROUP_ID);
            Map<TopicPartition, OffsetAndMetadata> offset = result.partitionsToOffsetAndMetadata().get(10, TimeUnit.SECONDS);
            log.info("消费者位移: groupId = {}, offsets = {}", GROUP_ID, offset);
        } catch (Throwable th) {
            log.error("查询消费者组位移出错了", th);
        }
    }

    /**
     * 演示3.删除 Topic
     */
    public static void deleteTopic() {
        try (AdminClient client = AdminClient.create(ADMIN_PROPS)) {
            List<String> topics = Arrays.asList(
                    TOPIC_1
                    , TOPIC_2
            );
            log.info("开始删除 topics: {}", Arrays.toString(topics.toArray()));
            DeleteTopicsResult result = client.deleteTopics(topics);
            result.all().get(10, TimeUnit.SECONDS);
        } catch (Throwable th) {
            log.error("删除 Topics 出错了", th);
        }
    }

    /**
     * 演示4.查询Broker的磁盘占用
     */
    public static void describeLogDirs() {
        List<Integer> brokers = Collections.singletonList(BROKER_ID);
        try (AdminClient client = AdminClient.create(ADMIN_PROPS)) {
            DescribeLogDirsResult result = client.describeLogDirs(brokers); // 指定Broker id
            long size = 0L;

            Map<Integer, Map<String, DescribeLogDirsResponse.LogDirInfo>> dirInfoMap = result.all().get(3, TimeUnit.SECONDS);
            for (Map<String, DescribeLogDirsResponse.LogDirInfo> logDirInfoMap : dirInfoMap.values()) {
                size += logDirInfoMap.values().stream().map(logDirInfo -> logDirInfo.replicaInfos).flatMap(
                        topicPartitionReplicaInfoMap ->
                                topicPartitionReplicaInfoMap.values().stream().map(replicaInfo -> replicaInfo.size))
                        .mapToLong(Long::longValue).sum();
            }
            log.info("查询 Brokers = {} 的磁盘占用 size = {}", brokers, size);
        } catch (Exception e) {
            log.error("查询 Brokers = {} 的磁盘占用时出错了", brokers, e);
        }
    }

    //============== 消费者 ================//

    private static final Properties CONSUMER_PROPS;

    static {
        CONSUMER_PROPS = new Properties();
        CONSUMER_PROPS.put("bootstrap.servers", KAFKA_BOOTSTRAP_SERVER);
        CONSUMER_PROPS.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        CONSUMER_PROPS.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        CONSUMER_PROPS.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        CONSUMER_PROPS.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        CONSUMER_PROPS.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 2000);
        CONSUMER_PROPS.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 6000);
        CONSUMER_PROPS.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        CONSUMER_PROPS.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5);
        CONSUMER_PROPS.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 20000);
    }

    private static void startConsumer() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(CONSUMER_PROPS);
        List<String> topics = Arrays.asList(TOPIC_1
                , TOPIC_2
        );
        consumer.subscribe(topics);
        new Thread(() -> {
            log.info("开始重设消费者位移...");
            consumer.poll(0);
            consumer.partitionsFor(TOPIC_1).stream()
                    .map(info -> new TopicPartition(info.topic(), info.partition()))
                    .forEach(tp -> {
                        OffsetAndMetadata offsetAndMetadata = consumer.committed(tp);
                        if (!Objects.isNull(offsetAndMetadata)) {
                            long offset = offsetAndMetadata.offset();
                            log.info("重设分区的消费者位移: tp = {}, offset = {}", tp, offset);
                            consumer.seek(tp, offset);
                        } else {
                            log.info("{} 未找到已提交的位移", tp);
                        }
                    });
            consumer.seekToBeginning(consumer.partitionsFor(TOPIC_2).stream()
                    .map(info -> new TopicPartition(info.topic(), info.partition()))
                    .collect(Collectors.toList()));
            log.info("重设 {} 所有分区的消费者位移为 begin", TOPIC_2);
            log.info("启动消费者... threadName = {}", CONSUMER_THREAD_NAME);
            while (true) {
                try {
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                    if (records.isEmpty()) {
                        continue;
                    }
                    for (ConsumerRecord<String, String> record : records) {
                        log.info("接收到消息：topic = {}, partition = {}, offset = {}, value = {}", record.topic(), record.partition(), record.offset(), record.value());
                        offsets.put(
                                new TopicPartition(record.topic(), record.partition()),
                                new OffsetAndMetadata(record.offset() + 1));
                    }
                    log.info("开始提交消费者位移...");
                    consumer.commitSync(offsets);
                    log.info("提交位移成功: offsets = {}", offsets);
                } catch (Exception e) {
                    log.error("消费出现异常~", e);
                    break;
                }
            }
            log.info("消费者退出~ threadName = {}", CONSUMER_THREAD_NAME);
        }, CONSUMER_THREAD_NAME).start();
    }


    //============= 生产者 =============//

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
        KafkaProducer<String, String> producer = new KafkaProducer<>(PRODUCER_PROPS);
        for (int i = 0; i < 10; i++) {
            String num = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddHHmm_ss:nnnnnnnnn"));
            String value = MSG_CONTENT + num;
            ProducerRecord<String, String> record1 = new ProducerRecord<>(TOPIC_1, num, value);
            producer.send(record1, (metadata, exception) -> {
                if (Objects.isNull(exception)) {
                    log.info("发送消息成功! num = {}", num);
                } else {
                    log.error("发送消息失败~ num = {}", num, exception);
                }
            });
            ProducerRecord<String, String> record2 = new ProducerRecord<>(TOPIC_2, num, value);
            producer.send(record2, (metadata, exception) -> {
                if (Objects.isNull(exception)) {
                    log.info("发送消息成功! num = {}", num);
                } else {
                    log.error("发送消息失败~ num = {}", num, exception);
                }
            });
        }
        log.info("发送消息完成!");
    }
}
