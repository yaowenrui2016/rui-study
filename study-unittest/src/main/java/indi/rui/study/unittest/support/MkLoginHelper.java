package indi.rui.study.unittest.support;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import indi.rui.study.unittest.dto.MkLoginResult;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.UserInfo;
import indi.rui.study.unittest.util.FileUtils;
import indi.rui.study.unittest.util.Hex;
import indi.rui.study.unittest.util.HttpClientUtils;
import indi.rui.study.unittest.util.RsaHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;

import java.net.URLEncoder;
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
    public static UserInfo loginLoadUserInfo(String address, String username, String password) {
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
        return doLogin(address, username, password, null);
    }

    /**
     * 登录
     *
     * @param username
     * @param password
     * @return X-Auth-Token
     */
    public static MkLoginResult doLogin(String address, String username, String password, String pubKeyFile) {
        // 密码加密处理
        String pubKey = FileUtils.readFileToString(
                Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                        KEY_PAIR_DIR + "pubkey" + (StringUtils.isBlank(pubKeyFile) ? "" : "_" + pubKeyFile) + ".txt"))
                        .getFile(), "utf-8");
        String encPwd = RsaHelper.encode(Hex.decode(pubKey), password);
        // 返回字段信息
        String xAuthToken = null;
        UserInfo userInfo = null;
        // 请求地址
        String url = null;
        boolean success = false;
        String errorMsg = null;
        try {
            CookieStore cookieStore = new BasicCookieStore();
            url = address + "/data/sys-auth/login?" + "j_username=" + username + "&j_password=" + URLEncoder.encode(encPwd, "utf-8");
            String httpResult = HttpClientUtils.httpPost(url, null, null, cookieStore);
            if (httpResult != null) {
                MkResponse<UserInfo> mkResponse = JSONObject.parseObject(httpResult,
                        new TypeReference<MkResponse<UserInfo>>() {
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
        }
        // 返回结果
        MkLoginResult result = new MkLoginResult();
        result.setXAuthToken(xAuthToken);
        result.setUserInfo(userInfo);
        return result;
    }
}
