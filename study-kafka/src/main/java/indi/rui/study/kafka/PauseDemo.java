package indi.rui.study.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static indi.rui.study.kafka.CommonUtil.printOffsets;
import static indi.rui.study.kafka.CommonUtil.printTopicPartition;
import static indi.rui.study.kafka.Constant.KAFKA_BOOTSTRAP_SERVER;

/**
 * @author: yaowr
 * @create: 2020-09-17
 */
@Slf4j
public class PauseDemo {

    private static final String TOPIC = "pause_test_topic";

    private static final String GROUP_ID = "kafka-consumer";

    private static final String MSG_CONTENT = "这是一条测试消息";

    private static final String CONSUMER_THREAD_NAME = GROUP_ID;

    private static Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();

    private static Random random = new Random(System.currentTimeMillis());

    private static boolean running = true;


    public static void shutdown() {
        PauseDemo.running = false;
    }

    /**
     * ======================================================================================
     * || ------------- * ------------- ||      管理员      || ------------- * ------------- ||
     * ======================================================================================
     **/


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
            List<NewTopic> topics = Collections.singletonList(new NewTopic(TOPIC, 4, (short) 1));
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
     * 删除 Topic
     */
    public static void listTopics() {
        try (AdminClient client = AdminClient.create(ADMIN_PROPS)) {
            List<String> topics = Collections.singletonList(TOPIC);
            log.info("开始删除 topics...");
            DescribeTopicsResult result = client.describeTopics(topics);
            result.all().get(10, TimeUnit.SECONDS);
            log.info("删除成功! topics = {}", Arrays.toString(topics.toArray()));
        } catch (Throwable th) {
            log.error("删除 topics 出错了", th);
        }
    }


