package indi.rui.study.unittest.callapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.util.FileUtils;
import indi.rui.study.unittest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class CallApiSendALotOfTodo {

    private static final String address = "Http://127.0.0.1:8040";
    private static final String xServiceName = "73456775666d4c416f73776139584a4131432f6847413d3d";

    private static final int TOTAL = 5000;

    /**
     * 消息JSON文件路径
     */
    private static final String sendJsonPath = "json/CallApiSendALotOfTodo/send.json";

    /**
     * 执行待办发送线程池
     */
    private static final AtomicInteger threadCount = new AtomicInteger(0);
    private static final ThreadPoolExecutor senderPool = new ThreadPoolExecutor(16, 16,
            0, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100000),
            r -> new Thread(r, "todo-sender-" + threadCount.getAndIncrement()),
            new ThreadPoolExecutor.DiscardPolicy());

    public static void main(String[] args) {
        // 将所有待办置已办
        List<String> snidList = sendALotOfTodo();
        senderPool.shutdown();
        log.info("send a lot of todo success:\n{}", printLog(snidList));
    }

    private static String printLog(List<String> snidList) {
        if (snidList == null && snidList.isEmpty()) {
            return null;
        }
        StringBuffer buf = new StringBuffer();
        for (String snid : snidList) {
            buf.append(snid).append("\n");
        }
        return buf.toString();
    }

    private static List<String> sendALotOfTodo() {
        List<String> snidList = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(TOTAL);
        for (int i = 0; i < TOTAL; i++) {
            final int idx = i;
            senderPool.execute(() -> {
                try {
                    String url = address + "/api/sys-notifybus/sysNotifyComponent/send";
                    Map<String, String> httpHeaders = new HashMap<>();
                    httpHeaders.put("X-SERVICE-NAME", xServiceName);
                    httpHeaders.put("content-type", "application/json;charset=utf-8");
                    JSONObject json = FileUtils.loadJSON(sendJsonPath);
                    json.put("entityId", idx);
                    String httpResult = HttpClientUtils.httpPost(url, json, httpHeaders);
                    snidList.add(idx + ": " + JSON.parseObject(httpResult,
                            new TypeReference<MkResponse<String>>() {
                            }).getData());
                } catch (Exception e) {
                    log.error("send exception", e);
                }
                countDownLatch.countDown();
            });
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.warn("main thread is waiting for sending a lot of todo, but interrupted!");
        }
        return snidList;
    }
}
