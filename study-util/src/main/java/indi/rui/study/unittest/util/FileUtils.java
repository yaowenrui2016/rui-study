package indi.rui.study.unittest.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author: yaowr
 * @create: 2021-10-20
 */
@Slf4j
public class FileUtils {

    public static String readFileToString(String filePath, String encode) {
        StringBuffer sb = new StringBuffer();
        File file = new File(filePath);
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(file));
            byte[] buff = new byte[1024];
            int len;
            while ((len = in.read(buff)) != -1) {
                sb.append(new String(buff, 0, len, encode));
            }
        } catch (IOException e) {
            log.error("IO exception when read file [{}]", filePath, e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    log.error("IO exception when close file [{}]", filePath, e);
                }
            }
        }
        return sb.toString();
    }

    public static JSONObject loadJSON(String filename) {
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                "json/" + filename)).getFile();
        String sendJson = FileUtils.readFileToString(filePath, "utf-8");
        return JSONObject.parseObject(sendJson);
    }

    public static <T> T loadJSON(String filename, Class<T> clazz) {
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                "json/" + filename)).getFile();
        String sendJson = FileUtils.readFileToString(filePath, "utf-8");
        return JSONObject.parseObject(sendJson, clazz);
    }

    public static <T> List<T> loadJSON4List(String filename, Class<T> type) {
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                "json/" + filename)).getFile();
        String sendJson = FileUtils.readFileToString(filePath, "utf-8");
        return JSONObject.parseObject(sendJson, new TypeReference<List<T>>(type) {
        });
    }
}
