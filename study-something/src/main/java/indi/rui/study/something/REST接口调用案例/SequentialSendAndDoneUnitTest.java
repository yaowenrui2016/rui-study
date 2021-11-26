package indi.rui.study.something.REST接口调用案例;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import indi.rui.study.something.FileUtils;
import indi.rui.study.something.HttpClientUtils;
import indi.rui.study.something.REST接口调用案例.DTO.MkResponse;
import indi.rui.study.something.ThreadHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author: yaowr
 * @create: 2021-10-21
 */
@Slf4j
public class SequentialSendAndDoneUnitTest {

    private static final String ADDRESS = "http://localhost:8040";
    private static final String X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";

    private static final String SEND_TODO_JSON_PATH = "json/send_todo.json";
    private static final String DONE_TODO_JSON_PATH = "json/done_todo.json";

    public static void main(String[] args) {
        SequentialSendAndDoneUnitTest unitTest = new SequentialSendAndDoneUnitTest();
        // 启动监控线程
        unitTest.clear();
        new Thread(() -> ThreadHelper.TimedRun(unitTest::monitor, 500),
                "notify-monitor").start();
        // 按顺序执行发送和置已办
        for (int i = 0; i < 1; i++) {
            unitTest.send(i + "");
//            unitTest.done(i + "");
//            unitTest.removeTodo(i + "");
//            unitTest.removeDone(i + "");
//            unitTest.removeAll(i + "");
//            unitTest.suspend(i + "");
//            unitTest.resume(i + "");
//            unitTest.read(i + "");
        }
    }

    private void send(String entityId) {
        String sendUrl = ADDRESS + "/api/sys-notifybus/sysNotifyComponent/send";
        // 从JSON文件中获取请求体
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader()
                .getResource(SEND_TODO_JSON_PATH)).getFile();
        String sendJson = FileUtils.readFileToString(filePath, "utf-8");
        JSONObject body = JSONObject.parseObject(sendJson);
        body.put("entityId", entityId);
        body.put("entityKey", System.currentTimeMillis());
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", X_SERVICE_NAME);
        try {
            String response = HttpClientUtils.httpPost(sendUrl, body, header);
            MkResponse mkResponse = JSONObject.parseObject(response, MkResponse.class);
            if (!mkResponse.isSuccess()) {
                log.error("send todo failed! [request={}, response={}]",
                        JSONUtils.toJSONString(body),
                        response);
            }
        } catch (Exception e) {
            log.error("send todo exception", e);
        }
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

    /**
     * 调用bus
     *
     * @param entityId
     */
    private void call(String method, String bodyPath, String entityId) {
        String sendUrl = ADDRESS + "/api/sys-notifybus/sysNotifyComponent/" + method;
        // 从JSON文件中获取请求体
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader()
                .getResource(bodyPath)).getFile();
        String sendJson = FileUtils.readFileToString(filePath, "utf-8");
        JSONObject body = JSONObject.parseObject(sendJson);
        body.put("entityId", entityId);
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", X_SERVICE_NAME);
        try {
            String response = HttpClientUtils.httpPost(sendUrl, body, header);
            MkResponse mkResponse = JSONObject.parseObject(response, MkResponse.class);
            if (!mkResponse.isSuccess()) {
                log.error("{} todo failed! [request={}, response={}]",
                        method,
                        JSONUtils.toJSONString(body),
                        response);
            }
        } catch (Exception e) {
            log.error("{} todo exception", method, e);
        }
    }

    /**
     * 监视结果
     */
    private void monitor() {
        callMonitor("monitor");
    }

    /**
     * 清空结果
     */
    private void clear() {
        callMonitor("clear");
    }

    private void callMonitor(String method) {
//        String monitorUrl = ADDRESS + "/api/sys-lbpm-approval/demoTodoExt/" + method;
        String monitorUrl = ADDRESS + "/api/sys-notify/ekpInformTodoExtension/" + method;
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", X_SERVICE_NAME);
        try {
            String response = HttpClientUtils.httpPost(monitorUrl, null, header);
            log.info("{} response: {}", method, response);
        } catch (Exception e) {
            log.error("{} exception", method, e);
        }
    }
}
