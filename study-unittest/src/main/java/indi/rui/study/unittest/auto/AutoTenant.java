package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2022-08-10
 */
@Slf4j
public class AutoTenant {

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mktest.ywork.me", "jj01", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mktest.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkmini.ywork.me/", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkmini.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkmini-se.ywork.me", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkpre.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");


    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        // 初始化账号
        initAccount();

//        // 获取初始化信息
//        getInitialInfo();
//
//        // 重置初始化信息
//        resetInitialInfo();
//
//        // 获取初始化信息
//        getInitialInfo();
    }

    /**
     * 初始化管理员账号
     */
    private static void initAccount() {
        JSONObject body = FileUtils.loadJSON("AutoTenant/init_account.json");
        JSONObject result = mkApiRequestHelper.callApi(
                "/api/sys-admin/initial/initAccount", body, JSONObject.class);
        log.warn("initAccount: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(result, SerializerFeature.PrettyFormat));
    }

    /**
     * 获取初始化信息
     */
    private static void getInitialInfo() {
        JSONArray body = new JSONArray();
        body.add(0);
        JSONObject result = mkApiRequestHelper.callApi(
                "/api/sys-admin/initial/getInitialInfo", body, JSONObject.class);
        log.warn("getInitialInfo: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(result, SerializerFeature.PrettyFormat)
        );
    }

    /**
     * 重置初始化信息
     */
    private static void resetInitialInfo() {
        JSONArray body = new JSONArray();
        body.add(0);
        JSONObject result = mkApiRequestHelper.callApi(
                "/api/sys-org/initialization/resetInitialInfo", body, JSONObject.class);
        log.warn("resetInitialInfo: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(result, SerializerFeature.PrettyFormat)
        );
    }
}
