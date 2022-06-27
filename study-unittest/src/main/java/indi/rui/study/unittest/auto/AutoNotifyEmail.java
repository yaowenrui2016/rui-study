package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2022-06-15
 */
@Slf4j
public class AutoNotifyEmail {


//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://127.0.0.1:8040",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://mkdev02.ywork.me", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://mkdev02.ywork.me",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkoppo.ywork.me", "yaowr", "1", "oppo");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkoppo.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmokemini.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmokemini.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");


    public static void main(String[] args) {
        send();
        list();
    }

    /**
     * 获取消息模板元数据
     */
    private static void send() {
        JSONArray json = FileUtils.loadJSON("AutoNotifyEmail/send.json", JSONArray.class);
        MkResponse<String> repVars = mkDataRequestHelper.callData(
                "/data/sys-notify/email/send", json, String.class);
        log.info("send email: {}", JSONObject.toJSONString(repVars, SerializerFeature.PrettyFormat));
    }

    /**
     * 获取消息模板元数据
     */
    private static void list() {
        JSONObject json = new JSONObject();
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-notify/email/list", json, JSONObject.class);
        log.info("list email: {}", JSONObject.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
    }
}
