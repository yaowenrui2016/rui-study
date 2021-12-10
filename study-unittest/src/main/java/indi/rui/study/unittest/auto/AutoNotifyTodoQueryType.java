package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
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
public class AutoNotifyTodoQueryType {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");

    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

    private static final int MAX_TIMEOUT_MS = 10000;

    public static void main(String[] args) {
        // 执行用例1~6
        for (int useCaseNo = 1; useCaseNo <= 6; useCaseNo++) {
            String snid = sendTodoRPC(useCaseNo);
            checkTodo(useCaseNo, snid);
        }
        log.info("All use case passed!");
    }

    private static String sendTodoRPC(int useCaseNo) {
        JSONObject json = FileUtils.loadJSON("send.json", AutoNotifyTodoQueryType.class);
        json.put("entityKey", System.currentTimeMillis());
        switch (useCaseNo) {
            case 1: // 不传todoType，不传queryType
                json.remove("todoType");
                json.remove("queryType");
                break;
            case 2: // todoType为1，不传queryType
                json.put("todoType", 1);
                json.remove("queryType");
                break;
            case 3: // todoType为2，不传queryType
                json.put("todoType", 2);
                json.remove("queryType");
                break;
            case 4: // 不传todoType，queryType为1
                json.remove("todoType");
                json.put("queryType", 1);
                break;
            case 5: // 不传todoType，queryType为2
                json.remove("todoType");
                json.put("queryType", 2);
                break;
            case 6: // todoType为1，queryType为2
                json.put("todoType", 1);
                json.put("queryType", 2);
                break;
        }
        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/sysNotifyComponent/send",
                json, String.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Send todo error! useCaseNo=" + useCaseNo);
        }
        // 返回snid
        return mkResponse.getData();
    }

    private static void checkTodo(int useCaseNo, String snid) {
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
        MkNotifyTodo todo = content.get(0);
        boolean pass = false;
        String errMsg = null;
        switch (useCaseNo) {
            case 1: // 不传todoType，不传queryType
            case 2: // todoType为1，不传queryType
            case 4: // 不传todoType，queryType为1
                pass = todo.getFdType() == 1 && todo.getFdQueryType() == 1;
                errMsg = "expectType=1, expectQueryType=1";
                break;
            case 3: // todoType为2，不传queryType
                pass = todo.getFdType() == 2 && todo.getFdQueryType() == 2;
                errMsg = "expectType=2, expectQueryType=2";
                break;
            case 5: // 不传todoType，queryType为2
            case 6: // todoType为1，queryType为2
                pass = todo.getFdType() == 1 && todo.getFdQueryType() == 2;
                errMsg = "expectType=1, expectQueryType=2";
                break;
        }
        if (!pass) {
            throw new RuntimeException("Check my todo error! [snid=" + snid + ", " + errMsg + ", realType=" + todo.getFdType() + ", realQueryType=" + todo.getFdQueryType() + "]");
        }
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
