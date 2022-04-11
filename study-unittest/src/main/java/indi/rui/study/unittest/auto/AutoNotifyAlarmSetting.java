package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkNotifyAlarmSetting;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class AutoNotifyAlarmSetting {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yuxd", "1");

    public static void main(String[] args) {
        MkNotifyAlarmSetting alarmSetting = getAlarmSettingRPC();
        alarmSetting.setEnable(!alarmSetting.getEnable());
        setAlarmSettingRPC(alarmSetting);
        getAlarmSettingRPC();
//        resetAlarmSettingRPC();
    }

    private static MkNotifyAlarmSetting getAlarmSettingRPC() {
        // 拉取来源系统和模块
        MkResponse<MkNotifyAlarmSetting> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyAlarmSetting/getAlarmSetting", null, MkNotifyAlarmSetting.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get informed method error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Get alarm setting: {}", JSON.toJSONString(mkResponse.getData(),
                SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    private static void setAlarmSettingRPC(MkNotifyAlarmSetting alarmSetting) {
        // 保存消息告警设置
        JSONObject json = (JSONObject) JSONObject.toJSON(alarmSetting);
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyAlarmSetting/setAlarmSetting", json);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get informed method error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Set alarm setting: {}", JSON.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    private static void resetAlarmSettingRPC() {
        // 保存消息告警设置
        JSONObject json = FileUtils.loadJSON("AutoNotifyAlarmSetting/alarm_setting.json");
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyAlarmSetting/setAlarmSetting", json);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get informed method error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Set alarm setting: {}", JSON.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }
}
