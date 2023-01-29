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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 班次
 *
 * @author: yaowr
 * @create: 2022-11-02
 */
@Slf4j
public class AutoTimeWorkClasses {

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
//        // 新增班次
//        create();
        // 查询班次列表
        List<JSONObject> classesList = list();
        if (!CollectionUtils.isEmpty(classesList)) {
//            // 启用/禁用班次
//            updateEnable(classesList.get(0));
//            // 编辑班次
//            edit(classesList.get(0));
//            // 查询班次详情
//            get(classesList.get(0));
//            // 批量删除班次
//            deleteAll(classesList);
        }
    }


    /**
     * 新增班次
     */
    private static void create() {
        JSONObject body = FileUtils.loadJSON("AutoTimeWorkClasses/create.json");
//        JSONObject body = FileUtils.loadJSON("AutoTimeWorkClasses/create_cross.json");
        body.put("fdEditors", UserHelper.getUsers("yaowr", "chenp"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workClasses/create", body, JSONObject.class);
        log.info("create: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 编辑班次
     */
    private static void edit(JSONObject classes) {
        JSONObject body = FileUtils.loadJSON("AutoTimeWorkClasses/edit.json");
        body.put("fdEditors", UserHelper.getUsers("zhangyl", "lizj"));
        body.put("fdId", classes.getString("fdId"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workClasses/edit", body, JSONObject.class);
        log.info("edit: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 启用/禁用班次
     */
    private static void updateEnable(JSONObject classes) {
        JSONObject body = new JSONObject();
        body.put("fdId", classes.getString("fdId"));
        body.put("fdEnabled", !classes.getBooleanValue("fdEnabled"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workClasses/updateEnable", body, JSONObject.class);
        log.info("updateEnable: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 查询列表
     */
    private static List<JSONObject> list() {
        JSONObject body = new JSONObject();
        body.put("pageSize", 20);
        body.put("offset", 0);
        body.put("columns", Arrays.asList("fdId", "fdName", "fdEnabled", "fdColor", "fdCreator", "fdCreateTime", "fdEditors", "fdTimePeriodList"));
        JSONObject sort = new JSONObject();
        sort.put("fdCreateTime", "DESC");
        body.put("sorts", sort);
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-time/workClasses/list", body, JSONObject.class);
        log.info("list: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData().getContent();
    }


    /**
     * 查询班次详情
     */
    private static void get(JSONObject person) {
        JSONObject body = new JSONObject();
        body.put("fdId", person.getString("fdId"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workClasses/get", body, JSONObject.class);
        log.info("get: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 批量删除班次
     */
    private static void deleteAll(List<JSONObject> classesList) {
        JSONObject body = new JSONObject();
        body.put("fdIds", classesList.stream().map(json -> json.getString("fdId")).collect(Collectors.toList()));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workClasses/deleteAll", body, JSONObject.class);
        log.info("deleteAll: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }
}
