package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2022-10-28
 */
@Slf4j
public class AutoSchedule {

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
        addClasses();
    }


    /**
     * 新增班次
     */
    private static void addClasses() {
        JSONObject body = FileUtils.loadJSON("AutoSchedule/addSchedule.json");
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/timeScheduleMain/addSchedule", body, JSONObject.class);
        log.info("addClasses: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 新增排班
     */
    private static void addSchedule() {
        JSONObject body = FileUtils.loadJSON("AutoSchedule/addSchedule.json");
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/timeScheduleMain/addSchedule", body, JSONObject.class);
        log.info("updateSchedule: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 更新排班
     */
    private static void updateSchedule() {
        JSONObject body = FileUtils.loadJSON("AutoSchedule/updateSchedule.json");
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-time/timeScheduleMain/updateSchedule", body, JSONObject.class);
        log.info("updateSchedule: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }
}
