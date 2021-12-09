package indi.rui.study.unittest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.TimeComputingDTO;
import indi.rui.study.unittest.util.FileUtils;
import indi.rui.study.unittest.util.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 碧桂园待办完整性测试程序
 *
 * @author: yaowr
 * @create: 2021-10-22
 */
@Slf4j
public class TestNotifyMultiTypeTimeComputing {

//    private static final String ADDRESS = "https://bipnew-sit.countrygarden.com.cn";
//    private static final String X_SERVICE_NAME = "43534c48566d654e5031674d355238395259346736673d3d";

//    private static final String ADDRESS = "http://192.168.51.202:8050";
//    private static final String X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";

//    private static final String ADDRESS = "http://localhost:8040";
//    private static final String X_SERVICE_NAME = "73456775666d4c416f73776139584a4131432f6847413d3d";

    private static final String ADDRESS = "https://p.landray.com.cn";
    private static final String X_SERVICE_NAME = "7455654271706f49474936332f6857624757456a467a726c316838566b2f386f583350595477392b4c78593d";


    public static void main(String[] args) throws Exception {
        // 发送消息或置已办
        String snid = sendOrDone("send");
//        String snid = "1fmbvjfe6w1swmfluwbe0ast3ca0lle1f6w0";
        // 查询original
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

    private static boolean loadOriginalBySnid(String snid) throws Exception {
        String url = ADDRESS + "/api/sys-notify/sysNotifyOriginal/timeComputing?snid=" + snid;
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("X-SERVICE-NAME", X_SERVICE_NAME);
        httpHeaders.put("content-type", "application/json;charset=utf-8");
        String httpResult = HttpClientUtils.httpPost(url, null, httpHeaders);
        boolean success = false;
        if (httpResult != null && httpResult.length() > 0) {
            List<TimeComputingDTO> computingDTOS = JSONObject.parseObject(httpResult, new TypeReference<List<TimeComputingDTO>>() {
            });
            if (computingDTOS != null && computingDTOS.size() > 0) {
                log.info("Find original with snid '{}':\n{}",
                        snid,
                        JSONObject.toJSONString(computingDTOS, SerializerFeature.PrettyFormat));
                success = true;
            } else {
                log.debug("The original with snid '{}' not found!", snid);
            }
        } else {
            log.debug("The original with snid '{}' not found!", snid);
        }
        return success;
    }

    private static String sendOrDone(String method) throws Exception {
        // 发送消息或置已办
        String url = ADDRESS + "/api/sys-notifybus/sysNotifyComponent/" + method;
        Map<String, String> httpHeaders = new HashMap<>();
        httpHeaders.put("X-SERVICE-NAME", X_SERVICE_NAME);
        httpHeaders.put("content-type", "application/json;charset=utf-8");
        String filename = method + ".json";
        if (!"send".equalsIgnoreCase(method)) {
            filename = "done.json";
        }
        JSONObject json = FileUtils.loadJSON(filename, TestNotifyMultiTypeTimeComputing.class);
        if ("send".equalsIgnoreCase(method)) {
            json.put("entityKey", System.currentTimeMillis());
        }
        String httpResult = HttpClientUtils.httpPost(url, json, httpHeaders);
        return JSON.parseObject(httpResult, new TypeReference<MkResponse<String>>() {
        }).getData();
    }
}
