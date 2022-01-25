package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.TimeComputingDTO;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import indi.rui.study.unittest.util.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * 碧桂园待办完整性测试程序
 *
 * @author: yaowr
 * @create: 2021-10-22
 */
@Slf4j
public class AutoNotifyMultiTypeTimeComputing {

//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://127.0.0.1:8040",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev02.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "https://p.landray.com.cn",
            "7455654271706f49474936332f6857624757456a467a726c316838566b2f386f583350595477392b4c78593d");


    private static RedissonClient redissonClient = RedisUtils.getRedis(
            "redis://study.rui.ubuntu:6379", 3);
    private static RAtomicLong counter = redissonClient.getAtomicLong(
            "unittest:AutoNotifyMultiTypeTimeComputing:counter");

    /**
     * 存放多线程处理的结果
     */
    private static CopyOnWriteArrayList<Float> timeComputings = new CopyOnWriteArrayList<>();


    public static void main(String[] args) throws Exception {
//        mutiThread("send");
        run("removeAll");
        redissonClient.shutdown();
    }

    private static void mutiThread(String method) {
        int threadNum = 100;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNum);
        CountDownLatch countDownLatch = new CountDownLatch(threadNum);
        for (int i = 0; i < threadNum; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                    run(method);
                } catch (Throwable e) {
                    log.error("Run Exception");
                }
                countDownLatch.countDown();
            }, "runner-" + i).start();
//            try {
//                Thread.sleep(800);
//            } catch (InterruptedException e) {
//            }
        }
        try {
            countDownLatch.await();
            Collections.sort(timeComputings);
            log.info("({}):{}", timeComputings.size(), Arrays.toString(timeComputings.toArray()));
        } catch (InterruptedException e) {
        }
    }

    private static void run(String method) {
        // 1.发送消息或置已办
        String snid = sendOrDone(method);
        // 2.计算消息耗时
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            if (loadOriginalBySnid(snid)) {
                break;
            }
        }
    }

    private static String sendOrDone(String method) {
        // 发送消息或置已办
        String filename = method + ".json";
        if (!"send".equalsIgnoreCase(method)) {
            filename = "done.json";
        }
        JSONObject json = FileUtils.loadJSON(filename, AutoNotifyMultiTypeTimeComputing.class);
        if ("send".equalsIgnoreCase(method)) {
            json.put("entityKey", counter.getAndIncrement());
        }
        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/sysNotifyComponent/" + method,
                json, String.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Send or done todo error! errMsg=" + mkResponse.getMsg());
        }
        // 返回snid
        return mkResponse.getData();
    }

    private static boolean loadOriginalBySnid(String snid) {
        List<TimeComputingDTO> computingDTOS = mkApiRequestHelper.callApiForList(
                "/api/sys-notify/sysNotifyOriginal/timeComputing?snid=" + snid,
                null, TimeComputingDTO.class);
        boolean success = false;
        if (computingDTOS != null && computingDTOS.size() > 0) {
            log.info("Find time computing: snid={}, time={}",
                    snid,
                    JSONObject.toJSONString(computingDTOS, SerializerFeature.PrettyFormat));
            success = true;
            for (TimeComputingDTO dto : computingDTOS) {
                if ("todo".equals(dto.getProvider())) {
                    timeComputings.add(dto.getTotalDuration());
                }
            }
        } else {
            log.info("Not found time computing! snid={}", snid);
        }
        return success;
    }
}
