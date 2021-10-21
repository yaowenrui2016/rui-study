package indi.rui.study.something.REST接口调用案例;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import indi.rui.study.something.FileUtils;
import indi.rui.study.something.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author: yaowr
 * @create: 2021-10-21
 */
@Slf4j
public class SequentialSendAndDone {

    private static final String ADDRESS = "http://localhost:8040";
    private static final String X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";

    private static final String SEND_TODO_JSON_PATH = "json/send_todo.json";
    private static final String DONE_TODO_JSON_PATH = "json/done_todo.json";

    public static void main(String[] args) {
        SequentialSendAndDone seq = new SequentialSendAndDone();
        for (int i = 2000; i < 3000; i++) {
            seq.sendTodo(i + "");
            seq.doneTodo(i + "");
        }
    }

    private void sendTodo(String entityId) {
        call("send", SEND_TODO_JSON_PATH, entityId);
    }

    private void doneTodo(String entityId) {
        call("done", DONE_TODO_JSON_PATH, entityId);
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
            log.info("{} request: {} >>> response: {}", method, JSONUtils.toJSONString(body), response);
        } catch (Exception e) {
            log.error("{} exception", method, e);
        }
    }
}
