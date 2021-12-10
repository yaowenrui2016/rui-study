package indi.rui.study.unittest.support;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-12-10
 */
@Slf4j
public class MkApiRequestHelper {

    private final String address;

    private final String xServiceName;

    public MkApiRequestHelper(String address, String xServiceName) {
        this.address = address;
        this.xServiceName = xServiceName;
    }

    // ====================== public method =======================

    public <T> T callApi(String path, JSONObject json, Class<T> rtnClass) {
        T rtn = null;
        String httpResult = callApi(path, json);
        if (httpResult != null && httpResult.length() > 0) {
            rtn = JSONObject.parseObject(httpResult,
                    new TypeReference<T>(rtnClass) {
                    });
        }
        return rtn;
    }

    public <T> MkResponse<T> callApiForMkResponse(String path, JSONObject json, Class<T> rtnClass) {
        MkResponse<T> mkResponse = null;
        String httpResult = callApi(path, json);
        if (httpResult != null && httpResult.length() > 0) {
            mkResponse = JSONObject.parseObject(httpResult,
                    new TypeReference<MkResponse<T>>(rtnClass) {
                    });
        }
        return mkResponse;
    }

    public String callApi(String path, JSONObject body) {
        String url = address + path;
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("X-SERVICE-NAME", xServiceName);
        httpHeaders.put("content-type", "application/json;charset=utf-8");
        String httpResult = null;
        try {
            httpResult = HttpClientUtils.httpPost(url, body, httpHeaders);
        } catch (Exception e) {
            log.error("Call mk api request error! [url={}, body={}]",
                    url,
                    body != null ? body.toString(SerializerFeature.PrettyFormat) : null,
                    e);
        }
        return httpResult;
    }
}
