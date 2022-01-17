package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

/**
 * @author: yaowr
 * @create: 2021-11-22
 */
@Slf4j
public class AutoKKSetting {

    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
            "http://127.0.0.1:8040", "yaowr", "1");

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://mksmoke.ywork.me", "yuxd", "1");

    public static void main(String[] args) throws Exception {
        JSONObject kkSetting = get();
        log.info("Please input true or false:");
        Scanner scanner = new Scanner(System.in);
        boolean input = scanner.nextBoolean();
        // 开启或关闭
        kkSetting.put("enabled", input);
        save(kkSetting);
    }

    private static JSONObject get() {
        // 获取KK设置
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/tic-kk/kkSetting/get", null, JSONObject.class);
        log.info("Get kk setting: {}",
                JSON.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    private static void save(JSONObject kkSetting) {
        // 保存KK设置
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/tic-kk/kkSetting/save", kkSetting, JSONObject.class);
        log.info("Save kk setting: request={}, response={}",
                JSONObject.toJSONString(kkSetting, SerializerFeature.PrettyFormat),
                JSON.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }
}
