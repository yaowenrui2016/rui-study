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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class AutoTimeWorkScheduleManual {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


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
        // 创建手动排班
        create();
        // 查询手动排班列表
        List<JSONObject> scheduleList = list();
        if (!CollectionUtils.isEmpty(scheduleList)) {
//            // 编辑排班
//            edit(scheduleList.get(0));
            // 手动排班
            manualSchedule(get(scheduleList.get(0)));
//            // 批量删除排班
//            deleteAll(scheduleList);
        }
//        // 工时模拟
//        mock();
    }


    /**
     * 新增排班
     */
    private static void create() {
        try {
            JSONObject body = new JSONObject();
            body.put("fdName", "yaoTest手动排班");
            body.put("fdType", 2);
            body.put("fdBegin", FORMAT.parse("2022-12-01 03:45:00"));
            body.put("fdEnd", FORMAT.parse("2022-12-31 03:45:00"));
            body.put("fdOrgList", UserHelper.getUserIds("yaowr")
                    .stream().map(userId -> {
                        JSONObject org = new JSONObject();
                        org.put("fdOrgId", userId);
                        return org;
                    }).collect(Collectors.toList()));
            body.put("fdEditors", UserHelper.getUsers("zhangyl"));
            MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                    "/data/sys-time/workSchedule/create", body, JSONObject.class);
            log.info("create: request={}, response={}",
                    JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                    JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 编辑排班
     */
    private static void edit(JSONObject schedule) {
        try {
            JSONObject body = FileUtils.loadJSON("AutoTimeworkSchedule/edit.json");
            body.put("fdId", schedule.getString("fdId"));
            body.put("fdName", schedule.getString("fdName") + "_-");
            body.put("fdType", 2);
            body.put("fdBegin", FORMAT.parse("2022-11-01 03:45:00"));
            body.put("fdEnd", FORMAT.parse("2023-01-31 03:45:00"));
            body.put("fdOrgList", UserHelper.getUsers("yaowr")
                    .stream().map(idName -> {
                        JSONObject org = new JSONObject();
                        org.put("fdOrgId", idName.getFdId());
                        return org;
                    }).collect(Collectors.toList()));
            body.put("fdVersion", schedule.getIntValue("fdVersion"));
            body.put("fdSourceId", schedule.getString("fdSourceId"));
            body.put("newVersion", false);
//            body.put("newVersionDay", "2023-06-01");
            body.put("fdEditors", UserHelper.getUsers("zhangyl"));
            MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                    "/data/sys-time/workSchedule/edit", body, JSONObject.class);
            log.info("edit: request={}, response={}",
                    JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                    JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
            );
        } catch (ParseException e) {
            log.error("edit error!", e);
        }
    }


    /**
     * 编辑排班
     */
    private static void mock() {
        try {
            JSONObject body = new JSONObject();
            body.put("fdOrgIds", UserHelper.getUserIds("chenp"));
            body.put("beginDate", FORMAT.parse("2022-11-20 03:45:00"));
            body.put("endDate", FORMAT.parse("2023-02-1 03:45:00"));
            MkResponse<List<JSONObject>> mkResponse = mkDataRequestHelper.callDataForList(
                    "/data/sys-time/workTime/mock", body, JSONObject.class);
            log.info("mock: request={}, response={}",
                    JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                    JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
            );
        } catch (ParseException e) {
            log.error("mock error!", e);
        }
    }


    /**
     * 查询列表
     */
    private static List<JSONObject> list() {
        JSONObject body = FileUtils.loadJSON("AutoTimeworkSchedule/list.json");
        body.getJSONObject("conditions").put("fdType", 2);
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
    private static JSONObject get(JSONObject schedule) {
        JSONObject body = new JSONObject();
        body.put("fdId", schedule.getString("fdId"));
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workSchedule/get", body, JSONObject.class);
        log.info("get: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData();
    }

    private static void manualSchedule(JSONObject schedule) {
        schedule.put("fdClassesList", Collections.singletonList(getClasses()));
        int count = 0;
        for (Object workTimeObj : schedule.getJSONArray("fdWorkTimeList")) {
//            if (count++ < 10) {
            JSONObject workTime = (JSONObject) workTimeObj;
            workTime.put("fdWorkClasses", getClasses());
//            }
        }
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/workSchedule/manualSchedule", schedule, JSONObject.class);
        log.info("manualSchedule: request={}, response={}",
                JSONObject.toJSONString(schedule, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 批量删除排班
     */
    private static void deleteAll(List<JSONObject> classesList) {
        JSONObject body = new JSONObject();
        body.put("fdIds", classesList.stream().map(json -> json.getString("fdId")).collect(Collectors.toList()));
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
