package indi.rui.study.unittest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.dto.SourceAppDTO;
import indi.rui.study.unittest.util.FileUtils;
import indi.rui.study.unittest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 来源系统push接口自动化测试
 *
 * @author: yaowr
 * @create: 2021-10-22
 */
@Slf4j
public class TestNotifySourceApp {

    private static final String DEFAULT_ADDRESS = "http://localhost:8040";

    private static final String DEFAULT_X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";

//    private static final String DEFAULT_ADDRESS = "http://10.253.0.228:8080";
//
//    private static final String DEFAULT_X_SERVICE_NAME = "43534c48566d654e5031674d355238395259346736673d3d";

    private static final int DEFAULT_EXECUTE_INTERVAL_MS = -1;

    private static final int DEFAULT_MONITOR_INTERVAL_S = 60;

    private static final String SPLITER = "_";

    private static final String USECASE_PREFIX = "usecase";

    private static final String EXPECTION = "expection";

    private static final String JSON_PATH_PREFIX = "json/TestNotifySourceApp/";

    private static final String JSON_PATH_SUFFIX = ".json";

    private static final String[] TEST_APPS = new String[]{"A", "B", "C", "D"};

    // 完整启动命令：
    // java -jar -Dmk.address=http://localhost:8040 -Dmk.xServiceName=73456775666d4c416f73776139584a4131432f6847413d3d -Dmk.execInterval.ms=100 -Dmk.monitorInterval.s=60 lib\study-unittest-sourceapp-0.0.1.SNAPSHOT.jar
    public static void main(String[] args) {
        log.info(">>>> 来源系统push接口自动化测试 <<<<");
        TestNotifySourceApp unitTest = new TestNotifySourceApp();
        // 启动监控
        unitTest.monitor();

        // 执行消息发送和置已办操作
        unitTest.execute();

        // 通过控制台手动触发查询以及停止程序
        Scanner scanner = new Scanner(System.in);
        log.info("\n===请输入'stop'停止执行===" +
                "\n===再次输入'stop'则停止监控查询===" +
                "\n===输入'exec'立即执行===" +
                "\n===输入其他任意字符立即触发监控查询===");
        while (true) {
            String input = scanner.next();
            if (unitTest.executorRunning && "stop".equalsIgnoreCase(input)) {
                unitTest.executorRunning = false;
                unitTest.wakeUpExecutor();
            } else if (unitTest.executorRunning && "exec".equalsIgnoreCase(input)) {
                unitTest.wakeUpExecutor();
            } else if (unitTest.monitorRunning && "stop".equalsIgnoreCase(input)) {
                unitTest.monitorRunning = false;
                unitTest.wakeUpMonitor();
                break;
            } else {
                unitTest.wakeUpMonitor();
            }
        }
        log.info("accomplish!");
    }

    // ===================== 成员变量 ===================== //

    private String address = System.getProperty("mk.address",
            DEFAULT_ADDRESS);

    private String xServiceName = System.getProperty("mk.xServiceName",
            DEFAULT_X_SERVICE_NAME);

    private int executeInterval = Integer.valueOf(System.getProperty("mk.executeInterval.ms", String.valueOf(
            DEFAULT_EXECUTE_INTERVAL_MS)));

    private int monitorInterval = Integer.valueOf(System.getProperty("mk.monitorInterval.s", String.valueOf(
            DEFAULT_MONITOR_INTERVAL_S)));

    private boolean executorRunning = false;

    private boolean monitorRunning = false;

    private long lastMonitorTime = System.currentTimeMillis();

    private LinkedBlockingQueue<Integer> executorQueue = new LinkedBlockingQueue<>(10);

    private LinkedBlockingQueue<Integer> monitorQueue = new LinkedBlockingQueue<>(10);

    private int usecaseAmount = 10;

    private AtomicInteger usecaseNum = new AtomicInteger(0);

    private List<Boolean> usecasesExecResult = new ArrayList<>(usecaseAmount);


    // ===================== Constructor ===================== //

