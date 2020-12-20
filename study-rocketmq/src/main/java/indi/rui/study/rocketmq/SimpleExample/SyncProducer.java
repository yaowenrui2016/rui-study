package indi.rui.study.rocketmq.SimpleExample;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author: yaowr
 * @create: 2020-12-09
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        // Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("Group2");
        // Specify name server addresses.
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setSendMsgTimeout(6000);
//        producer.setVipChannelEnabled(false);
//        producer.setRetryTimesWhenSendFailed(3);
        // Launch the instance.
        producer.start();
        for (int i = 0; i < 10; i++) {
            // Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("TopicTest" /* Topic */,
                    "TagA" /* Tag */,
                    ("Hello RocketMQ, 我是老王" + i + "，隔壁那个... " +
                            i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body */
            );
            // Call send message to deliver message to one of brokers.
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        // Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
