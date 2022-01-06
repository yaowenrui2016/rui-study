package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkNotifyTodo;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class AutoGetMyTodo {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

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

    private static final int MAX_TIMEOUT_MS = 10000;

    public static void main(String[] args) {
        String snid = sendTodoRPC();
        getMyTodo(snid);
        log.info("Finished!");
    }

    private static String sendTodoRPC() {
        JSONObject json = FileUtils.loadJSON("send.json", AutoGetMyTodo.class);
        json.put("entityKey", System.currentTimeMillis());
        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/sysNotifyComponent/send",
                json, String.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Send todo error! errMsg=" + mkResponse.getMsg());
        }
        // 返回snid
        return mkResponse.getData();
    }

    private static void getMyTodo(String snid) {
        long beginTime = System.currentTimeMillis();
        boolean timeout;
        List<MkNotifyTodo> content;
        do {
            QueryResult<MkNotifyTodo> queryResult = getTodoRPC(snid);
            content = queryResult.getContent();
            timeout = (System.currentTimeMillis() - beginTime) > MAX_TIMEOUT_MS;
        } while ((content == null || content.isEmpty()) && !timeout);
        if (content == null || content.isEmpty()) {
            throw new RuntimeException("Query todo with snid '" + snid + "' found nothing!");
        }
        log.info("Get my todo: {}", JSONObject.toJSONString(content.get(0), SerializerFeature.PrettyFormat));
    }

    private static QueryResult<MkNotifyTodo> getTodoRPC(String snid) {
        // 查询我的待办
        JSONObject json = new JSONObject();
        Map<String, Object> conditions = (Map<String, Object>) json.computeIfAbsent("conditions", (k) -> new HashMap<>());
        conditions.put("fdSnid", snid);
        MkResponse<QueryResult<MkNotifyTodo>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-notify/sysNotifyTodo/my/list", json, MkNotifyTodo.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get my todo error! errMsg=" + mkResponse.getMsg());
        }
        return mkResponse.getData();
    }
}
