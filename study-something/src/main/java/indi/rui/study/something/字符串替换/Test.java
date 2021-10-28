package indi.rui.study.something.字符串替换;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-10-28
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        JSONObject jsonObj = new JSONObject();
        String url = "http://www.baidu.com?\nkeyword=abc==\nabc";
        jsonObj.put("url", url);

        log.info("url={}", url);
        log.info("json={}", jsonObj.toJSONString());

        if (url.contains("\n")) {
            url = url.replace("\n", "");
        }
        log.info("url={}", url);
    }
}
