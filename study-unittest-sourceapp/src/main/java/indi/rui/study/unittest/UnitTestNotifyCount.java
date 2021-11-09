package indi.rui.study.unittest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.UserInfo;
import indi.rui.study.unittest.util.FileUtils;
import indi.rui.study.unittest.util.Hex;
import indi.rui.study.unittest.util.HttpClientUtils;
import indi.rui.study.unittest.util.RsaHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author: yaowr
 * @create: 2021-11-05
 */
@Slf4j
public class UnitTestNotifyCount {

    private static final String DEFAULT_ADDRESS = "http://localhost:8040";

    private static final String DEFAULT_X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";

    private static final String KEY_PAIR_DIR = "keypair/";

    private static final String PUB_KEY_FILE = "pubkey.txt";

    private static final ThreadLocal<CookieStore> COOKIE_THREAD_LOCAL = ThreadLocal.withInitial(
            () -> new BasicCookieStore());

    private static final ThreadLocal<UserInfo> USER_THREAD_LOCAL = new ThreadLocal<>();


    public static void main(String[] args) {
        List<String[]> users = Arrays.asList(
                new String[]{"yaowr", "1"},
                new String[]{"cuipx", "1"},
                new String[]{"laow", "1"},
                new String[]{"zhangs", "1"},
                new String[]{"lis", "1"});
        for (int i = 0; i < users.size(); i++) {
            String[] user = users.get(i);
            new Thread(() -> {
                login(user[0], user[1]);
                count();
            }).start();
        }
        for (Cookie cookie : COOKIE_THREAD_LOCAL.get().getCookies()) {
            log.info("cookie: {} => {}, {}", cookie.getName(), cookie.getValue(), cookie.getDomain());
        }
    }

    private static void login(String username, String password) {
        // 密码加密处理
        String pubKey = FileUtils.readFileToString(
                Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                        KEY_PAIR_DIR + PUB_KEY_FILE)).getFile(), "utf-8");
        String encPwd = RsaHelper.encode(Hex.decode(pubKey), password);
        // 请求地址
        String url = null;
        try {
            url = DEFAULT_ADDRESS + "/data/sys-auth/login?" + "j_username=" + username + "&j_password=" + URLEncoder.encode(encPwd, "utf-8");
            String response = HttpClientUtils.httpPost(url, null, null, COOKIE_THREAD_LOCAL.get());
            MkResponse<UserInfo> mkResponse = JSON.parseObject(response, new TypeReference<MkResponse<UserInfo>>() {
            });
            if (mkResponse.isSuccess()) {
                USER_THREAD_LOCAL.set(mkResponse.getData());
            } else {
                log.error("login failure: {} [url={}]",
                        JSON.toJSONString(mkResponse.getMsg()),
                        url);
            }
        } catch (Exception e) {
            log.error("login exception! [url={}]", url, e);
        }
    }

    private static void count() {
        // 请求地址
        String url = DEFAULT_ADDRESS + "/data/sys-notify/portlet/count";
        try {
            String response = HttpClientUtils.httpPost(url, null, null, COOKIE_THREAD_LOCAL.get());
            MkResponse<JSONArray> mkResponse = JSON.parseObject(response, new TypeReference<MkResponse<JSONArray>>() {
            });
            if (mkResponse.isSuccess()) {
                String username = null;
                if (!Objects.isNull(USER_THREAD_LOCAL.get())) {
                    username = USER_THREAD_LOCAL.get().getUserName();
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
