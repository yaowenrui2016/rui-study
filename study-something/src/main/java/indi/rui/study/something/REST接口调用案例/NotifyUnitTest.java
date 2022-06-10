package indi.rui.study.something.REST接口调用案例;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import indi.rui.study.something.HttpClientUtils;
import indi.rui.study.something.REST接口调用案例.DTO.QueryResult;
import indi.rui.study.something.ThreadHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author: yaowr
 * @create: 2021-10-22
 */
@Slf4j
public class NotifyUnitTest {

    private static final String ADDRESS = "http://localhost:8040";
    private static final String X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";

    private static final String SEND_TODO_JSON_PATH = "json/send_todo.json";
    private static final String DONE_TODO_JSON_PATH = "json/done_todo.json";

    public static void main(String[] args) {
        NotifyUnitTest unitTest = new NotifyUnitTest();
        // 初始化 entityIds
        List<String> entityIds = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            String entityId = i + "";
            entityIds.add(entityId);
        }
        // 启动监控线程
        new Thread(() -> {
            ThreadHelper.TimedRun(() -> {
                unitTest.findByPerson(entityIds);
            }, 500);
        }, "notify-monitor").start();
        // 执行消息发送|置已办|移除等操作
        for (String entityId : entityIds) {
//            unitTest.send(entityId);
//            unitTest.done(entityId);
//            unitTest.removeTodo(entityId);
//            unitTest.removeDone(entityId);
//            unitTest.removeAll(entityId);
        }
    }

    private void findByPerson(List<String> entityIds) {
        String findUrl = ADDRESS + "/api/sys-notify/baseSysNotify/findByPerson";
        // 从JSON文件中获取请求体
        JSONObject body = new JSONObject();
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("todoOrDone", "done");
        conditions.put("targets", Arrays.asList("yaowr"));
        conditions.put("fdEntityId", Collections.singletonMap("$in", entityIds));
        body.put("conditions", conditions);
        body.put("pageSize", 1000);
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", X_SERVICE_NAME);
        String response = null;
        try {
            response = HttpClientUtils.httpPost(findUrl, body, header);
            QueryResult<JSONObject> queryResult = JSONObject.parseObject(response, QueryResult.class);
            List<Integer> counts = new ArrayList<>();
            for (int i = 0; i < entityIds.size(); i++) {
                counts.add(i, 0);
            }
            for (JSONObject jsonObj : queryResult.getContent()) {
                int idx = Integer.valueOf((String) jsonObj.get("fdEntityId"));
                Integer previousVal = counts.get(idx);
                if (previousVal == null) {
                    previousVal = 0;
                }
                counts.set(idx, previousVal + 1);
            }
            log.info("findByPerson  response OK >>> {}", Arrays.toString(counts.toArray()));
        } catch (Exception e) {
            log.error("findByPerson exception: {}", response, e);
        }
    }

    private void send(String entityId) {
        call("send", SEND_TODO_JSON_PATH, entityId);
    }

    private void done(String entityId) {
        call("done", DONE_TODO_JSON_PATH, entityId);
    }

    private void removeTodo(String entityId) {
        call("removeTodo", DONE_TODO_JSON_PATH, entityId);
    }

    private void removeDone(String entityId) {
        call("removeDone", DONE_TODO_JSON_PATH, entityId);
    }

    private void removeAll(String entityId) {
        call("removeAll", DONE_TODO_JSON_PATH, entityId);
    }

    private void suspend(String entityId) {
        call("suspend", DONE_TODO_JSON_PATH, entityId);
    }

    private void resume(String entityId) {
        call("resume", DONE_TODO_JSON_PATH, entityId);
    }

    private void read(String entityId) {
        call("read", DONE_TODO_JSON_PATH, entityId);
    }

    private void call(String method, String bodyPath, String entityId) {
        String sendUrl = ADDRESS + "/api/sys-notifybus/sysNotifyComponent/" + method;
        // 从JSON文件中获取请求体
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(bodyPath)).getFile();
        String sendJson = FileUtils.readFileToString(filePath, "utf-8");
        JSONObject body = JSONObject.parseObject(sendJson);
        body.put("entityId", entityId);
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", X_SERVICE_NAME);
        try {
            String response = HttpClientUtils.httpPost(sendUrl, body, header);
            if (response.contains("\"success\":false")) {
                log.info("{} request: {} >>> response: {}", method, JSONUtils.toJSONString(body), response);
            }
        } catch (Exception e) {
            log.error("{} exception", method, e);
        }
    }
}
