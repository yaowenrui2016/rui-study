package indi.rui.study.unittest.support;

import indi.rui.study.unittest.netty.HttpClient;
import indi.rui.study.unittest.netty.HttpResult;
import indi.rui.study.unittest.util.FileUtils;
import indi.rui.study.unittest.util.Hex;
import indi.rui.study.unittest.util.RsaHelper;
import lombok.extern.slf4j.Slf4j;

import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;

/**
 * @author: yaowr
 * @create: 2021-11-17
 */
@Slf4j
public class MkLoginHelper {

    private static final String ADDRESS = "Http://127.0.0.1:8040";

    private static final String KEY_PAIR_DIR = "keypair/";

    private static final String PUB_KEY_FILE = "pubkey.txt";

    /**
     * 登录，返回X-Auth-Token
     *
     * @param username
     * @param password
     * @return X-Auth-Token
     */
    public static String login(String username, String password) {
        // 密码加密处理
        String pubKey = FileUtils.readFileToString(
                Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                        KEY_PAIR_DIR + PUB_KEY_FILE)).getFile(), "utf-8");
        String encPwd = RsaHelper.encode(Hex.decode(pubKey), password);
        // 请求地址
        String url = null;
        String xAuthToken = null;
        try {
            url = ADDRESS + "/data/sys-auth/login?" + "j_username=" + username + "&j_password=" + URLEncoder.encode(encPwd, "utf-8");
            HttpResult httpResult = HttpClient.post(url, null, null);
            if (Integer.valueOf(200).equals(httpResult.getStatus())) {
                List<String> cookies = httpResult.getHeaders().get("Set-Cookie");
                for (String cookie : cookies) {
                    if (cookie.startsWith("X-AUTH-TOKEN")) {
                        xAuthToken = cookie.substring(cookie.indexOf("=") + 1, cookie.indexOf(";"));
                    }
                }
            }
        } catch (Exception e) {
            log.error("Login exception! url={}", url, e);
        }
        return xAuthToken;
    }
}
