package indi.rui.study.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Iterator;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2020-09-07
 */
@Slf4j
public class MyConsumerInterceptor implements ConsumerInterceptor<String, String> {

    @Override
    public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
//        System.err.println("消息来了");
        return records;
    }

    @Override
    public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
        Iterator<Map.Entry<TopicPartition, OffsetAndMetadata>> iterator = offsets.entrySet().iterator();
//        log.warn("----------------------{}", offsets.size());
        while (iterator.hasNext()) {
            Map.Entry<TopicPartition, OffsetAndMetadata> entry = iterator.next();
            int partition = entry.getKey().partition();
            if (partition == 0) {
                log.warn("提交了: 分区({}} -> {}", partition, entry.getValue().offset());
            }
        }
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
