package indi.rui.study.something.REST接口调用案例;

import indi.rui.study.something.HttpClientUtils;
import indi.rui.study.something.ThreadHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-10-20
 */
@Slf4j
public class TodoExtMonitor {

    private static final String ADDRESS = "http://localhost:8040";
    private static final String X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";


    private void monitor() {
        call("monitor");
    }
    private void clear() {
        call("clear");
    }

    private void call(String method) {
        String monitorUrl = ADDRESS + "/api/sys-lbpm-approval/demoTodoExt/" + method;
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", X_SERVICE_NAME);
        try {
            String response = HttpClientUtils.httpPost(monitorUrl, null, header);
            log.info("{} response: {}", method, response);
        } catch (Exception e) {
            log.error("{} exception", method, e);
        }
    }

    public static void main(String[] args) {
        TodoExtMonitor monitor = new TodoExtMonitor();
        monitor.clear();
        ThreadHelper.TimedRun(monitor::monitor, 500);
    }
}
