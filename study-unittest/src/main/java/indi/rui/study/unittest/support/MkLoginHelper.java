package indi.rui.study.unittest.support;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import indi.rui.study.unittest.dto.MkLoginResult;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.util.FileUtils;
import indi.rui.study.unittest.util.Hex;
import indi.rui.study.unittest.util.HttpClientUtils;
import indi.rui.study.unittest.util.RsaHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author: yaowr
 * @create: 2021-11-17
 */
@Slf4j
public class MkLoginHelper {

    private static final String ADDRESS = "Http://127.0.0.1:8040";

    private static final String KEY_PAIR_DIR = "keypair/";

    private static final String PUB_KEY_FILE = ".txt";

    /**
     * 登录，返回X-Auth-Token
     *
     * @param username
     * @param password
     * @return X-Auth-Token
     */
    public static MkLoginResult login(String address, String username, String password, String verificationCode, String pubKeyFile) {
        Map<String, String> params = new HashMap<>();
        if (verificationCode != null && verificationCode.length() > 1) {
            params.put("verificationCode", verificationCode);
        }
        return doLoginWithPubKeyFile(address, username, password, params, pubKeyFile);
    }

    /**
     * 登录，返回X-Auth-Token
     *
     * @param username
     * @param password
     * @return X-Auth-Token
     */
    public static String login(String username, String password) {
        return login(ADDRESS, username, password);
    }

    /**
     * 登录，返回X-Auth-Token
     *
     * @param username
     * @param password
     * @return X-Auth-Token
     */
    public static String login(String address, String username, String password) {
        return doLogin(address, username, password).getXAuthToken();
    }

    /**
     * 登录，返回用户信息
     *
     * @param username
     * @param password
     * @return X-Auth-Token
     */
    public static JSONObject loginLoadUserInfo(String address, String username, String password) {
        return doLogin(address, username, password).getUserInfo();
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return X-Auth-Token
     */
    public static MkLoginResult doLogin(String address, String username, String password) {
        return doLoginWithPubKeyFile(address, username, password, null, null);
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return X-Auth-Token
     */
    public static MkLoginResult doLoginWithPubKeyFile(String address, String username, String password, Map<String, String> params, String pubKeyFile) {
        // 密码加密处理
        String pubKey = FileUtils.readFileToString(
                Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                        KEY_PAIR_DIR + "pubkey" + (StringUtils.isBlank(pubKeyFile) ? "" : "_" + pubKeyFile) + ".txt"))
                        .getFile(), "utf-8");
        return doLoginWithPubKey(address, username, password, params, pubKey);
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return X-Auth-Token
     */
    public static MkLoginResult doLoginWithPubKey(String address, String username, String password,
                                                  Map<String, String> params, String pubKey) {
        String encPwd = RsaHelper.encode(Hex.decode(pubKey), password);
        // 返回字段信息
        String xAuthToken = null;
        JSONObject userInfo = null;
        // 请求地址
        String url = null;
        boolean success = false;
        String errorMsg = null;
        try {
            CookieStore cookieStore = new BasicCookieStore();
            StringBuilder urlBuilder = new StringBuilder(address + "/data/sys-auth/login?");
            urlBuilder.append("j_username=" + username)
                    .append("&j_password=" + URLEncoder.encode(encPwd, "utf-8"));
            if (!MapUtils.isEmpty(params)) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    urlBuilder.append("&" + entry.getKey() + "=" + entry.getValue());
                }
            }
            url = urlBuilder.toString();
            String httpResult = HttpClientUtils.httpPost(url, null, null, cookieStore);
            if (httpResult != null) {
                MkResponse<JSONObject> mkResponse = JSONObject.parseObject(httpResult,
                        new TypeReference<MkResponse<JSONObject>>() {
                        });
                if (mkResponse.isSuccess()) {
                    for (Cookie cookie : cookieStore.getCookies()) {
                        if ("X-AUTH-TOKEN".equalsIgnoreCase(cookie.getName())) {
                            xAuthToken = cookie.getValue();
                            success = true;
                            break;
                        }
                    }
                    userInfo = mkResponse.getData();
                } else {
                    errorMsg = mkResponse.getMsg();
                }
            }
        } catch (Exception e) {
            Throwable thr = e;
            while (thr.getCause() != null) {
                thr = thr.getCause();
            }
            errorMsg = thr.getMessage();
        }

        if (!success) {
            throw new RuntimeException("username=" + username + ", password=" + password +
                    ", url=" + url + ", errMsg=" + errorMsg);
        } else {
            log.info("login url: {}", url);
        }
        // 返回结果
        MkLoginResult result = new MkLoginResult();
        result.setXAuthToken(xAuthToken);
        result.setUserInfo(userInfo);
        return result;
    }

    public static void main(String[] args) {
        login("yaowr", "1");
    }
}
