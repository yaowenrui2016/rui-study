package indi.rui.study.bgytest;

import com.alibaba.fastjson.JSONObject;
import indi.rui.study.bgytest.dto.MkResponse;
import indi.rui.study.bgytest.dto.QueryResult;
import indi.rui.study.bgytest.util.FileUtils;
import indi.rui.study.bgytest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 碧桂园待办完整性测试程序
 *
 * @author: yaowr
 * @create: 2021-10-22
 */
@Slf4j
public class BgyNotifyUnitTest {

    private static final String ADDRESS = "https://bipnew-sit.countrygarden.com.cn";
    private static final String X_SERVICE_NAME = "43534c48566d654e5031674d355238395259346736673d3d";
//    private static final String ADDRESS = "http://localhost:8040";
//    private static final String X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";

    private static final String TARGETS = "youwei,chenqianbin01,leikun02,penghe,youqingyang";
    private static final int EXEC_INTERVAL = 100;
    private static final int MONITOR_INTERVAL = 60;

    private static final String SEND_TODO_JSON_PATH = "json/send_todo.json";
    private static final String DONE_TODO_JSON_PATH = "json/done_todo.json";

    // MK服务访问地址 | MK验权请求头x-service-name | 测试人员账号 | 执行待办请求间隔 | 监控时间间隔
    // -Dmk.address=https://bipnew-sit.countrygarden.com.cn1
    // -Dmk.xServiceName=43534c48566d654e5031674d355238395259346736673d3d
    // -Dmk.targets=youwei,chenqianbin01,leikun02,penghe,youqingyang
    // -Dmk.execInterval.ms=100
    // -Dmk.monitorInterval.s=10
    public static void main(String[] args) {
        log.info(">>>>>>>>>>>>>>>>> begin <<<<<<<<<<<<<<<<<");
        BgyNotifyUnitTest unitTest = new BgyNotifyUnitTest();
        // 启动监控线程
        new Thread(unitTest::monitor, "notify-monitor").start();

        // 执行消息发送和置已办操作
        unitTest.execute();

        // 通过控制台停止程序
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        while (true) {
            String input = scanner.next();
            if (!stop && "stop".equalsIgnoreCase(input)) {
                stop = true;
                unitTest.execRunning = false;
            } else if (stop && "stop".equalsIgnoreCase(input)) {
                unitTest.monitorRunning = false;
                unitTest.addPermit();
                break;
            } else {
                unitTest.addPermit();
            }
        }
        log.info("accomplish!");
    }

    // ===================== 成员变量 ===================== //

    private String address = System.getProperty("mk.address", ADDRESS);

    private String xServiceName = System.getProperty("mk.xServiceName", X_SERVICE_NAME);


    private final String entityName = "kafka_transfer_" + System.currentTimeMillis();

    private final List<String> targets = Arrays.asList(System.getProperty("mk.targets", TARGETS).split(","));

    private final List<AtomicInteger> counts = new ArrayList<>(targets.size());


