package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: yaowr
 * @create: 2022-01-17
 */
@Slf4j
public class AutoEmailSetting {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev02.ywork.me", "yuxd", "1");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");

    public static void main(String[] args) {
//        getSetting();
//        saveSetting();
//        verify();
        sendEmail();
    }

    private static void getSetting() {
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/emailSetting/get", null, JSONObject.class);
        log.info("{}", JSONObject.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
    }

    private static void saveSetting() {
        JSONObject json = FileUtils.loadJSON("AutoEmailSetting/setting.json");
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/emailSetting/save", json, JSONObject.class);
        log.info("{}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    private static void verify() {
        JSONObject json = FileUtils.loadJSON("AutoEmailSetting/setting.json");
        MkResponse<Object> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/emailSetting/verify", json, Object.class);
        log.info("{}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }


    private static String sendEmail() {
        JSONObject json = FileUtils.loadJSON("AutoEmailSetting/sendEmail.json");
        json.put("entityKey", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));
        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/sysNotifyComponent/send",
                json, String.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Send todo error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Send email success!");
        // 返回snid
        return mkResponse.getData();
    }
}
