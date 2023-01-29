package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.support.UserHelper;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 排班
 *
 * @author: yaowr
 * @create: 2022-11-02
 */
@Slf4j
public class AutoTimeWorkComponent {

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
//        // 获取排班列表
//        getScheduleList();
        // 获取工作时间段
        getWorkPeriod();
//        // 获取最后期限
//        getDeadLine();
        // 获取上班时间长度
        getDuration();
    }


    /**
     * 获取排班列表
     */
    private static void getScheduleList() {
        JSONObject body = new JSONObject();
        List<JSONObject> result = mkApiRequestHelper.callApiForList(
                "/api/sys-time/workScheduleComponent/getScheduleList", body, JSONObject.class);
        log.info("getScheduleList: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(result, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 获取工作区间
     */
    private static void getWorkPeriod() {
        try {
            JSONObject body = new JSONObject();
            body.put("beginTime", FORMAT.parse("2022-12-08 15:50:00"));
            body.put("endTime", FORMAT.parse("2022-12-09 19:02:00"));
            body.put("orgId", UserHelper.getUserId("yaowr"));
//            body.put("scheduleId", "1gjnss0u4w9w1kw3scld252iq53gf273e8w0");
            JSONObject result = mkApiRequestHelper.callApi(
                    "/api/sys-time/workScheduleComponent/getWorkPeriod", body, JSONObject.class);
            JSONArray periods = result.getJSONArray("periods");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            for (int i = 0; i < periods.size(); i++) {
                JSONObject period = periods.getJSONObject(i);
                String begin = formatter.format(period.getLong("begin"));
                period.put("begin", begin);
                String end = formatter.format(period.getLong("end"));
                period.put("end", end);
            }
            log.info("getWorkPeriod: request={}, response={}",
                    JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                    JSONObject.toJSONString(result, SerializerFeature.PrettyFormat)
            );
        } catch (ParseException e) {
            log.error("getWorkPeriod error!", e);
        }
    }


    /**
     * 获取最后期限
     */
    private static void getDeadLine() {
        try {
            JSONObject body = new JSONObject();
            body.put("beginTime", FORMAT.parse("2022-12-02 03:45:00"));
            body.put("duration", 150);
            body.put("orgId", UserHelper.getUserId("yaowr"));
//            body.put("scheduleId", "1gjnss0u4w9w1kw3scld252iq53gf273e8w0");
            JSONObject result = mkApiRequestHelper.callApi(
                    "/api/sys-time/workScheduleComponent/getDeadLine", body, JSONObject.class);
            log.info("getDeadLine: request={}, response={}",
                    JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                    JSONObject.toJSONString(result, SerializerFeature.PrettyFormat)
            );
        } catch (ParseException e) {
            log.error("getDeadLine error!", e);
        }
    }


    /**
     * 获取上班时间长度
     */
    private static void getDuration() {
        try {
            JSONObject body = new JSONObject();
//            body.put("beginTime", FORMAT.parse("2022-12-08 15:50:00"));
//            body.put("endTime", FORMAT.parse("2022-12-09 19:02:00"));
            body.put("beginTime", FORMAT.parse("2022-12-08 00:01:00"));
            body.put("endTime", FORMAT.parse("2022-12-08 00:05:00"));
            body.put("orgId", UserHelper.getUserId("yaowr"));
//            body.put("scheduleId", "1gjnss0u4w9w1kw3scld252iq53gf273e8w0");
            JSONObject result = mkApiRequestHelper.callApi(
                    "/api/sys-time/workScheduleComponent/getDuration", body, JSONObject.class);
            log.info("getDuration: request={}, response={}",
                    JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                    JSONObject.toJSONString(result, SerializerFeature.PrettyFormat)
            );
        } catch (ParseException e) {
            log.error("getDuration error!", e);
        }
    }
}
