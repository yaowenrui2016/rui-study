package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2022-11-23
 */
@Slf4j
public class AutoNotifyBatchNotify {

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://127.0.0.1:8040",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("https://mkoppo.ywork.me", "jm", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "https://mkoppo.ywork.me",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) {
        // 批量消息
        batchNotify();
    }


    /**
     * 查询面板扩展
     */
    private static void batchNotify() {
        JSONArray body = FileUtils.loadJSONArray("AutoNotifyBatchNotify/batchNotify.json");
        MkResponse<JSONObject> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/sysNotifyComponent/batchNotify", body, JSONObject.class);
        log.info("batchNotify: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }
}
