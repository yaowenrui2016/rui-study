package indi.rui.study.unittest.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkLoginResult;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.dto.UserInfo;
import indi.rui.study.unittest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
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

    private MkLoginResult loginResult;

    private String address;

    public MkDataRequestHelper() {
    }

    public MkDataRequestHelper(String address, String username, String password) {
        this(address, username, password, null);
    }

    public MkDataRequestHelper(String address, String username, String password, String pubKeyFile) {
        this.address = address;
        // 登录用户
        this.loginResult = MkLoginHelper.doLoginWithPubKeyFile(address, username, password, pubKeyFile);
    }

    // ====================== public method =======================

    public JSONObject getUserInfo() {
        return this.loginResult.getUserInfo();
    }

    /**
     * 发送GET请求
     *
     * @param url
     * @return
     */
    public String httpGet(String url) {
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("content-type", "application/json;charset=utf-8");
        String httpResult = null;
        try {
            httpResult = HttpClientUtils.httpGet(url, httpHeaders);
        } catch (Exception e) {
            log.error("Http get request error! [url={}, httpHeaders={}]",
                    url,
                    JSONObject.toJSONString(httpHeaders),
                    e);
        }
        return httpResult;
    }

    /**
     * 文件下载
     *
     * @param path
     * @param body
     * @param downloadPath
     */
    public String callDataDownload(String path, JSONObject body, String downloadPath) {
        String url = address + path;
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("X-AUTH-TOKEN", loginResult.getXAuthToken());
        httpHeaders.put("content-type", "application/json;charset=utf-8");
        String filename = null;
        try {
            filename = HttpClientUtils.httpPostDownload(url, body, httpHeaders, downloadPath);
        } catch (Exception e) {
            log.error("Call mk data download file error! [url={}, body={}]",
                    url,
                    body != null ? body.toString(SerializerFeature.PrettyFormat) : null,
                    e);
        }
        return filename;
    }

    /**
     * 文件上传
     *
     * @param path
     * @param uploadFile
     */
    public String callDataUpload(String path, File uploadFile) {
        String url = address + path;
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("X-AUTH-TOKEN", loginResult.getXAuthToken());
        // 不能设置content-type: multipart/form-data，否则导致"the request was rejected because no multipart boundary was found"
//        httpHeaders.put("content-type", "multipart/form-data");
        try {
            return HttpClientUtils.httpPostUpload(url, httpHeaders, uploadFile);
        } catch (Exception e) {
            log.error("Call mk data upload file error! [url={}, uploadFile={}]",
                    url,
                    uploadFile.getName(),
                    e);
        }
        return null;
    }

    public MkResponse<?> callData(String path, JSON json) {
        return callData(path, json, Void.class);
    }

    public <T> MkResponse<T> callData(String path, JSON json, Class<T> rtnClass) {
        MkResponse<T> mkResponse = null;
        String httpResult = callDataForString(path, json);
        if (httpResult != null && httpResult.length() > 0) {
            mkResponse = JSONObject.parseObject(httpResult,
                    new TypeReference<MkResponse<T>>(rtnClass) {
                    });
        }
        return mkResponse;
    }

    public <T> MkResponse<List<T>> callDataForList(String path, JSON json, Class<T> rtnClass) {
        MkResponse<List<T>> mkResponse = null;
        String httpResult = callDataForString(path, json);
        if (httpResult != null && httpResult.length() > 0) {
            mkResponse = JSONObject.parseObject(httpResult,
                    new TypeReference<MkResponse<List<T>>>(rtnClass) {
                    });
        }
        return mkResponse;
    }

    public MkResponse<List<?>> callDataForList(String path, JSONObject json) {
        MkResponse<List<?>> mkResponse = null;
        String httpResult = callDataForString(path, json);
        if (httpResult != null && httpResult.length() > 0) {
            mkResponse = JSONObject.parseObject(httpResult,
                    new TypeReference<MkResponse<List<?>>>() {
                    });
        }
        return mkResponse;
    }

    public <T> MkResponse<QueryResult<T>> callDataForMkQueryResult(String path, JSONObject json, Class<T> rtnClass) {
        MkResponse<QueryResult<T>> mkResponse = null;
        String httpResult = callDataForString(path, json);
        if (httpResult != null && httpResult.length() > 0) {
            mkResponse = JSONObject.parseObject(httpResult,
                    new TypeReference<MkResponse<QueryResult<T>>>(rtnClass) {
                    });
        }
        return mkResponse;
    }

    public MkResponse<QueryResult<?>> callDataForMkQueryResult(String path, JSONObject json) {
        MkResponse<QueryResult<?>> mkResponse = null;
        String httpResult = callDataForString(path, json);
        if (httpResult != null && httpResult.length() > 0) {
            mkResponse = JSONObject.parseObject(httpResult,
                    new TypeReference<MkResponse<QueryResult<?>>>() {
                    });
        }
        return mkResponse;
    }

    public MkResponse<JSONObject> CallDataForJson(String path, JSONObject json) {
        MkResponse<JSONObject> mkResponse = null;
        String httpResult = callDataForString(path, json);
        if (httpResult != null && httpResult.length() > 0) {
            mkResponse = JSONObject.parseObject(httpResult,
                    new TypeReference<MkResponse<JSONObject>>() {
                    });
        }
        return mkResponse;
    }

    // =================== 私有方法 ================== //

    public String callDataForString(String path, JSON body) {
        String url = address + path;
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("X-AUTH-TOKEN", loginResult.getXAuthToken());
        httpHeaders.put("content-type", "application/json;charset=utf-8");
        String httpResult = null;
        try {
            httpResult = HttpClientUtils.httpPost(url, body, httpHeaders);
        } catch (Exception e) {
            log.error("Call mk data request error! [url={}, body={}]",
                    url,
                    body != null ? body.toString(SerializerFeature.PrettyFormat) : null,
                    e);
        }
        return httpResult;
    }
}
