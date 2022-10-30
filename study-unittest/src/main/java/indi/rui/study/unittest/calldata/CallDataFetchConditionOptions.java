package indi.rui.study.unittest.calldata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-11-22
 */
@Slf4j
public class CallDataFetchConditionOptions {

    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
            "http://127.0.0.1:8040", "yaowr", "1");

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://mksmoke.ywork.me", "yuxd", "1");

    public static void main(String[] args) {
        // 获取用户ID
        String userId = mkDataRequestHelper.getUserInfo().getString("userId");
        // 禁用模块
        JSONObject json = new JSONObject();
        json.put("personId", userId);
        json.put("todoType", 1);
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.CallDataForJson(
                "/data/sys-notify/sysNotifyTodo/fetchConditionOptions", json);
        log.info("Fetch condition options: {}", JSON.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }
}
