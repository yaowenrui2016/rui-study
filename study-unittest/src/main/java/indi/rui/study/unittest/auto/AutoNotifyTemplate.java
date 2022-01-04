package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: yaowr
 * @create: 2021-12-29
 */
@Slf4j
public class AutoNotifyTemplate {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev02.ywork.me", "yuxd", "1");

    public static void main(String[] args) {
        String name = "Yao Test " + 0;
        String code = "TMP_" + 0;
        addTemplateRPC(name, code);
        addTemplateRPC(name, code);
        List<JSONObject> temps = findTemplateRPC();
        for (String id : temps.stream()
                .map(jsonObj -> (String) jsonObj.get("fdId"))
                .collect(Collectors.toList())) {
            getTemplateRPC(id);
        }
        for (String tmpCode : temps.stream()
                .map(jsonObj -> (String) jsonObj.get("fdCode"))
                .collect(Collectors.toList())) {
            findByCodeRPC(tmpCode);
        }
        findAvailable();
    }

    private static void addTemplateRPC(String name, String code) {
        // 新建模板
        JSONObject json = FileUtils.loadJSON("add.json", AutoNotifyTemplate.class);
        json.put("fdCode", name);
        json.put("fdName", code);
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-notify/sysNotifyTemplate/add", json, JSONObject.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Find template error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Add template: {}", JSONObject.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
    }

    private static List<JSONObject> findTemplateRPC() {
        // 查询模板
        JSONObject json = new JSONObject();
        json.put("pageSize", 1000);
        Map<String, Object> conditions = (Map<String, Object>) json.computeIfAbsent(
                "conditions", (k) -> new HashMap<>());
        Map<String, Object> sorts = (Map<String, Object>) json.computeIfAbsent(
                "sorts", (k) -> new HashMap<>());
        sorts.put("fdCreateTime", "DESC");
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-notify/sysNotifyTemplate/list", json, JSONObject.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Find template error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Find template: {}", JSONObject.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
        return mkResponse.getData().getContent();
    }

    private static void findAvailable() {
        // 查询模板
        MkResponse<List<JSONObject>> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-notify/sysNotifyTemplate/findAvailable", null, JSONObject.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Find template error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Find available template: {}", JSONObject.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
    }

    private static void getTemplateRPC(String id) {
        // 获取模板
        JSONObject json = new JSONObject();
        json.put("fdId", id);
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.CallDataForJson(
                "/data/sys-notify/sysNotifyTemplate/get", json);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get template error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Get template: {}", JSONObject.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
    }

    private static void findByCodeRPC(String code) {
        // 获取模板
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.CallDataForJson(
                "/data/sys-notify/sysNotifyTemplate/findByCode?code=" + code, null);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Find template by code error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Find template by code: {}", JSONObject.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
    }
}
