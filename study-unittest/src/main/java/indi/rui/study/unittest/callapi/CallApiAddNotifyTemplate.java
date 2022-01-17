package indi.rui.study.unittest.callapi;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class CallApiAddNotifyTemplate {

    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040", "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev01.ywork.me", "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) throws Exception {
        // 新增消息模板
        JSONObject json = FileUtils.loadJSON("add.json", CallApiAddNotifyTemplate.class);
        JSONObject result = mkApiRequestHelper.callApi(
                "/api/sys-notify/sysNotifyTemplate/add", json, JSONObject.class);
        log.info("Add notify template: {}", JSONObject.toJSONString(result, SerializerFeature.PrettyFormat));
    }
}
