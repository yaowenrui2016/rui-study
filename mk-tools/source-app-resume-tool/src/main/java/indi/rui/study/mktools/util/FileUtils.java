package indi.rui.study.mktools.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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

    public static JSONObject loadJSON(String jsonPath) {
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                jsonPath)).getFile();
        String sendJson = FileUtils.readFileToString(filePath, "utf-8");
        return JSONObject.parseObject(sendJson);
    }

    public static JSONObject loadJSON(String filename, Class clazz) {
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                "json/" + clazz.getSimpleName() + "/" + filename)).getFile();
        String sendJson = FileUtils.readFileToString(filePath, "utf-8");
        return JSONObject.parseObject(sendJson);
    }
}
