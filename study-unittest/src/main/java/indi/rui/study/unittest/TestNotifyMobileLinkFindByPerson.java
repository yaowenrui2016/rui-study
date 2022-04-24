package indi.rui.study.unittest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.UserInfo;
import indi.rui.study.unittest.support.MkLoginHelper;
import indi.rui.study.unittest.util.FileUtils;
import indi.rui.study.unittest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-12-09
 */
@Slf4j
public class TestNotifyMobileLinkFindByPerson {

    private static final String address = "Http://127.0.0.1:8040";
    private static final String xServiceName = "73456775666d4c416f73776139584a4131432f6847413d3d";

    public static void main(String[] args) {
//        send();
        findByPerson();

        UserInfo userInfo = MkLoginHelper.loginLoadUserInfo(address, "yaowr", "1");
        log.info("userInfo:\n{}", JSONObject.toJSONString(userInfo, SerializerFeature.PrettyFormat));
        findAll(userInfo.getUserId());
//        remove();
    }

    private static void findByPerson() {
        try {
            String url = address + "/api/sys-notify/baseSysNotify/findByPerson";
            Map<String, String> httpHeaders = new HashMap<>();
            httpHeaders.put("X-SERVICE-NAME", xServiceName);
            httpHeaders.put("content-type", "application/json;charset=utf-8");
//            JSONObject json = FileUtils.loadJSON("TestNotifyMobileLinkFindByPerson/findByPerson.json");
            JSONObject json = FileUtils.loadJSON("TestNotifyMobileLinkFindByPerson/find_by_person.json");
            String httpResult = HttpClientUtils.httpPost(url, json, httpHeaders);
            log.info("{}", JSONObject.parseObject(httpResult).toString(SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            log.error("send error", e);
        }
    }

    private static void findAll(String ownerId) {
        try {
            String url = address + "/api/sys-notify/sysNotifyTodo/findAll";
            Map<String, String> httpHeaders = new HashMap<>();
            httpHeaders.put("X-SERVICE-NAME", xServiceName);
            httpHeaders.put("content-type", "application/json;charset=utf-8");
            JSONObject json = FileUtils.loadJSON("TestNotifyMobileLinkFindByPerson/findAll.json");
            ((JSONObject) json.get("conditions")).put("fdOwnerId", ownerId);
            String httpResult = HttpClientUtils.httpPost(url, json, httpHeaders);
            log.info("{}", JSONObject.parseObject(httpResult).toString(SerializerFeature.PrettyFormat));
        } catch (Exception e) {
            log.error("send error", e);
        }
    }

    private static void send() {
        try {
            String url = address + "/api/sys-notifybus/sysNotifyComponent/send";
            Map<String, String> httpHeaders = new HashMap<>();
            httpHeaders.put("X-SERVICE-NAME", xServiceName);
            httpHeaders.put("content-type", "application/json;charset=utf-8");
            JSONObject json = FileUtils.loadJSON("TestNotifyMobileLinkFindByPerson/send.json");
            json.put("entityKey", System.currentTimeMillis());
            String httpResult = HttpClientUtils.httpPost(url, json, httpHeaders);
            JSON.parseObject(httpResult,
                    new TypeReference<MkResponse<String>>() {
                    }).getData();
        } catch (Exception e) {
            log.error("send error", e);
        }
    }

    private static void remove() {
        try {
            String url = address + "/api/sys-notifybus/sysNotifyComponent/removeAll";
            Map<String, String> httpHeaders = new HashMap<>();
            httpHeaders.put("X-SERVICE-NAME", xServiceName);
            httpHeaders.put("content-type", "application/json;charset=utf-8");
            JSONObject json = FileUtils.loadJSON("TestNotifyMobileLinkFindByPerson/remove.json");
            String httpResult = HttpClientUtils.httpPost(url, json, httpHeaders);
            JSON.parseObject(httpResult,
                    new TypeReference<MkResponse<String>>() {
                    }).getData();
        } catch (Exception e) {
            log.error("send error", e);
        }
    }
}
