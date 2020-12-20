package indi.rui.study.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;
import java.util.Set;

/**
 * @author: yaowr
 * @create: 2020-12-10
 */
@Slf4j
public class PullConsumer {
    public static void main(String[] args) throws Exception {
//        MessageQueue messageQueue = new MessageQueue();
//        messageQueue.setTopic("TestTopic");
//        messageQueue.setBrokerName("lryaowenrui");

        DefaultMQPullConsumer consumer  = new DefaultMQPullConsumer("Group_1");
        consumer.start();
        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues("TopicTest");
        log.info("MessageQueues: {}", messageQueues);

//        PullResult pullResult = consumer.pull(messageQueue, "*", 0, 20);
//        log.info("Pull Result: {}", pullResult);
        consumer.shutdown();
    }
}
