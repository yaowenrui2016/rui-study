package indi.rui.study.unittest.usage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.TodoExtVO;
import indi.rui.study.unittest.netty.HttpClient;
import indi.rui.study.unittest.netty.HttpResult;
import indi.rui.study.unittest.support.MkLoginHelper;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-11-22
 */
@Slf4j
public class TestNettyClientUsageKkSetting {

//    private static final String address = "http://mksmoke.ywork.me";
    private static final String address = "http://127.0.0.1:8040";

    public static void main(String[] args) throws Exception {
        // 获取待办同步扩展
        JSONObject preNotifyTypeVOs = get();
        log.info("Get KkSetting success.\n{}",
                JSON.toJSONString(preNotifyTypeVOs, SerializerFeature.PrettyFormat));
    }

    private static JSONObject get() throws Exception {
        // 登录用户
        String xAuthToken = MkLoginHelper.login("yaowr", "1");
        // 拉取来源系统和模块
        String url = address + "/data/tic-kk/kkSetting/get";
        HttpHeaders httpHeaders = new DefaultHttpHeaders(true);
        httpHeaders.set("X-AUTH-TOKEN", xAuthToken);
        HttpResult httpResult = HttpClient.post(url, null, httpHeaders);
        JSONObject resultContent = JSON.parseObject(httpResult.getContent(),
                new TypeReference<MkResponse<JSONObject>>() {
                }).getData();
        return resultContent;
    }
}
