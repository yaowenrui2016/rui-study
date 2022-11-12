package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.support.UserHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: yaowr
 * @create: 2022-10-17
 */
@Slf4j
public class AutoApplicationMain {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev01.ywork.me", "jm", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev01.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) {
//        // 检查唯一字段值是否存在
//        boolean exist = checkUniqueField();
//        // 查询应用并且按分类看板返回结果
//        panels();
        // 新增
        create();
        // 查询列表
        List<JSONObject> applications = listApplication();
        if (!CollectionUtils.isEmpty(applications)) {
            // 编辑
            edit(applications.get(0));
            // 获取详情
            getApplication(applications.get(0));
//            // 删除
//            delete(applications.get(0));
            // 删除所有
            deleteAll(applications);
        }
    }


    /**
     * 新增
     */
    private static void create() {
        JSONObject body = FileUtils.loadJSON("AutoApplicationMain/add.json");
        body.put("fdVisitors", UserHelper.getUsers("yaowr", "chenp"));
        body.put("fdEditors", UserHelper.getUsers("yaowr", "chenp"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-application/main/save", body, JSONObject.class);
        log.info("create: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 编辑
     */
    private static void edit(JSONObject application) {
        JSONObject body = FileUtils.loadJSON("AutoApplicationMain/edit.json");
        body.put("fdId", application.getString("fdId"));
        body.put("fdVisitors", UserHelper.getUsers("zhangyl", "lizj"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-application/main/save", body, JSONObject.class);
        log.info("edit: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 检查唯一字段值是否存在
     */
    private static Boolean checkUniqueField() {
        JSONObject body = FileUtils.loadJSON("AutoApplicationMain/checkUniqueField.json");
        MkResponse<Boolean> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-application/main/checkUniqueField", body, Boolean.class);
        log.info("checkUniqueField: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData();
    }


    /**
     * 查询列表
     */
    private static List<JSONObject> listApplication() {
        JSONObject body = new JSONObject();
        body.put("pageSize", 20);
        body.put("offset", 0);
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-application/main/list", body, JSONObject.class);
        log.info("listApplication: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData().getContent();
    }


    /**
     * 查询应用并且按分类看板返回结果
     */
    private static void panels() {
        JSONObject body = new JSONObject();
        body.put("pageSize", 1000);
        body.put("offset", 0);
        JSONObject conditions = new JSONObject();
        conditions.put("fdSource", "DEVELOPED");
        body.put("conditions", conditions);
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-application/main/panels", body, JSONObject.class);
        log.info("panels: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 获取详情
     */
    private static void getApplication(JSONObject application) {
        JSONObject body = new JSONObject();
        body.put("fdId", application.getString("fdId"));
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-application/main/get", body, JSONObject.class);
        log.info("getApplication: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 删除
     */
    private static void delete(JSONObject application) {
        JSONObject body = new JSONObject();
        body.put("fdIds", Collections.singletonList(application.getString("fdId")));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-application/main/deleteAll", body, JSONObject.class);
        log.info("delete: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 删除所有
     */
    private static void deleteAll(List<JSONObject> applications) {
        if (!CollectionUtils.isEmpty(applications)) {
            List<String> ids = applications.stream()
                    .map(application -> application.getString("fdId")).collect(Collectors.toList());
            JSONObject body = new JSONObject();
            body.put("fdIds", ids);
            MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                    "/data/sys-application/main/deleteAll", body, JSONObject.class);
            log.info("deleteAll: request={}, response={}",
                    JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                    JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
            );
        }
    }
}
