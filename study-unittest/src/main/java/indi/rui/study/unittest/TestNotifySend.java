package indi.rui.study.unittest;

import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.util.FileUtils;
import indi.rui.study.unittest.util.HttpClientUtils;
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
public class TestNotifySend {

//    private static final String ADDRESS = "https://bipnew-sit.countrygarden.com.cn";
//
//    private static final String X_SERVICE_NAME = "43534c48566d654e5031674d355238395259346736673d3d";
//
//    private static final String TARGETS = "youwei,chenqianbin01,leikun02,penghe,youqingyang,yangqinggong,lizhaohua05";

//
//    private static final String ADDRESS = "http://192.168.51.202:8050";
//
//    private static final String X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";
//
//    private static final String TARGETS = "weilq,liuf,zz1,zz2,zz3,zz4,zz5,zz6,zz7,zz8,zz9";


    private static final String ADDRESS = "http://localhost:8040";

    private static final String X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";

    private static final String TARGETS = "yaowr,cuipx,laow,zhangs,lis,gebilw";

    private static final int EXEC_INTERVAL = -1;

    private static final int MONITOR_INTERVAL = 60;

    private static final String SEND_JSON_PATH = "json/TestNotifySend/send.json";

    private static final String DONE_JSON_PATH = "json/TestNotifySend/done.json";

