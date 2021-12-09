package indi.rui.study.unittest.support;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkLoginResult;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这是要给线程不安全的帮助类，用于处理MK系统/data请求
 *
 * @author: yaowr
 * @create: 2021-12-08
 */
@Slf4j
public class MkDataRequestHelper {

    private final MkLoginResult loginResult;

    public MkDataRequestHelper(String address, String username, String password) {
        // 登录用户
        this.loginResult = MkLoginHelper.doLogin(address, username, password);
    }

    // ====================== public method =======================

    public <T> MkResponse<T> callData(String url, JSONObject json, Class<T> rtnClass) {
        MkResponse<T> mkResponse = null;
        String httpResult = CallData(url, json);
        if (httpResult != null && httpResult.length() > 0) {
            mkResponse = JSONObject.parseObject(httpResult,
                    new TypeReference<MkResponse<T>>(rtnClass) {
                    });
        }
        return mkResponse;
    }

    public <T> MkResponse<List<T>> callDataForList(String url, JSONObject json, Class<T> rtnClass) {
        MkResponse<List<T>> mkResponse = null;
        String httpResult = CallData(url, json);
        if (httpResult != null && httpResult.length() > 0) {
            mkResponse = JSONObject.parseObject(httpResult,
                    new TypeReference<MkResponse<List<T>>>(rtnClass) {
                    });
        }
        return mkResponse;

    }

    public <T> MkResponse<QueryResult<T>> callDataForMkQueryResult(String url, JSONObject json, Class<T> rtnClass) {
        MkResponse<QueryResult<T>> mkResponse = null;
        String httpResult = CallData(url, json);
        if (httpResult != null && httpResult.length() > 0) {
            mkResponse = JSONObject.parseObject(httpResult,
                    new TypeReference<MkResponse<QueryResult<T>>>(rtnClass) {
                    });
        }
        return mkResponse;

    }

    public String CallData(String url, JSONObject json) {
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("X-AUTH-TOKEN", loginResult.getXAuthToken());
        httpHeaders.put("content-type", "application/json;charset=utf-8");
        String httpResult = null;
        try {
            httpResult = HttpClientUtils.httpPost(url, json, httpHeaders);
        } catch (Exception e) {
            log.error("call data request error! url={}, json={}, response={}",
                    url,
                    json.toString(SerializerFeature.PrettyFormat),
                    httpResult,
                    e);
        }
        return httpResult;
    }
}
