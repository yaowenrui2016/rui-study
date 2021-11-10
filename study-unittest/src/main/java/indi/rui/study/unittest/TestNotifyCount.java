package indi.rui.study.unittest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.UserInfo;
import indi.rui.study.unittest.interf.MonitorTestPlan;
import indi.rui.study.unittest.interf.Value;
import indi.rui.study.unittest.util.FileUtils;
import indi.rui.study.unittest.util.Hex;
import indi.rui.study.unittest.util.HttpClientUtils;
import indi.rui.study.unittest.util.RsaHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * @author: yaowr
 * @create: 2021-11-05
 */
@Slf4j
public class TestNotifyCount implements MonitorTestPlan {

    private static final String KEY_PAIR_DIR = "keypair/";

    private static final String PUB_KEY_FILE = "pubkey.txt";

    private ThreadLocal<CookieStore> cookieStoreThreadLocal = ThreadLocal.withInitial(
            BasicCookieStore::new);

    private ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();

    @Value("mk.address")
    private String address;


    @Override
    public String planName() {
        return "消息count接口测试";
    }

    @Override
    public String monitor() {
        return null;
    }

    @Override
    public void test() {
        List<String[]> users = Arrays.asList(
                new String[]{"yaowr", "1"},
                new String[]{"cuipx", "1"},
                new String[]{"laow", "1"},
                new String[]{"zhangs", "1"},
                new String[]{"lis", "1"});
        CountDownLatch countDownLatch = new CountDownLatch(users.size());
        for (int i = 0; i < users.size(); i++) {
            String[] user = users.get(i);
            new Thread(() -> {
                // 先登录
                login(user[0], user[1]);
                // 查询统计
                count();
                countDownLatch.countDown();
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
        }
    }

    private void login(String username, String password) {
        // 密码加密处理
        String pubKey = FileUtils.readFileToString(
                Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                        KEY_PAIR_DIR + PUB_KEY_FILE)).getFile(), "utf-8");
        String encPwd = RsaHelper.encode(Hex.decode(pubKey), password);
        // 请求地址
        String url = null;
        try {
            url = address + "/data/sys-auth/login?" + "j_username=" + username + "&j_password=" + URLEncoder.encode(encPwd, "utf-8");
            String response = HttpClientUtils.httpPost(url, null, null, cookieStoreThreadLocal.get());
            MkResponse<UserInfo> mkResponse = JSON.parseObject(response, new TypeReference<MkResponse<UserInfo>>() {
            });
            if (mkResponse.isSuccess()) {
                userInfoThreadLocal.set(mkResponse.getData());
            } else {
                log.error("login failure: {} [url={}]",
                        JSON.toJSONString(mkResponse.getMsg()),
                        url);
            }
        } catch (Exception e) {
            log.error("login exception! [url={}]", url, e);
        }
    }

    private void count() {
        // 请求地址
        String url = address + "/data/sys-notify/portlet/count";
        try {
            String response = HttpClientUtils.httpPost(url, null, null, cookieStoreThreadLocal.get());
            MkResponse<JSONArray> mkResponse = JSON.parseObject(response, new TypeReference<MkResponse<JSONArray>>() {
            });
            if (mkResponse.isSuccess()) {
                String username = null;
                if (!Objects.isNull(userInfoThreadLocal.get())) {
                    username = userInfoThreadLocal.get().getUserName();
                }
                log.info("count success: \n[username={}, count={}]", username, JSON.toJSONString(mkResponse.getData()));
            } else {
                log.error("count failure: {} ", JSON.toJSONString(mkResponse.getMsg()));
            }
        } catch (Exception e) {
            log.error("count exception! [url={}]", url, e);
        }
    }
}
