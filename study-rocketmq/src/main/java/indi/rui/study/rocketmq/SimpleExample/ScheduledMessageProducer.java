package indi.rui.study.rocketmq.SimpleExample;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

/**
 * @author: yaowr
 * @create: 2020-12-10
 */
@Slf4j
public class ScheduledMessageProducer {
    public static void main(String[] args) throws Exception {
        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("Group2");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setSendMsgTimeout(6000);
        // Launch producer
        producer.start();
        int totalMessagesToSend = 1;
        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TopicTest", ("Hello scheduled message " + i).getBytes());
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(3);
            // Send the message
            SendResult result = producer.send(message);
            log.info("Send Result: {}", result);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }
}
