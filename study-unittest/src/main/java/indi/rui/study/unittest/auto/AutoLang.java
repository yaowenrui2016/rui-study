package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

/**
 * @author: yaowr
 * @create: 2022-08-10
 */
@Slf4j
public class AutoLang {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev01.ywork.me", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev01.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mktest.ywork.me", "jj01", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mktest.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmokemini.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmokemini.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");


    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        // 开关
        saveConfig();

        // 多语言编辑
//        findByMsgKeys();
        updateByMsgKeys();
//        findByMsgKeys();

        // .json请求获取前端多语言
        getFrontJson("sys-lbpm/manage/locale", "zh_cn");
        getFrontJson("sys-lbpm/mobile/locale", "zh_hk");
        getFrontJson("sys-lbpm/desktop/locale", "en_us");
    }

    /**
     * 保存多语言编辑开关
     */
    private static void saveConfig() {
        JSONObject body = new JSONObject();
        body.put("langChangeSwitch", true);
        log.info("save lang edit config: request={}", JSONObject.toJSONString(body, SerializerFeature.PrettyFormat));
        MkResponse<?> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-lang/langSwitchConfig/saveConfig", body, JSONObject.class);
        log.info("save lang edit config: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    /**
     * 查询多语言
     */
    private static void findByMsgKeys() {
        String filePath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(
                "json/AutoLang/find_by_msg_key.json")).getFile();
        String jsonString = FileUtils.readFileToString(filePath, "utf-8");
        JSONArray body = JSONArray.parseArray(jsonString);
        log.info("find lang by msgKeys: request={}", JSONObject.toJSONString(body, SerializerFeature.PrettyFormat));
        MkResponse<?> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-lang/langEditPage/findByMsgKeys", body, JSONObject.class);
        log.info("find lang by msgKeys: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    /**
     * 更新多语言
     */
    private static void updateByMsgKeys() {
        String filePath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(
                "json/AutoLang/update_by_msg_key.json")).getFile();
        String jsonString = FileUtils.readFileToString(filePath, "utf-8");
        JSONArray body = JSONArray.parseArray(jsonString);
        log.info("update lang by msgKeys: request={}", JSONObject.toJSONString(body, SerializerFeature.PrettyFormat));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-lang/langEditPage/updateByMsgKeys", body);
        log.info("update lang by msgKeys: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    /**
     * 查询多语言
     */
    private static void getFrontJson(String path, String langType) {
        String url = "http://127.0.0.1:8040/data/sys-lang/" + path + "/" + langType + ".json";
        log.info("get lang json: url={}", url);
        String mkResponse = mkDataRequestHelper.httpGet(url);
        log.info("get lang json: response={}", JSONObject.parseObject(mkResponse).toString(SerializerFeature.PrettyFormat));
    }
}
