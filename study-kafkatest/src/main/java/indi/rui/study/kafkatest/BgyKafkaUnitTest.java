package indi.rui.study.kafkatest;

import indi.rui.study.kafkatest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 碧桂园待办完整性测试程序
 *
 * @author: yaowr
 * @create: 2021-10-22
 */
@Slf4j
public class BgyKafkaUnitTest {

    // ===================== 静态变量 ===================== //

    private static final String DEFAULT_KAFKA_BOOTSTRAP_SERVER = "study.rui.ubuntu:9092";

    private static final String DEFAULT_TOPIC = "test_topic";

    private static final int DEFAULT_PRODUCE_INTERVAL_MS = -1;

    private static final int DEFAULT_MONITOR_INTERVAL_SEC = 10;

    private static final int DEFAULT_MAX_PRODUCE_RECORDS = 1000000;

    private static final boolean DEFAULT_IGNORE_PRODUCER = false;

    private static final boolean DEFAULT_IGNORE_CONSUMER = false;

    private static final int DEFAULT_PRODUCER_THREAD = 10;

    private static final int DEFAULT_CONSUMER_THREAD = 10;

    private static final String MESSAGE_PATH = "json/send_todo.json";


    // =====================  main ===================== //

    // java -jar -Dkafka.bootstrapServer=192.168.191.10:19092,192.168.191.11:19092,192.168.191.12:19092 -Dkafka.topic=test_topic -Dkafka.producerThread=10 -Dkafka.consumerThread=10 -Dkafka.maxProduceRecords=-1 -Dkafka.ignoreProducer=false lib\study-kafkatest-0.0.1.SNAPSHOT.jar
    public static void main(String[] args) {
        log.info(">>>>>>>>>>>>>>>>> kafka测试开始 <<<<<<<<<<<<<<<<<");
        BgyKafkaUnitTest unitTest = new BgyKafkaUnitTest();
        // 启动监控线程
        new Thread(unitTest::monitor, "monitor").start();

        // 启动消费者
        unitTest.consume();

        // 启动生产者
        unitTest.produce();

        // 通过控制台手动触发查询以及停止程序
        log.info("\n===第一次输入'stop'停止生产===" +
                "\n===第二次输入'stop'停止消费===" +
                "\n===第三次输入'stop'停止程序===" +
                "\n===输入其他任意字符立即触发监控查询===");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String input = scanner.next();
            if (unitTest.produceRunning && "stop".equalsIgnoreCase(input)) {
                unitTest.produceRunning = false;
            } else if (unitTest.consumeRunning && !unitTest.produceRunning && "stop".equalsIgnoreCase(input)) {
                unitTest.consumeRunning = false;
            } else if (unitTest.monitorRunning && !unitTest.consumeRunning && "stop".equalsIgnoreCase(input)) {
                unitTest.monitorRunning = false;
                unitTest.addPermit();
                break;
            } else {
                unitTest.addPermit();
            }
        }
        log.info("stopped.");
    }


    // ===================== 成员变量 ===================== //

    private String bootstrapServer = System.getProperty("kafka.bootstrapServer", DEFAULT_KAFKA_BOOTSTRAP_SERVER);

    private String topic = System.getProperty("kafka.topic", DEFAULT_TOPIC);

    private final AtomicInteger produceCount = new AtomicInteger(0);

    private final AtomicInteger lastProduceCount = new AtomicInteger(0);

    private final AtomicInteger consumeCount = new AtomicInteger(0);

    private final AtomicInteger lastConsumeCount = new AtomicInteger(0);

    private long lastSpeedTime = System.currentTimeMillis();

    private LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(10);

    private int produceInterval = Integer.valueOf(System.getProperty("kafka.produceInterval.ms", String.valueOf(DEFAULT_PRODUCE_INTERVAL_MS)));

    private int monitorInterval = Integer.valueOf(System.getProperty("kafka.monitorInterval.s", String.valueOf(DEFAULT_MONITOR_INTERVAL_SEC)));

    private long maxProduceRecords = Long.valueOf(System.getProperty("kafka.maxProduceRecords", String.valueOf(DEFAULT_MAX_PRODUCE_RECORDS)));

    private boolean produceRunning = true;

    private boolean consumeRunning = true;

    private boolean monitorRunning = true;

    private boolean ignoreProducer = Boolean.valueOf(System.getProperty("kafka.ignoreProducer", String.valueOf(DEFAULT_IGNORE_PRODUCER)));

    private boolean ignoreConsumer = Boolean.valueOf(System.getProperty("kafka.ignoreConsumer", String.valueOf(DEFAULT_IGNORE_CONSUMER)));

    private int producerThread = Integer.valueOf(System.getProperty("kafka.producerThread", String.valueOf(DEFAULT_PRODUCER_THREAD)));

    private int consumerThread = Integer.valueOf(System.getProperty("kafka.consumerThread", String.valueOf(DEFAULT_CONSUMER_THREAD)));

    private String message;

