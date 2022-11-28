package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.support.UserHelper;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 排班
 *
 * @author: yaowr
 * @create: 2022-11-02
 */
@Slf4j
public class AutoTimeWorkComponent {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");


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
        // 获取工作区间
        getWorkPeriod();
    }


    /**
     * 获取工作区间
     */
    private static void getWorkPeriod() {
        try {
            JSONObject body = new JSONObject();
            body.put("beginTime", FORMAT.parse("2022-12-01"));
            body.put("endTime", FORMAT.parse("2022-12-31"));
            body.put("orgId", UserHelper.getUsers("lizj").get(0).getFdId());
            JSONObject result = mkApiRequestHelper.callApi(
                    "/api/sys-time/workScheduleComponent/getWorkPeriod", body, JSONObject.class);
            log.info("getWorkPeriod: request={}, response={}",
                    JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                    JSONObject.toJSONString(result, SerializerFeature.PrettyFormat)
            );
        } catch (ParseException e) {
            log.error("getWorkPeriod error!", e);
        }
    }
}
