package indi.rui.study.unittest.other;

import indi.rui.study.unittest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2022-08-06
 */
@Slf4j
public class TestControllerParam {

    public static void main(String[] args) {
        testDynamic();
    }

    private static void testIsEdit() {
        String url = "http://localhost:13004/study-hibernate/param/test?isEdit=true";
        try {
            String result = HttpClientUtils.httpGet(url, null);
            log.info("param test: {}", result);
        } catch (Exception e) {
            log.error("param test error", e);
        }
    }

    private static void testDynamic() {
        String url = "http://localhost:13004/study-hibernate/param/dynamic?foo=bar&users=a&users=b&users=c";
        try {
            String result = HttpClientUtils.httpGet(url, null);
            log.info("dynamic test: {}", result);
        } catch (Exception e) {
            log.error("dynamic test error", e);
        }
    }
}