    public TestNotifySourceApp() {
    }


    // ===================== 主要API ===================== //

    public void execute() {
        executorRunning = true;
        new Thread(() -> {
            long begin = System.currentTimeMillis();
            // 执行用例之前清理数据
            deleteApp(TEST_APPS);
            while (executorRunning) {
                int usecaseNo = usecaseNum.incrementAndGet();
                if (usecaseNo > usecaseAmount) {
                    usecaseNum.decrementAndGet();
                    executorRunning = false;
                    break;
                }
                try {
                    // 执行用例
                    push(usecaseNo);
                    String result = findResult(TEST_APPS);
                    String expect = getExpect(usecaseNo);
                    boolean success = expect.equals(result);
                    if (success) {
                        log.info("usecase {} => [success]", usecaseNo);
                    } else {
                        log.info("usecase {} => [failure]\nexpect:\n{}\nresult:\n{}",
                                usecaseNo,
                                expect,
                                result);
                    }
                    usecasesExecResult.add(usecaseNo - 1, success);
                } catch (Exception e) {
                    log.error("usecase {} run execute exception!", usecaseNo, e);
                }
                if (executeInterval > 0) {
                    try {
                        executorQueue.poll(executeInterval, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                    }
                }
            }
            long end = System.currentTimeMillis();
            log.info("execution stopped. [duration={}(s)]", (end - begin) / 1000f);
        }, "executor").start();
    }

    public void monitor() {
        monitorRunning = true;
        new Thread(() -> {
            while (true) {
                if (monitorInterval > 0) {
                    try {
                        monitorQueue.poll(monitorInterval, TimeUnit.SECONDS);
                    } catch (InterruptedException e) {
                    }
                }
                if (!monitorRunning) {
                    break;
                }
                long currentMonitorTime = System.currentTimeMillis();
                float monitorDuration = (currentMonitorTime - lastMonitorTime) / 1000f;
                lastMonitorTime = currentMonitorTime;
                long begin = System.currentTimeMillis();
                // 监控查询
                log.info("---------------- monitor interval: {}(s) ----------------\n" +
                                "{}\n" +
                                "finished usecase: [{}/{}]\n" +
                                "executor status: {}\n" +
                                "monitor  status: {}",
                        monitorDuration,
                        toMonitorString(usecasesExecResult),
                        usecaseNum.get(),
                        usecaseAmount,
                        executorRunning ? "running" : "stopped",
                        monitorRunning ? "running" : "stopped");
                long end = System.currentTimeMillis();
                log.info("duration: {}(s)\n",
                        (end - begin) / 1000f);
            }
            log.info("monitor stopped.");
        }, "monitor").start();
    }

    private String toMonitorString(List<Boolean> usecasesExecResult) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < usecasesExecResult.size(); i++) {
            buf.append(USECASE_PREFIX).append(i + 1).append(": ").append(usecasesExecResult.get(i))
                    .append("\n");
        }
        return buf.toString();
    }

    public void wakeUpMonitor() {
        try {
            monitorQueue.add(1);
        } catch (Exception e) {
            log.error("add permit failed!", e);
        }
    }

    public void wakeUpExecutor() {
        try {
            executorQueue.add(1);
        } catch (Exception e) {
            log.error("add permit failed!", e);
        }
    }

    // ===================== 私有方法 ===================== //

    private void push(int usecase) {
        // 请求地址
        String sendUrl = this.address + "/api/sys-notify/sysNotifySourceApp/push";
        // 从JSON文件中获取请求体
        String bodyPath = JSON_PATH_PREFIX + USECASE_PREFIX + usecase + JSON_PATH_SUFFIX;
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(bodyPath)).getFile();
        String jsonStr = FileUtils.readFileToString(filePath, "utf-8");
        JSONObject body = JSONObject.parseObject(jsonStr);
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", this.xServiceName);
        try {
            HttpClientUtils.httpPost(sendUrl, body, header);
        } catch (Exception e) {
            log.error("push app exception! [usecase={}]",
                    usecase, e);
        }
    }

    private String findResult(String... codes) {
        // 请求地址
        String url = this.address + "/api/sys-notify/sysNotifySourceApp/findAll";
        // 构造查询条件
        JSONObject body = new JSONObject();
        Map<String, Object> conditions = new HashMap<>();
        if (codes != null && codes.length > 0) {
            conditions.put("fdCode", Collections.singletonMap("$in", Arrays.asList(codes)));
        }
        body.put("conditions", conditions);
        body.put("columns", Arrays.asList("fdName", "fdCode", "fdSourceModules"));
        body.put("pageSize", 1000);
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", this.xServiceName);
        try {
            String response = HttpClientUtils.httpPost(url, body, header);
            ParserConfig config = new ParserConfig();
            config.putDeserializer(SourceAppDTO.class, new ObjectDeserializer() {
                @Override
                public <T> T deserialze(DefaultJSONParser defaultJSONParser, Type type, Object o) {
                    return null;
                }

                @Override
                public int getFastMatchToken() {
                    return 0;
                }
            });
//            QueryResult<SourceAppDTO> result = JSON.parseObject(response,
//                    new TypeReference<QueryResult<SourceAppDTO>>(){});
//            List<SourceAppDTO> apps = result.getContent();
            List<Object> content = JSONObject.parseObject(response, QueryResult.class).getContent();
            List<SourceAppDTO> apps = buildSourceAppDTO(content);
            appSort(apps);
            return JSONArray.toJSONString(apps);
        } catch (Exception e) {
            log.error("find app exception! ", e);
        }
        return null;
    }

    private List<SourceAppDTO> buildSourceAppDTO(List<Object> content) {
        List<SourceAppDTO> rtnList = new ArrayList<>();
        for (int i = 0; i < content.size(); i++) {
            JSONObject jsonObject = (JSONObject) content.get(i);
            SourceAppDTO dto = new SourceAppDTO();
            dto.setName((String) jsonObject.get("fdName"));
            dto.setCode((String) jsonObject.get("fdCode"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("fdSourceModules");
            if (jsonArray != null && !jsonArray.isEmpty()) {
                dto.setChildren(buildSourceAppDTO(jsonArray));
            }
            rtnList.add(dto);
        }
        return rtnList;
    }

    private void deleteApp(String... codes) {
        // 请求地址
        String url = this.address + "/api/sys-notify/sysNotifySourceApp/deleteByCode";
        // 请求体
        JSON body = new JSONArray(Arrays.asList(codes));
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", this.xServiceName);
        try {
            HttpClientUtils.httpPost(url, body, header);
        } catch (Exception e) {
            log.error("delete app exception! ", e);
        }
    }

    private void deleteModule(String... codes) {
        // 请求地址
        String url = this.address + "/api/sys-notify/sysNotifySourceModule/deleteByCode";
        // 请求体
        JSON body = new JSONArray(Arrays.asList(codes));
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", this.xServiceName);
        try {
            HttpClientUtils.httpPost(url, body, header);
        } catch (Exception e) {
            log.error("delete module exception! ", e);
        }
    }

    private String getExpect(int usecase) {
        // 获取期望结果进行比较
        String expectPath = JSON_PATH_PREFIX + USECASE_PREFIX + usecase + SPLITER + EXPECTION + JSON_PATH_SUFFIX;
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                expectPath)).getFile();
        String expectStr = FileUtils.readFileToString(filePath, "utf-8");
        List<SourceAppDTO> expectsApp = JSONArray.parseArray(expectStr, SourceAppDTO.class);
        appSort(expectsApp);
        return JSONArray.toJSONString(expectsApp);
    }

    private void appSort(List<SourceAppDTO> apps) {
        if (apps == null || apps.size() == 0) {
            return;
        }
        // 系统排序
        apps.sort(Comparator.comparing(SourceAppDTO::getCode));
        // 模块排序
        for (SourceAppDTO app : apps) {
            appSort(app.getChildren());
        }
    }
}
