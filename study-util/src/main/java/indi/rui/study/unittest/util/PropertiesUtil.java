package indi.rui.study.unittest.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author: yaowr
 * @create: 2022-07-27
 */
@Slf4j
public class PropertiesUtil {

    /**
     * 加载属性文件
     */
    private static Properties properties = new Properties();


    static {
        try {
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config/application.properties");
            properties.load(inputStream);
            log.info("load properties: {}", JSONObject.toJSONString(properties, SerializerFeature.PrettyFormat));
        } catch (IOException e) {
            throw new RuntimeException("Load 'config/application.properties' error!");
        }
    }

    public static String getProperty(String key) {
        return (String) properties.get(key);
    }
}
