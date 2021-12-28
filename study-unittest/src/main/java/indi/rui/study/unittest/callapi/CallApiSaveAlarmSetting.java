package indi.rui.study.unittest.callapi;

import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.dto.MkNotifyAlarmSetting;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-12-20
 */
@Slf4j
public class CallApiSaveAlarmSetting {

//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://127.0.0.1:8040", "73456775666d4c416f73776139584a4131432f6847413d3d");

    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://10.253.0.227:8080", "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) throws Exception {
        JSONObject setting = new JSONObject();
        setting.put("enable", false);
        String result = mkApiRequestHelper.callApi("/api/sys-notify/sysNotifyAlarm/getAlarmSetting",
                setting);
        log.info("Set mk notify alarm setting: {}", result);
    }
}
