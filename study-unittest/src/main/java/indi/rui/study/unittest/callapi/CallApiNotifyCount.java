package indi.rui.study.unittest.callapi;

import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-12-20
 */
@Slf4j
public class CallApiNotifyCount {

    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040", "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://10.253.0.227:8080", "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) throws Exception {
//        JSONObject condition = new JSONObject();
//        condition.put("loginName", "cuipx");
//        condition.put("fdType",1);
        JSONObject condition = FileUtils.loadJSON("conditions.json", CallApiNotifyCount.class);
        String result = mkApiRequestHelper.callApi("/api/sys-notify/sysNotifyTodo/count", condition);
        log.info("Get notify count: condition={}, count={}", JSONObject.toJSONString(condition), result);
    }
}
