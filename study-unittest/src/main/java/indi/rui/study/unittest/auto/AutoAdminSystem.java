package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2022-11-02
 */
@Slf4j
public class AutoAdminSystem {


    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "sysadmin", "Password_1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev01.ywork.me", "jm", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev01.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) {
        // 系统初始化
        systemInitByIds();
    }


    /**
     * 系统初始化
     */
    private static void systemInitByIds() {
        JSONArray body = new JSONArray();
//        JSONObject each1 = new JSONObject();
//        each1.put("fdId", "sys-application:initializer.application");
//        body.add(each1);
        JSONObject each2 = new JSONObject();
        each2.put("fdId", "sys-right:sys.right.initializer");
        body.add(each2);
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-admin/systemInitialize/systemInitByIds", body, JSONObject.class);
        log.info("systemInitByIds: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }
}