package indi.rui.study.unittest.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
}
