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
 * @create: 2022-10-26
 */
@Slf4j
public class AutoNotifySend {

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkt1.ywork.me", "jm", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkt1.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");


    public static void main(String[] args) {
        send();
//        sendByOpenApi();
    }

    private static void send() {
        JSONObject body = FileUtils.loadJSON("AutoNotifySend/send_with_template.json");
        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/sysNotifyComponent/send", body, String.class);
        log.info("Send request:{}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    private static void sendByOpenApi() {
        JSONObject body = FileUtils.loadJSON("AutoNotifySend/sendByOpenApi.json");
        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/openapi/sys-notifybus/v2/main/send", body, String.class);
        log.info("Send request:{}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }
}
