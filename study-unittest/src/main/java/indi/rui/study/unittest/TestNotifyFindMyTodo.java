package indi.rui.study.unittest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.NotifyTodo;
import indi.rui.study.unittest.dto.QueryResult;
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
import java.util.*;

/**
 * @author: yaowr
 * @create: 2021-11-09
 */
@Slf4j
public class TestNotifyFindMyTodo implements MonitorTestPlan {

    private static final String sendJsonPath = "json/TestNotifyFindMyTodo/send.json";

    private static final String DONE_JSON_PATH = "json/TestNotifyFindMyTodo/done.json";

    private static final String KEY_PAIR_DIR = "keypair/";

    private static final String PUB_KEY_FILE = "pubkey.txt";

    @Value("mk.address")
    private String address;

    @Value("mk.xServiceName")
    private String xServiceName;

    private ThreadLocal<CookieStore> cookieStoreThreadLocal = ThreadLocal.withInitial(
            () -> new BasicCookieStore());

    @Override
    public String planName() {
        return "测试查询我的待办";
    }

    @Override
    public void test() {
        // 请求地址
        String url = address + "/api/sys-notifybus/sysNotifyComponent/send";
        // 从JSON文件中获取请求体
        String filePath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                sendJsonPath)).getFile();
        String sendJson = FileUtils.readFileToString(filePath, "utf-8");
        JSONObject body = JSONObject.parseObject(sendJson);
        body.put("entityKey", System.currentTimeMillis());
        // 需要x-service-name请求头验权
        Map<String, String> header = Collections.singletonMap("x-service-name", xServiceName);
        String response = null;
        try {
            response = HttpClientUtils.httpPost(url, body, header);
            MkResponse<String> mkResponse = JSONObject.parseObject(response, new TypeReference<MkResponse<String>>() {
            });
            if (!mkResponse.isSuccess()) {
                log.error("send todo failed! [url={}, body={}, response={}]",
                        url,
                        JSONObject.toJSONString(body),
                        response);
            } else {
                log.info("send todo success. [snid={}, context={}]",
                        mkResponse.getData(),
                        JSONObject.toJSONString(body));
            }
        } catch (Exception e) {
            log.error("send todo failed! [url={}, body={}, response={}]",
                    url,
                    JSONObject.toJSONString(body),
                    response, e);
        }
    }

    @Override
    public String monitor() {
        login("yaowr", "1");
        List<NotifyTodo> todos = findMyTodo();
//        StringBuffer buf = new StringBuffer();
//        for (NotifyTodo todo : todos) {
//            buf.append("[subject=").append(todo.getFdSubject())
//                    .append(", entityKey=").append(todo.getFdEntityKey())
//                    .append(", snid=").append(todo.getFdSnid())
//                    .append(", todoType=").append(todo.getFdType())
//                    .append(", todoLevel=").append(todo.getFdLevel())
//                    .append("]\n");
//        }
//        return buf.toString();
        return JSONObject.toJSONString(todos, SerializerFeature.PrettyFormat);
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
            if (!mkResponse.isSuccess()) {
                log.error("login failure: {} [url={}]",
                        JSON.toJSONString(mkResponse.getMsg()),
                        url);
            }
        } catch (Exception e) {
            log.error("login exception! [url={}]", url, e);
        }
    }

    public List<NotifyTodo> findMyTodo() {
        // 请求地址
        String url = address + "/data/sys-notify/sysNotifyTodo/my/list";
        // 构造查询条件
        JSONObject body = new JSONObject();
        body.put("offset", 0);
        body.put("pageSize", 5);
        Map<String, Object> conditions = new HashMap<>();
        body.put("conditions", conditions);
        ((Map) body.computeIfAbsent("sorts", (k) -> new HashMap<>()))
                .put("fdCreateTime", "DESC");
        String response = null;
        List<NotifyTodo> rtnList = Collections.emptyList();
        try {
            response = HttpClientUtils.httpPost(url, body, null, cookieStoreThreadLocal.get());
            MkResponse<QueryResult<NotifyTodo>> mkResponse = JSON.parseObject(response,
                    new TypeReference<MkResponse<QueryResult<NotifyTodo>>>() {
                    });
            if (mkResponse.isSuccess()) {
                rtnList = mkResponse.getData().getContent();
            } else {
                log.error("query todo list failed! [url={}, body={}, response={}]",
                        url,
                        JSONObject.toJSONString(body),
                        response);
            }
        } catch (Exception e) {
            log.error("query todo list failed! [url={}, body={}, response={}]",
                    url,
                    JSONObject.toJSONString(body),
                    response, e);
        }
        return rtnList;
    }
}
