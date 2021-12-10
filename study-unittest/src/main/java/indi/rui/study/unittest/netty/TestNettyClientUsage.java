package indi.rui.study.unittest.netty;

import com.alibaba.fastjson.JSON;
import indi.rui.study.unittest.netty.HttpClient;
import indi.rui.study.unittest.netty.HttpResult;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class TestNettyClientUsage {
    public static void main(String[] args) throws Exception {
        AtomicInteger threadNo = new AtomicInteger(0);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 16, 0,
                TimeUnit.SECONDS, new LinkedBlockingQueue<>(1000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "executor-" + threadNo.getAndIncrement());
            }
        });
        String queryString = URLEncoder.encode("param={\"username\":\"yaowr\"}", "utf-8");
        String url = "Http://localhost:8848/web?" + queryString;
        List<Integer> nums = new CopyOnWriteArrayList<Integer>();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        long beginTie = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            nums.add(i);
            String username = getUsername(i);
            executor.execute(() -> {
                String jsonStr = "{\"username\":\"" + username + "\", \"password\":\"1234\"}";
                HttpResult result = null;
                try {
                    result = HttpClient.post(url, JSON.parseObject(jsonStr), null);
                    String content = (String) result.getContent();
                    nums.remove(getNum(content));
                    log.info("{}", content);
                } catch (Exception e) {
                    log.error("post exception [{}]", username, e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        long endTime = System.currentTimeMillis();
        executor.shutdown();
        log.error("duration: [{}(s)], failure user {}",
                (endTime - beginTie) / 1000f,
                Arrays.toString(nums.toArray()));
    }

    private static String getUsername(int i) {
        return "user-" + i;
    }

    private static Integer getNum(String content) {
        return Integer.valueOf(content.substring(content.indexOf('-') + 1, content.indexOf('，')));
    }
}
