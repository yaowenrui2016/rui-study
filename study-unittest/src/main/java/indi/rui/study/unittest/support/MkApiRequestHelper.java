package indi.rui.study.unittest.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-12-10
 */
@Slf4j
public class MkApiRequestHelper {

    public enum HttpMethod {
        GET,
        POST;
    }

    private final String address;

    private final String xServiceName;

    public MkApiRequestHelper(String address, String xServiceName) {
        this.address = address;
        this.xServiceName = xServiceName;
    }

    // ====================== public method =======================

    public <T> T callApi(String path, JSON json, Class<T> rtnClass) {
        T rtn = null;
        String httpResult = callApi(path, json);
        if (httpResult != null && httpResult.length() > 0) {
            rtn = JSONObject.parseObject(httpResult, rtnClass);
        }
        return rtn;
    }

    public <T> MkResponse<T> callApiForMkResponse(String path, JSON json, Class<T> rtnClass) {
        MkResponse<T> mkResponse = null;
        String httpResult = callApi(path, json);
        if (httpResult != null && httpResult.length() > 0) {
            mkResponse = JSONObject.parseObject(httpResult,
                    new TypeReference<MkResponse<T>>(rtnClass) {
                    });
        }
        return mkResponse;
    }

    public <T> List<T> callApiForList(String path, JSON json, Class<T> rtnClass) {
        List<T> mkResponse = null;
        String httpResult = callApi(path, json);
        if (httpResult != null && httpResult.length() > 0) {
            mkResponse = JSONObject.parseObject(httpResult,
                    new TypeReference<List<T>>(rtnClass) {
                    });
        }
        return mkResponse;
    }

    public String callApi(String path, JSON body) {
        return callApi(HttpMethod.POST, path, body);
    }

    public String callApi(HttpMethod httpMethod, String path, JSON body) {
        String url = address + path;
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("X-SERVICE-NAME", xServiceName);
        httpHeaders.put("content-type", "application/json;charset=utf-8");
        String httpResult = null;
        try {
            switch (httpMethod) {
                case GET:
                    httpResult = HttpClientUtils.httpGet(url, httpHeaders);
                    break;
                case POST:
                    httpResult = HttpClientUtils.httpPost(url, body, httpHeaders);
                    break;
            }
        } catch (Exception e) {
            log.error("Call mk api request error! [url={}, body={}]",
                    url,
                    body != null ? body.toString(SerializerFeature.PrettyFormat) : null,
                    e);
        }
        return httpResult;
    }
}
