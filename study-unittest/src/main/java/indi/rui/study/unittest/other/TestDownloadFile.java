package indi.rui.study.unittest.other;

import indi.rui.study.unittest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2022-08-06
 */
@Slf4j
public class TestDownloadFile {

    public static void main(String[] args) {
        String downloadPath = "";
        String url = "http://localhost:13004/study-hibernate/download/normal";
        try {
            String filename = HttpClientUtils.httpGetDownload(url, null, null, downloadPath);
            log.info("download success: {}", filename);
        } catch (Exception e) {
            log.error("download error", e);
        }
    }
}
