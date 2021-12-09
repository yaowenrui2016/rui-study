package indi.rui.study.unittest.callapi;

import com.alibaba.fastjson.JSON;
import indi.rui.study.unittest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class CallApiRightInitialize {

    private static final String address = "Http://127.0.0.1:8040";
    private static final String xServiceName = "73456775666d4c416f73776139584a4131432f6847413d3d";

    public static void main(String[] args) throws Exception {
        // 初始化权限
        initialize();
    }

    private static void initialize() throws Exception {
        // 拉取来源系统和模块
        String url = address + "/api/sys-right/initialize";
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("X-SERVICE-NAME", xServiceName);
        String httpResult = HttpClientUtils.httpPost(url, null, httpHeaders);
        Boolean success = JSON.parseObject(httpResult,
                Boolean.class);
        log.info("Right initialize {}", success);
    }
}
