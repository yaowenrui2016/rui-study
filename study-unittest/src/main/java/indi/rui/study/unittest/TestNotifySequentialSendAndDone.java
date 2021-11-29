package indi.rui.study.unittest;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.util.FileUtils;
import indi.rui.study.unittest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

import static indi.rui.study.unittest.util.FileUtils.loadJSON;

/**
 * @author: yaowr
 * @create: 2021-10-21
 */
@Slf4j
public class TestNotifySequentialSendAndDone {

    private static final String ADDRESS = "http://localhost:8040";
    private static final String X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";

    private static final String DONE_TODO_JSON_PATH = "json/done_todo.json";

    public static void main(String[] args) {
        TestNotifySequentialSendAndDone unitTest = new TestNotifySequentialSendAndDone();
        // 启动监控线程
//        unitTest.clear();
//        new Thread(() -> ThreadHelper.TimedRun(unitTest::monitor, 500),
//                "notify-monitor").start();
        // 按顺序执行发送和置已办
        for (int i = 0; i < 1; i++) {
            unitTest.send(i + "");
            unitTest.done(i + "");
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
        JSONObject body = loadJSON("send.json", this.getClass());
        body.put("entityId", entityId);
        body.put("entityKey", System.currentTimeMillis());
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", X_SERVICE_NAME);
        try {
            String response = HttpClientUtils.httpPost(sendUrl, body, header);
            MkResponse mkResponse = JSONObject.parseObject(response, MkResponse.class);
            if (!mkResponse.isSuccess()) {
                log.error("send todo failed! [request={}, response={}]",
                        JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                        response);
            }
        } catch (Exception e) {
            log.error("send todo exception", e);
        }
    }

    private void done(String entityId) {
        todoOpt("done", entityId);
    }

    private void removeTodo(String entityId) {
        todoOpt("removeTodo", entityId);
    }

    private void removeDone(String entityId) {
        todoOpt("removeDone", entityId);
    }

    private void removeAll(String entityId) {
        todoOpt("removeAll", entityId);
    }

    private void suspend(String entityId) {
        todoOpt("suspend", entityId);
    }

    private void resume(String entityId) {
        todoOpt("resume", entityId);
    }

    private void read(String entityId) {
        todoOpt("read", entityId);
    }

    /**
     * 调用bus的消息操作接口
     *
     * @param entityId
     */
    private void todoOpt(String method, String entityId) {
        String sendUrl = ADDRESS + "/api/sys-notifybus/sysNotifyComponent/" + method;
        // 从JSON文件中获取请求体
        JSONObject body = FileUtils.loadJSON("done.json", this.getClass());
        body.put("entityId", entityId);
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", X_SERVICE_NAME);
        try {
            String response = HttpClientUtils.httpPost(sendUrl, body, header);
            MkResponse mkResponse = JSONObject.parseObject(response, MkResponse.class);
            if (!mkResponse.isSuccess()) {
                log.error("{} todo failed! [request={}, response={}]",
                        method,
                        JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
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