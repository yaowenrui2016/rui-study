package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-01-22
 */
@Slf4j
public class AutoNotifySync {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");


    public static void main(String[] args) {
        JSONObject json = FileUtils.loadJSON("syncNotify.json", AutoNotifySync.class);
        // 设置已办的完成时间
        List<Long> targetsFinish = new ArrayList<>();
        for (int i = 0; i < ((List<String>) json.get("targetsDone")).size(); i++) {
            targetsFinish.add(System.currentTimeMillis());
        }
        json.put("targetsFinish", targetsFinish);
        MkResponse<Object> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notify/third/sync",
                json, Object.class);
        log.info("Mk todo sync result: {}",
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }
}
