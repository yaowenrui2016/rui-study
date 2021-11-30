package indi.rui.study.mktools.callapi;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.mktools.netty.HttpClient;
import indi.rui.study.mktools.netty.HttpResult;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class CallApiResumeSourceApp {

    private static final String address = System.getProperty("mk.address",
            "Http://127.0.0.1:8040");
    private static final String xServiceName = System.getProperty("mk.xServiceName",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) {
        // 将所有待办置已办
        try {
            String url = address + "/api/sys-notify/sysNotifySourceMApps/resumeSourceApp";
            HttpHeaders httpHeaders = new DefaultHttpHeaders();
            httpHeaders.set("X-SERVICE-NAME", xServiceName);
            httpHeaders.set("content-type", "application/json;charset=utf-8");
            HttpResult httpResult = HttpClient.post(url, null, httpHeaders);
            log.info("resumeSourceApp result: \n{}", JSONObject.toJSONString(
                    JSONObject.parseObject(httpResult.getContent()), SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            log.error("resumeSourceApp exception", e);
        }
    }
}
