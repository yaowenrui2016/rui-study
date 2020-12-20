package indi.rui.study.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author: yaowr
 * @create: 2020-12-09
 */
@Slf4j
public class Consumer {
    public static void main(String[] args) throws InterruptedException, MQClientException {

        // Instantiate with specified consumer group name.
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("rui_study");

        // Specify name server addresses.
        consumer.setNamesrvAddr("127.0.0.1:9876");

        // Subscribe one more more topics to consume.
        consumer.subscribe("TopicTest", "*");
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // Register callback to execute on arrival of messages fetched from brokers.
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            String[] contentArray = msgs.stream().map(msgExt -> {
                try {
                    return new String(msgExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                } catch (UnsupportedEncodingException e) {
                    log.error("Message byte[] to String Failed", e);
                    throw new RuntimeException("");
                }
            }).toArray(value -> new String[value]);
            log.info("Receive New Messages: {}, contents={}", msgs, Arrays.toString(contentArray));
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        //Launch the consumer instance.
        consumer.start();

        System.out.printf("Consumer Started.%n");
    }
}
