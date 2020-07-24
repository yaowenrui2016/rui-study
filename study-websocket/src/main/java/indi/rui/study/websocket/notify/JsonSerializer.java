package indi.rui.study.websocket.notify;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.common.serialization.Serializer;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2020-07-23
 */
public class JsonSerializer implements Serializer {
    @Override
    public void configure(Map configs, boolean isKey) {
    }

    @Override
    public byte[] serialize(String topic, Object data) {
        return JSON.toJSONString(data).getBytes(StandardCharsets.UTF_8);
    }

    @Override
    public void close() {

    }
}
