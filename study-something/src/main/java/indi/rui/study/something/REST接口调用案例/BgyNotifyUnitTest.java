package indi.rui.study.something.REST接口调用案例;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import indi.rui.study.something.FileUtils;
import indi.rui.study.something.HttpClientUtils;
import indi.rui.study.something.REST接口调用案例.DTO.QueryResult;
import indi.rui.study.something.ThreadHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: yaowr
 * @create: 2021-10-22
 */
@Slf4j
public class BgyNotifyUnitTest {

    private static final String ADDRESS = "https://bipnew-sit.countrygarden.com.cn";
    private static final String X_SERVICE_NAME = "43534c48566d654e5031674d355238395259346736673d3d";

    private static final String SEND_TODO_JSON_PATH = "json/send_todo.json";
    private static final String DONE_TODO_JSON_PATH = "json/done_todo.json";

    public static void main(String[] args) {
        BgyNotifyUnitTest unitTest = new BgyNotifyUnitTest();

        // 启动监控线程
        boolean running = true;
        new Thread(() -> {
            ThreadHelper.TimedRun(unitTest::findByPerson, 5000);
        }, "notify-monitor").start();

        // 执行消息发送和置已办操作
        new Thread(unitTest::exec, "notify-executor").start();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        unitTest.running = false;
        log.info("accomplish！input={}", input);

    }

    // ===================== 成员变量 ===================== //

    private final String entityName = "kafka_transfer_" + System.currentTimeMillis();

    private final String target = "youwei";

    private final AtomicInteger count = new AtomicInteger(0);

    private boolean running = true;


    // ===================== 主要API ===================== //

    public void exec() {
        long begin = System.currentTimeMillis();
        try {
            ThreadHelper.TimedRun(() -> {
                if (!running) {
                    throw new RuntimeException("STOP");
                }
                int entityId = nextEntityId();
                send(entityId + "");
                done(entityId + "");
            }, -1);
        } catch (Exception e) {
        }
        long end = System.currentTimeMillis();
        log.info("execute stopped! [runningTime={}, count={}]",
                end - begin,
                count.get());
    }

    public void findByPerson() {
        String findUrl = ADDRESS + "/api/sys-notify/baseSysNotify/findByPerson";
        // 从JSON文件中获取请求体
        JSONObject body = new JSONObject();
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("todoOrDone", "done");
        conditions.put("targets", Arrays.asList(target));
        conditions.put("fdEntityName", entityName);
        body.put("conditions", conditions);
        body.put("pageSize", 1);
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", X_SERVICE_NAME);
        String response = null;
        try {
            response = HttpClientUtils.httpPost(findUrl, body, header);
            QueryResult<JSONObject> queryResult = JSONObject.parseObject(response, QueryResult.class);
            log.info("findByPerson response OK >>> [entityName={}, target={}, count={}, find={}]",
                    this.entityName,
                    this.target,
                    count.get(),
                    queryResult.getTotalSize());
        } catch (Exception e) {
            log.error("findByPerson exception: {}", response, e);
        }
    }

    // ===================== 私有方法 ===================== //

    private int nextEntityId() {
        return count.incrementAndGet();
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
        body.put("subject", "消息完整性之kafka迁移测试" + entityId);
        body.put("entityId", entityId);
        body.put("entityName", this.entityName);
        body.put("targets", Arrays.asList("youwei"));
        body.put("orgProperty", "fdLoginName");
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
