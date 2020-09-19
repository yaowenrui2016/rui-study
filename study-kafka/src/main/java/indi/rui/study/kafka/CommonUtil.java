package indi.rui.study.kafka;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: yaowr
 * @create: 2020-09-19
 */
public class CommonUtil {

    public static String printOffsets(Map<TopicPartition, OffsetAndMetadata> offsets) {
        Set<String> tmp = new HashSet<>();
        Iterator<Map.Entry<TopicPartition, OffsetAndMetadata>> iterator = offsets.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<TopicPartition, OffsetAndMetadata> entry = iterator.next();
            tmp.add(entry.getKey() + " => " + entry.getValue().offset());
        }
        StringBuffer buf = new StringBuffer();
        for (String s : tmp) {
            buf.append("\r\n").append(s);
        }
        return buf.toString();
    }

    public static <T>String printTopicPartition(Collection<T> c) {
        List<String> tmp = c.stream().map(e -> e.toString()).sorted().collect(Collectors.toList());
        StringBuffer buf = new StringBuffer();
        for (String s : tmp) {
            buf.append("\r\n").append(s);
        }
        return buf.toString();
    }
}
