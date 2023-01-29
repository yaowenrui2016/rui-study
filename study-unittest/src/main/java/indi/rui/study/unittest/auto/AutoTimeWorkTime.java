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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 排班记录
 *
 * @author: yaowr
 * @create: 2022-11-02
 */
@Slf4j
public class AutoTimeWorkTime {

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
        // 查询排班列表
        list();
//        // 工时模拟
//        mock();
    }


    /**
     * 编辑排班
     */
    private static void mock() {
        try {
            JSONObject body = new JSONObject();
            body.put("fdOrgIds", UserHelper.getUserIds("yaowr"));
            body.put("beginDate", FORMAT.parse("2022-12-20 03:45:00"));
            body.put("endDate", FORMAT.parse("2023-01-10 03:45:00"));
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
        JSONObject body = FileUtils.loadJSON("AutoTimeworkTime/list.json");
        JSONObject conditions = body.getJSONObject("conditions");
        conditions.put("fdPerson.fdId", UserHelper.getUserId("yaowr"));
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-time/workTime/list", body, JSONObject.class);
        log.info("list: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData().getContent();
    }
}
