package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: yaowr
 * @create: 2022-03-08
 */
@Slf4j
public class AutoNotifySendALotCheckAmount {

    private static final int TOTAL = 1000;

    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040", "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me", "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev01.ywork.me", "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://10.253.1.18:8080", "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://192.168.51.202:8050", "73456775666d4c416f73776139584a4131432f6847413d3d");

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
                    JSONObject json = FileUtils.loadJSON("CallApiSendALotOfTodo/send.json");
                    json.put("notifyType", "todo");
                    json.put("subject", json.get("subject") + "_" + idx);
                    json.put("entityId", idx);
                    json.put("entityKey", "XXX-" + System.currentTimeMillis() + "-XXX");
                    json.put("level", (idx % 3) + 1);
                    // 添加接收人
                    json.put("targets", getSomeTargets());
                    MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                            "/api/sys-notifybus/sysNotifyComponent/send", json, String.class);
                    String snid = null;
                    if (mkResponse != null && mkResponse.isSuccess()) {
                        snid = mkResponse.getData();
                    }
                    snidList.add(idx + ": " + snid);
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

    private static List<String> getSomeTargets() {
        int count = 200;
        List<String> targets = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            targets.add("TestUser" + i);
        }
        return targets;
    }
}