//    private ConcurrentMap<String, String> msgMap = new ConcurrentHashMap<>();


    // ===================== Constructor ===================== //

    public BgyKafkaUnitTest() {
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader()
                .getResource(MESSAGE_PATH)).getFile();
        this.message = FileUtils.readFileToString(filePath, "utf-8");
    }


    // ===================== 主要API ===================== //

    /**
     * 启动生产者
     */
    public void produce() {
        if (ignoreProducer) {
            produceRunning = false;
            log.warn("ignore producer.");
            return;
        }
        KafkaProducer<String, String> kafkaProducer = initProducer();
        for (int i = 0; i < producerThread; i++) {
            new Thread(() -> {
                long begin = System.currentTimeMillis();
                while (produceRunning) {
                    try {
                        // 同步调用send
                        int count = produceCount.incrementAndGet();
                        if (maxProduceRecords > 0 && count > maxProduceRecords) {
                            produceRunning = false;
                            produceCount.decrementAndGet();
                        } else {
                            String key = count + "";
                            kafkaProducer.send(new ProducerRecord<>(topic, key, message), new Callback() {
                                @Override
                                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                                    if (e != null) {
                                        produceCount.decrementAndGet();
                                        log.error("send kafka message failed!", e);
                                    } else {
//                                        String value = recordMetadata.topic() + ":"
//                                                + recordMetadata.partition() + ":"
//                                                + recordMetadata.offset();
//                                        msgMap.put(key, value);
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        produceCount.decrementAndGet();
                        log.error("send kafka message failed!", e);
                    }
                    if (produceInterval > 0) {
                        try {
                            Thread.sleep(produceInterval);
                        } catch (InterruptedException e) {
                        }
                    }
                }
                long end = System.currentTimeMillis();
                log.info("producer stopped. [duration={}(s)]", (end - begin) / 1000f);
            }, "producer-" + i).start();
        }
    }

    /**
     * 启动消费者
     */
    private void consume() {
        if (ignoreConsumer) {
            consumeRunning = false;
            log.warn("ignore consumer.");
            return;
        }
        for (int i = 0; i < consumerThread; i++) {
            new Thread(() -> {
                KafkaConsumer<String, String> kafkaConsumer = initConsumer();
                kafkaConsumer.subscribe(Collections.singleton(topic));
                while (consumeRunning) {
                    ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
                    if (consumerRecords.isEmpty()) {
                        continue;
                    }
                    Iterator<ConsumerRecord<String, String>> iterator = consumerRecords.iterator();
                    while (iterator.hasNext()) {
                        ConsumerRecord<String, String> consumerRecord = iterator.next();
                        if (message.equals(consumerRecord.value())) {
//                            msgMap.remove(consumerRecord.key());
                            consumeCount.incrementAndGet();
                        }
                    }
                    kafkaConsumer.commitSync();
                }
                log.info("consume stopped.");
            }, "consumer-" + i).start();
        }
    }

    /**
     * 启动监控
     */
    public void monitor() {
        while (true) {
            try {
                queue.poll(monitorInterval, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
            }
            if (!monitorRunning) {
                break;
            }
            long currentSpeedTime = System.currentTimeMillis();
            long duration = (currentSpeedTime - lastSpeedTime) / 1000;
            duration = duration == 0 ? 1 : duration;
            lastSpeedTime = currentSpeedTime;
            int pCount = produceCount.get();
            int cCount = consumeCount.get();
            log.info("\n---------------- monitor duration: {}(s) ----------------\n" +
                            "topic: {}\n" +
                            "producer: [count={}, speed={}/s] - {}\n" +
                            "consume:  [count={}, speed={}/s] - {}",
                    duration,
                    topic,
                    pCount,
                    (pCount - lastProduceCount.getAndSet(pCount)) / duration,
                    produceRunning ? "running" : "stopped",
                    cCount,
                    (cCount - lastConsumeCount.getAndSet(cCount)) / duration,
                    consumeRunning ? "running" : "stopped");
        }
        log.info("monitor stopped.");
    }

    public void addPermit() {
        try {
            queue.add(1);
        } catch (Exception e) {
            log.error("add permit failed!", e);
        }
    }


    // =====================  私有方法 ===================== //

    /**
     * 初始化生产者
     */
    private KafkaProducer initProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
//        properties.put(ProducerConfig.LINGER_MS_CONFIG, 50);
//        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 100);
        properties.put(ProducerConfig.ACKS_CONFIG, "all");
        properties.put(ProducerConfig.RETRIES_CONFIG, 5);
        return new KafkaProducer<>(properties);
    }

    /**
     * 初始化消费者
     */
    private KafkaConsumer initConsumer() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "kafka_unit_test");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
//        PROPS.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 500);
        properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, 2000);
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 6000);
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
        properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 20000);
        return new KafkaConsumer<String, String>(properties);
    }
}
