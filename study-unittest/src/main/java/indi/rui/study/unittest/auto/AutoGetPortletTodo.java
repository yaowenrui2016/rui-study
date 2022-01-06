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

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class AutoGetPortletTodo {

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

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
//        String snid = sendTodoRPC();
//        getTodoForAwait(snid);
        getPortletTodo();
    }

    private static String sendTodoRPC() {
        JSONObject json = FileUtils.loadJSON("send.json", AutoGetPortletTodo.class);
        json.put("entityKey", System.currentTimeMillis());
        json.put("todoLevel", 1 + RANDOM.nextInt(3));
        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/sysNotifyComponent/send",
                json, String.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Send todo error! errMsg=" + mkResponse.getMsg());
        }
        // 返回snid
        return mkResponse.getData();
    }

    private static void getTodoForAwait(String snid) {
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

    private static void getPortletTodo() {
        // 查询我的待办列表
        JSONObject json = new JSONObject();
        json.put("rowSize", 5);
        json.put("sorts", "fdCreateTime");
//        json.put("sorts", "fdLevel");
//        json.put("direction", "DESC");
        json.put("direction", "ASC");
        MkResponse<List<JSONObject>> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-notify/portlet/todo/list", json, JSONObject.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get my todo error! errMsg=" + mkResponse.getMsg());
        }
//        List<JSONObject> showResult = check(mkResponse.getData());
        List<JSONObject> showResult = mkResponse.getData();
        log.info("Get portlet todo list: param={}, todos={}",
                JSONObject.toJSONString(json, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(showResult, SerializerFeature.PrettyFormat));
    }

    private static List<JSONObject> check(List<JSONObject> todoVOs) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<JSONObject> result = new ArrayList<>();
        for (JSONObject json : todoVOs) {
            JSONObject res = new JSONObject();
            res.put("level", json.get("statusInfo"));
            res.put("time", sdf.format(new Date(((Long) json.get("created")))));
            result.add(res);
        }
        return result;
    }

    private static QueryResult<MkNotifyTodo> getTodoRPC(String snid) {
        // 查询我的待办
        JSONObject json = new JSONObject();
        Map<String, Object> conditions = (Map<String, Object>) json.computeIfAbsent("conditions", (k) -> new HashMap<>());
        conditions.put("fdSnid", snid);
        json.put("count", false);
        MkResponse<QueryResult<MkNotifyTodo>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-notify/sysNotifyTodo/my/list", json, MkNotifyTodo.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get my todo error! errMsg=" + mkResponse.getMsg());
        }
        return mkResponse.getData();
    }
}
