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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 排班
 *
 * @author: yaowr
 * @create: 2022-11-02
 */
@Slf4j
public class AutoTimeWorkSchedule {

    private static final long ONE_DAY_MILLI = 24 * 60 * 60 * 1000L;


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
        // 新增排班
        create();
        // 查询排班列表
        List<JSONObject> scheduleList = list();
        if (!CollectionUtils.isEmpty(scheduleList)) {
//            // 编辑排班
//            edit(scheduleList.get(0));
            // 查询排班详情
            get(scheduleList.get(0));
//            // 手动排班
//            manual(scheduleList.get(0));
//            // 批量删除排班
//            deleteAll(scheduleList.get(0));
        }
    }


    /**
     * 新增排班
     */
    private static void create() {
        JSONObject body = FileUtils.loadJSON("AutoTimeworkSchedule/create_fixed.json");
        body.put("fdOrgList", UserHelper.getUsers("zhangyl", "lizj")
                .stream().map(idName -> {
                    JSONObject org = new JSONObject();
                    org.put("fdOrgId", idName.getFdId());
                    return org;
                }).collect(Collectors.toList()));
        body.put("fdEditors", UserHelper.getUsers("yaowr", "chenp"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workSchedule/create", body, JSONObject.class);
        log.info("create: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 编辑排班
     */
    private static void edit(JSONObject schedule) {
        JSONObject body = FileUtils.loadJSON("AutoTimeworkSchedule/edit.json");
        body.put("fdEditors", UserHelper.getUsers("zhangyl", "lizj"));
        body.put("fdOrgList", UserHelper.getUsers("zhangyl", "lizj")
                .stream().map(idName -> {
                    JSONObject org = new JSONObject();
                    org.put("fdOrgId", idName.getFdId());
                    return org;
                }).collect(Collectors.toList()));
        body.put("fdId", schedule.getString("fdId"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workSchedule/edit", body, JSONObject.class);
        log.info("edit: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 查询列表
     */
    private static List<JSONObject> list() {
        JSONObject body = FileUtils.loadJSON("AutoTimeworkSchedule/list.json");
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-time/workSchedule/list", body, JSONObject.class);
        log.info("list: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData().getContent();
    }


    /**
     * 查询排班详情
     */
    private static void get(JSONObject schedule) {
        JSONObject body = new JSONObject();
        body.put("fdId", schedule.getString("fdId"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workSchedule/get", body, JSONObject.class);
        log.info("get: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }

    private static void manual(JSONObject schedule) {
        schedule.put("fdClassesList", Collections.singletonList(getClasses()));
        for (Object workTimeObj : schedule.getJSONArray("fdWorkTimeList")) {
            JSONObject workTime = (JSONObject) workTimeObj;
            workTime.put("fdWorkClasses", getClasses());
        }
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workSchedule/get", schedule, JSONObject.class);
        log.info("manual: request={}, response={}",
                JSONObject.toJSONString(schedule, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 批量删除排班
     */
    private static void deleteAll(JSONObject classes) {
        JSONObject body = new JSONObject();
        body.put("fdIds", Collections.singletonList(classes.getString("fdId")));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workSchedule/deleteAll", body, JSONObject.class);
        log.info("deleteAll: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 查询班次
     */
    private static JSONObject getClasses() {
        JSONObject body = new JSONObject();
        body.put("pageSize", 20);
        body.put("offset", 0);
        body.put("columns", Arrays.asList("fdId", "fdName"));
        JSONObject sort = new JSONObject();
        sort.put("fdCreateTime", "DESC");
        body.put("sorts", sort);
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-time/workClasses/list", body, JSONObject.class);
        JSONObject idName = null;
        if (!CollectionUtils.isEmpty(mkResponse.getData().getContent())) {
            idName = new JSONObject();
            JSONObject classes = mkResponse.getData().getContent().get(0);
            idName.put("fdId", classes.getString("fdId"));
            idName.put("fdName", classes.getString("fdName"));
        }
        return idName;
    }
}
