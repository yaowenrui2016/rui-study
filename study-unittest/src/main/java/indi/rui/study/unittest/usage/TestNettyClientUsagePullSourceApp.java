package indi.rui.study.unittest.usage;

import indi.rui.study.unittest.netty.HttpClient;
import indi.rui.study.unittest.netty.HttpResult;
import indi.rui.study.unittest.support.MkLoginHelper;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class TestNettyClientUsagePullSourceApp {

    private static final String address = "Http://127.0.0.1:8040";

    public static void main(String[] args) throws Exception {
        long beginTime = System.currentTimeMillis();
        // 登录用户
        String xAuthToken = MkLoginHelper.login("yaowr", "1");
        // 拉取来源系统和模块
        String url = address + "/data/sys-notify/sysNotifySourceApp/pull/false";
        HttpHeaders httpHeaders = new DefaultHttpHeaders(true);
        httpHeaders.set("X-AUTH-TOKEN", xAuthToken);
        HttpResult httpResult = HttpClient.post(url, null, httpHeaders);
        httpResult.getContent();
        long endTime = System.currentTimeMillis();
        log.info("pull sourceApp success. [duration={}(s), resData={}]",
                (endTime - beginTime) / 1000f,
                httpResult.getContent());
    }

    private static String getUsername(int i) {
        return "user-" + i;
    }

    private static Integer getNum(String content) {
        return Integer.valueOf(content.substring(content.indexOf('-') + 1, content.indexOf('，')));
    }
}