    private LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1);

    private int execInterval = Integer.valueOf(System.getProperty("mk.execInterval.ms", String.valueOf(EXEC_INTERVAL)));
    private int monitorInterval = Integer.valueOf(System.getProperty("mk.monitorInterval.s", String.valueOf(MONITOR_INTERVAL)));

    private boolean execRunning = true;

    private boolean monitorRunning = true;


    // ===================== Constructor ===================== //

    public BgyNotifyUnitTest() {
        for (int i = 0; i < this.targets.size(); i++) {
            this.counts.add(new AtomicInteger(0));
        }
    }


    // ===================== 主要API ===================== //

    public void execute() {
        for (int i = 0; i < this.targets.size(); i++) {
            String person = this.targets.get(i);
            final int idx = i;
            new Thread(() -> {
                long begin = System.currentTimeMillis();
                while (true) {
                    if (!this.execRunning) {
                        break;
                    }
                    String entityId = String.valueOf(nextEntityId(idx));
                    try {
                        send(entityId, person);
                        done(entityId, person);
                    } catch (Exception e) {
                        log.error("send or done todo exception!", e);
                    }
                }
                long end = System.currentTimeMillis();
                log.info("execution stopped. [person={}, timeOfDuration={}, count={}]",
                        person,
                        end - begin,
                        this.counts.get(idx));
                if (execInterval > 0) {
                    try {
                        Thread.sleep(execInterval);
                    } catch (InterruptedException e) {
                    }
                }
            }, "notify-executor-" + i).start();
        }
    }

    public void monitor() {
        while (true) {
            try {
                this.queue.poll(monitorInterval, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
            }
            if (!this.monitorRunning) {
                break;
            }
            List<Integer> finds = new ArrayList<>();
            for (int i = 0; i < this.targets.size(); i++) {
                String target = this.targets.get(i);
                long find = findByPerson(target);
                finds.add(i, (int) find);
            }
            log.info("---------------- entityName: {} ----------------", this.entityName);
            for (int i = 0; i < this.targets.size(); i++) {
                log.info("find todo OK. [target={}, count={}, find={}]",
                        this.targets.get(i),
                        this.counts.get(i),
                        finds.get(i));
            }
        }
        log.info("monitor stopped.");
    }

    public void addPermit() {
        this.queue.add(1);
    }

    // ===================== 私有方法 ===================== //

    private int nextEntityId(int idx) {
        return this.counts.get(idx).incrementAndGet();
    }

    private void send(String entityId, String target) {
        call("send", SEND_TODO_JSON_PATH, entityId, target);
    }

    private void done(String entityId, String target) {
        call("done", DONE_TODO_JSON_PATH, entityId, target);
    }

    private void removeTodo(String entityId, String target) {
        call("removeTodo", DONE_TODO_JSON_PATH, entityId, target);
    }

    private void removeDone(String entityId, String target) {
        call("removeDone", DONE_TODO_JSON_PATH, entityId, target);
    }

    private void removeAll(String entityId, String target) {
        call("removeAll", DONE_TODO_JSON_PATH, entityId, target);
    }

    private void suspend(String entityId, String target) {
        call("suspend", DONE_TODO_JSON_PATH, entityId, target);
    }

    private void resume(String entityId, String target) {
        call("resume", DONE_TODO_JSON_PATH, entityId, target);
    }

    private void read(String entityId, String target) {
        call("read", DONE_TODO_JSON_PATH, entityId, target);
    }

    private void call(String method, String bodyPath, String entityId, String target) {
        // 请求地址
        String sendUrl = this.address + "/api/sys-notifybus/sysNotifyComponent/" + method;
        // 从JSON文件中获取请求体
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(bodyPath)).getFile();
        String sendJson = FileUtils.readFileToString(filePath, "utf-8");
        JSONObject body = JSONObject.parseObject(sendJson);
        body.put("subject", "消息完整性之kafka迁移测试" + entityId);
        body.put("entityId", entityId);
        body.put("entityName", this.entityName);
        body.put("targets", Arrays.asList(target));
        body.put("orgProperty", "fdLoginName");

        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", this.xServiceName);
        try {
            String response = HttpClientUtils.httpPost(sendUrl, body, header);
            MkResponse mkResponse = JSONObject.parseObject(response, MkResponse.class);
            if (!mkResponse.isSuccess()) {
                log.error("{} todo failed! [request={}, response={}]",
                        method,
                        JSONObject.toJSONString(body),
                        response);
            }
        } catch (Exception e) {
            log.error("{} todo exception! [entityId={}]", method, entityId, e);
        }
    }

    private long findByPerson(String target) {
        String findUrl = this.address + "/api/sys-notify/baseSysNotify/findByPerson";
        // 从JSON文件中获取请求体
        JSONObject body = new JSONObject();
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("todoOrDone", "done");
        conditions.put("targets", Arrays.asList(target));
        conditions.put("fdEntityName", this.entityName);
        body.put("conditions", conditions);
        body.put("pageSize", 1);
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", this.xServiceName);
        String response = null;
        try {
            response = HttpClientUtils.httpPost(findUrl, body, header);
            QueryResult<JSONObject> queryResult = JSONObject.parseObject(response, QueryResult.class);
            return queryResult.getTotalSize();
        } catch (Exception e) {
            log.error("find todo exception! {}", response, e);
        }
        return 0;
    }
}
