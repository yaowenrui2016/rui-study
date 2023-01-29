package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;

/**
 * @author: yaowr
 * @create: 2023-01-03
 */
@Slf4j
public class AutoDiscover {


    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://127.0.0.1:8040", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://127.0.0.1:8040",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

    //    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev01.ywork.me", "jm", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://mkdev01.ywork.me",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) {
        // 获取模块
        getModule();
    }

    /**
     * 获取模块
     */
    private static void getModule() {
        JSONObject body = null;
        JSONObject result = mkApiRequestHelper.callApi(
                "/api/framework-discovery/moduleMapping/get", body, JSONObject.class);
        log.info("getModule: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(result, SerializerFeature.PrettyFormat)
        );
    }

}