    /**
     * ======================================================================================
     * || ------------- * ------------- ||      消费者      || ------------- * ------------- ||
     * ======================================================================================
     **/

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

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1
            , 0, TimeUnit.SECONDS, new SynchronousQueue<>(true)
            , (r) -> new Thread(r, "executor"), new ThreadPoolExecutor.CallerRunsPolicy());

    /**
     * 即将恢复的分区
     * 如果改集合不为空表示有需要恢复的分区
     */
    private static LinkedTransferQueue<TopicPartition> pendingResume = new LinkedTransferQueue<>();

    /**
     * 是否暂停标记
     */
    private static boolean paused = false;

    /**
     * 当前消费者分配的所有主题分区
     */
    private static Set<TopicPartition> topicPartitions;

    /**
     * 启动消费者
     */
    private static void startConsumer() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(CONSUMER_PROPS);
        List<String> topics = Collections.singletonList(TOPIC);
        consumer.subscribe(topics);
        new Thread(() -> {
            log.info("开始重设消费者位移...");
            consumer.poll(0);
            topicPartitions = consumer.partitionsFor(TOPIC)
                    .stream()
                    .map(info -> new TopicPartition(TOPIC, info.partition()))
                    .collect(Collectors.toSet());
            for (TopicPartition tp : topicPartitions) {
                long offset = 0;
                OffsetAndMetadata offsetAndMetadata = consumer.committed(tp);
                if (!Objects.isNull(offsetAndMetadata)) {
                    offset = offsetAndMetadata.offset();
                } else {
                    log.info("未找到已提交的位移~");
                }
                consumer.seek(tp, offset);
                log.info("重设消费位移成功！tp = {}, offset = {}", tp, offset);
            }
            log.info("启动消费者... threadName = {}", CONSUMER_THREAD_NAME);
            Set<TopicPartition> pausedTps = new HashSet<>();
            while (running) {
                try {
                    // 检查是否有需要恢复的分区
                    if (paused && !pendingResume.isEmpty()) {
                        Iterator<TopicPartition> iterator = pendingResume.iterator();
                        while (iterator.hasNext()) {
                            pausedTps.add(iterator.next());
                            iterator.remove();
                        }
                        consumer.resume(pausedTps);
                        paused = false;
                        log.info("KafkaConsumer【恢复】拉取... TopicPartition = {}", printTopicPartition(pausedTps));
                    }
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(2000));
                    if (records.isEmpty()) {
                        log.warn("KafkaConsumer 未拉取到消息~");
                        continue;
                    }
                    CompletableFuture<Map<TopicPartition, OffsetAndMetadata>> future = CompletableFuture.supplyAsync(() -> {
                        long timeMillis = System.currentTimeMillis();
                        try {
                            for (ConsumerRecord<String, String> record : records) {
                                log.info("接收到消息：topic = {}, partition = {}, offset = {}, value = {}"
                                        , record.topic(), record.partition(), record.offset(), record.value());
                            }
                            // 模拟消息处理异常
                            if (timeMillis % 4 == 0) {
                                throw new IllegalArgumentException("模拟异常：时间戳不能被4整除");
                            }
                            int spend = random.nextInt(5) * 5 + 1;
                            log.info("处理消息中... 数量：{}, 耗时：{}秒", records.count(), spend);
                            // 模拟消息处理耗时
                            Thread.sleep(spend * 1000);
                            Map<TopicPartition, OffsetAndMetadata> offsets = new HashMap<>();
                            for (ConsumerRecord<String, String> record : records) {
                                offsets.put(
                                        new TopicPartition(record.topic(), record.partition()),
                                        new OffsetAndMetadata(record.offset() + 1));
                            }
                            return offsets;
                        } catch (Exception e) {
                            log.error("处理消息失败~", e);
                            return null;
                        }
                    }, executor);
                    future.whenComplete((offsets, exception) -> {
                        if (Objects.isNull(offsets)) {
                            log.error("记录消费失败的消息~");
                        } else {
                            // 即将要提交的位移
                            log.info("消息处理完毕！");
                            PauseDemo.offsets.putAll(offsets);
                        }
                        if (paused) {
                            log.info("需要恢复拉取.");
                            // 即将要恢复的分区
                            pendingResume.addAll(topicPartitions);
                        }
                    });
                    try {
                        // 阻塞当前线程5秒，这期间如果异步线程处理完成，则无须暂停消费者
                        future.get(10, TimeUnit.SECONDS);
                    } catch (Exception e) {
                        /*
                        暂停拉取主题分区，可以优化为当前消息的分区
                         */
                        consumer.pause(topicPartitions);
                        paused = true;
                        log.warn("短时间内未处理完消息，KafkaConsumer 【暂停】拉取... TopicPartition = {}", printTopicPartition(topicPartitions));
                    }
                    log.info("开始提交消费者位移...");
                    consumer.commitSync(offsets);
                    log.info("提交位移成功: offsets = {}", printOffsets(offsets));
                } catch (Exception e) {
                    log.error("KafkaConsumer 出现异常~", e);
                    break;
                }
            }
            log.info("消费者退出~ threadName = {}", CONSUMER_THREAD_NAME);
        }, CONSUMER_THREAD_NAME).start();
    }

    /**
     * 获取主题分区最新位移
     */
    private static void getLatestPosition() {
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(CONSUMER_PROPS);
        List<TopicPartition> tps = consumer.partitionsFor(TOPIC).stream()
                .map(info -> new TopicPartition(TOPIC, info.partition()))
                .collect(Collectors.toList());
        consumer.assign(tps);
        consumer.seekToEnd(tps);
        for (TopicPartition tp : tps) {
            log.info("主题分区的最新位置: tp = {}, position = {}", tp, consumer.position(tp));
        }
    }


    /**
     * ======================================================================================
     * || ------------- * ------------- ||      生产者      || ------------- * ------------- ||
     * ======================================================================================
     **/

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
            producer.send(record1, (metadata, exception) -> {
                if (Objects.isNull(exception)) {
                    log.info("发送消息成功! topic = {}, partition = {}, offset ={}, num = {}", metadata.topic(), metadata.partition(), metadata.offset(), num);
                } else {
                    log.error("发送消息失败~ num = {}", num, exception);
                }
            });
        }
        log.info("发送消息完成!");
    }

    public static void main(String[] args) {
        // 创建 Topics
//        createTopics();
        // 发送消息
//        startProducer();
        // 查看主题分区的最新位置
//        getLatestPosition();
        // 启动消费者
        startConsumer();
        // 删除 Topics
//        deleteTopic();
    }
}