    // MK服务访问地址 | MK验权请求头x-service-name | 测试人员账号 | 执行待办请求间隔 | 监控时间间隔
    // -Dmk.address=https://bipnew-sit.countrygarden.com.cn -Dmk.xServiceName=43534c48566d654e5031674d355238395259346736673d3d -Dmk.targets=youwei,chenqianbin01,leikun02,penghe,youqingyang,yangqinggong,lizhaohua05 -Dmk.execInterval.ms=100 -Dmk.monitorInterval.s=60
    // 完整启动命令：
    // java -jar -Dmk.address=https://bipnew-sit.countrygarden.com.cn -Dmk.xServiceName=43534c48566d654e5031674d355238395259346736673d3d -Dmk.targets=youwei,chenqianbin01,leikun02,penghe,youqingyang,yangqinggong,lizhaohua05 -Dmk.execInterval.ms=100 -Dmk.monitorInterval.s=60 lib\study-bgytest-0.0.1.SNAPSHOT.jar
    public static void main(String[] args) {
        log.info(">>>>>>>>>>>>>>>>> begin <<<<<<<<<<<<<<<<<");
        TestNotifySend unitTest = new TestNotifySend();
        // 启动监控线程
        new Thread(unitTest::monitor, "notify-monitor").start();

        // 执行消息发送和置已办操作
        unitTest.execute();

        // 通过控制台手动触发查询以及停止程序
        Scanner scanner = new Scanner(System.in);
        boolean stop = false;
        log.info("\n===请输入'stop'停止执行消息推送===" +
                "\n===再次输入'stop'则停止监控查询===" +
                "\n===输入其他任意字符立即触发监控查询===");
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

    private List<AtomicInteger> counts = new ArrayList<>(targets.size());

    private LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(100);

    private int execInterval = Integer.valueOf(System.getProperty("mk.execInterval.ms", String.valueOf(EXEC_INTERVAL)));

    private int monitorInterval = Integer.valueOf(System.getProperty("mk.monitorInterval.s", String.valueOf(MONITOR_INTERVAL)));

    private boolean execRunning = true;

    private boolean monitorRunning = true;


    // ===================== Constructor ===================== //

    public TestNotifySend() {
        for (int i = 0; i < targets.size(); i++) {
            counts.add(new AtomicInteger(0));
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
                    String entityId = String.valueOf(counts.get(idx).incrementAndGet());
                    try {
                        send(entityId, person);
                        done(entityId, person);
                    } catch (Exception e) {
                        counts.get(idx).decrementAndGet();
                        log.error("send or done todo exception!", e);
                    }
                    if (execInterval > 0) {
                        try {
                            Thread.sleep(execInterval);
                        } catch (InterruptedException e) {
                        }
                    }
                }
                long end = System.currentTimeMillis();
                log.info("execution stopped. [person={}, duration={}(s), count={}]",
                        person,
                        (end - begin) / 1000f,
                        counts.get(idx).get());
            }, "notify-executor-" + i).start();
        }
    }

    public void monitor() {
        while (true) {
            try {
                queue.poll(monitorInterval, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                // do noting
            }
            if (!monitorRunning) {
                break;
            }
            log.info("---------------- entityName: {} ----------------", this.entityName);
            long begin = System.currentTimeMillis();
            // 查询原始消息
//            List<Integer> oriSends = new ArrayList<>();
//            List<Integer> oriDones = new ArrayList<>();
//            for (int i = 0; i < this.targets.size(); i++) {
//                String target = this.targets.get(i);
//                long oriSend = findOriginal(target, "send");
//                long oriDone = findOriginal(target, "done");
//                oriSends.add(i, (int) oriSend);
//                oriDones.add(i, (int) oriDone);
//            }
            // 查询待办和已办
            List<Integer> findTodos = new ArrayList<>();
            List<Integer> findDones = new ArrayList<>();
            for (int i = 0; i < targets.size(); i++) {
                String target = targets.get(i);
                long findTodo = findByPerson(target, "todo");
                long findDone = findByPerson(target, "done");
                findTodos.add(i, (int) findTodo);
                findDones.add(i, (int) findDone);
            }
            long end = System.currentTimeMillis();
            for (int i = 0; i < targets.size(); i++) {
                log.info("find todo OK. [target={}, count={}, todo={}, done={}]",
                        targets.get(i),
                        counts.get(i).get(),
//                        oriSends.get(i),
//                        oriDones.get(i),
                        findTodos.get(i),
                        findDones.get(i));
            }
            log.info("duration: {}(s)", (end - begin) / 1000f);
        }
        log.info("monitor stopped.");
    }

    public void addPermit() {
        try {
            this.queue.add(1);
        } catch (Exception e) {
            log.error("add permit failed!", e);
        }
    }

    // ===================== 私有方法 ===================== //

    private void send(String entityId, String target) {
        call("send", SEND_JSON_PATH, entityId, target);
    }

    private void done(String entityId, String target) {
        call("done", DONE_JSON_PATH, entityId, target);
    }

    private void removeTodo(String entityId, String target) {
        call("removeTodo", DONE_JSON_PATH, entityId, target);
    }

    private void removeDone(String entityId, String target) {
        call("removeDone", DONE_JSON_PATH, entityId, target);
    }

    private void removeAll(String entityId, String target) {
        call("removeAll", DONE_JSON_PATH, entityId, target);
    }

    private void suspend(String entityId, String target) {
        call("suspend", DONE_JSON_PATH, entityId, target);
    }

    private void resume(String entityId, String target) {
        call("resume", DONE_JSON_PATH, entityId, target);
    }

    private void read(String entityId, String target) {
        call("read", DONE_JSON_PATH, entityId, target);
    }

    private void call(String method, String bodyPath, String entityId, String target) {
        // 请求地址
        String sendUrl = address + "/api/sys-notifybus/sysNotifyComponent/" + method;
        // 从JSON文件中获取请求体
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(bodyPath)).getFile();
        String sendJson = FileUtils.readFileToString(filePath, "utf-8");
        JSONObject body = JSONObject.parseObject(sendJson);
        body.put("subject", "消息完整性之kafka迁移测试" + entityId);
        body.put("entityId", entityId);
        body.put("entityName", entityName);
        body.put("entityKey", target);
        body.put("targets", Arrays.asList(target));
        body.put("orgProperty", "fdLoginName");

        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", xServiceName);
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

    private long findByPerson(String target, String todoOrDone) {
        String findUrl = address + "/api/sys-notify/baseSysNotify/findByPerson";
        // 构造查询条件
        JSONObject body = new JSONObject();
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("todoOrDone", todoOrDone);
        conditions.put("targets", Arrays.asList(target));
        conditions.put("fdEntityName", entityName);
        body.put("conditions", conditions);
        body.put("pageSize", 1);
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", xServiceName);
        try {
            String response = HttpClientUtils.httpPost(findUrl, body, header);
            QueryResult<JSONObject> queryResult = JSONObject.parseObject(response, QueryResult.class);
            return queryResult.getTotalSize();
        } catch (Exception e) {
            log.error("find todo exception! [target={}]", target, e);
        }
        return 0;
    }

    private long findOriginal(String target, String command) {
        String findUrl = address + "/api/sys-notifybus/sysNotifyOriginal/findAll";
        // 构造查询条件
        JSONObject body = new JSONObject();
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("fdCommand", command);
        conditions.put("fdEntityName", entityName);
        conditions.put("fdEntityKey", target);
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
            log.error("find original exception! [target={}, command={}]", target, command, e);
        }
        return 0;
    }
}
