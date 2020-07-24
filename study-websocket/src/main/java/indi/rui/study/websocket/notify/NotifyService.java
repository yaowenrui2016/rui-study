package indi.rui.study.websocket.notify;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonDeserializer;
import indi.rui.study.common.utils.IDGenerator;
import indi.rui.study.websocket.notify.email.EmailSender;
import indi.rui.study.websocket.notify.sender.ISender;
import indi.rui.study.websocket.notify.sender.SysmsgSender;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Slf4j
@RestController
@RequestMapping("/api/notify")
public class NotifyService implements NotifyApi {

    private static final KafkaProducer<String, NotifyQueueContext> producer;

    @Value("${topicAmount:4}")
    private int topicAmount;

    static {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "study.rui.ubuntu:9092");
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 1);
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        properties.put(ProducerConfig.RETRIES_CONFIG, 1);
        producer = new KafkaProducer<>(properties);
    }

    @Override
    public long send(NotifyContext notifyContext) {
        notifyContext.setNotifyId(IDGenerator.get());
        dispatch(notifyContext, "send");
        return notifyContext.getNotifyId();
    }

    @Override
    public void done(NotifyContext notifyContext) {
        dispatch(notifyContext, "done");
    }

    @Override
    public void remove(NotifyContext notifyContext) {
        dispatch(notifyContext, "remove");
    }

    private void dispatch(NotifyContext notifyContext, String command) {
        String sender = notifyContext.getSender();
        switch (sender) {
            case "sysmsg":
            case "email":
                break;
            default:
                throw new RuntimeException("不支持的消息发送方式");
        }
        String key = notifyContext.getEntityId();
        String topic = "notify_" + sender +"_" + (Math.abs(Objects.hashCode(key)) % topicAmount);
        NotifyQueueContext queueContext = new NotifyQueueContext(command, key, JSON.toJSONString(notifyContext));
        ProducerRecord<String, NotifyQueueContext> record = new ProducerRecord<>(topic, key, queueContext);
        Future<RecordMetadata> future = producer.send(record);
        try {
            future.get(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("线程被打断了", e);
        } catch (ExecutionException e) {
            log.error("执行异常", e);
        } catch (TimeoutException e) {
            log.error("超时了", e);
        }
        log.info("发送消息成功：topic={}, value={}", topic, JSON.toJSONString(queueContext));
    }
}
