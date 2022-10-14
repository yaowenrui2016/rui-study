package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import sun.rmi.runtime.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: yaowr
 * @create: 2022-09-30
 */
@Slf4j
public class AutoNotifybusMainOpenapi {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmokemini.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmokemini.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev02.ywork.me", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev02.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");


    public static void main(String[] args) {
//        // 发送待办
//        send();

        // 发送待办
        done();
    }

    private static void send() {
        JSONObject json = FileUtils.loadJSON("AutoNotifybusMainOpenapi/send.json");
        json.put("entityKey", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")));
        MkResponse<?> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/v2/main/send", json, Void.class);
        log.info("Send response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    private static void done() {
        JSONObject json = FileUtils.loadJSON("AutoNotifybusMainOpenapi/done.json");
        MkResponse<?> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/v2/main/done", json, Void.class);
        log.info("Send response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }
}
