package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.TimeComputingDTO;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

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


    public static void main(String[] args) throws Exception {
        // 1.发送消息或置已办
        String snid = sendOrDone("removeAll");
//        String snid = "1fpbsr2vqw36w2j51jw34j6svn2tljgnu9w0";
//        String snid = "1fpbt8id5w2uw1gt9mw3h3ffdv3amji2g3w0";
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
            json.put("entityKey", System.currentTimeMillis());
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
        } else {
            log.info("Not found time computing! snid={}", snid);
        }
        return success;
    }
}
