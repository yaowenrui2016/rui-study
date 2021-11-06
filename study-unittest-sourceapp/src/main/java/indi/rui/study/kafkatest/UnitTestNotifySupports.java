package indi.rui.study.kafkatest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import indi.rui.study.kafkatest.dto.MkResponse;
import indi.rui.study.kafkatest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-11-05
 */
@Slf4j
public class UnitTestNotifySupports {

    private static final String DEFAULT_ADDRESS = "http://localhost:8040";

    private static final String DEFAULT_X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";


    public static void main(String[] args) {
        // 请求地址
        String url = DEFAULT_ADDRESS + "/api/sys-notifybus/sysNotifyComponent/supports";
        // 请求体
//        JSON body = new JSONArray(Arrays.asList(codes));
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", DEFAULT_X_SERVICE_NAME);
        try {
            String response = HttpClientUtils.httpPost(url, null, header);
            MkResponse<JSONArray> mkResponse = JSON.parseObject(response, new TypeReference<MkResponse<JSONArray>>() {
            });
            if (mkResponse.isSuccess()) {
                log.info("result: \n{}", JSON.toJSONString(mkResponse.getData()));
            } else {
                log.error("failure: {}", JSON.toJSONString(mkResponse.getMsg()));
            }
        } catch (Exception e) {
            log.error("delete module exception! ", e);
        }
    }
}
