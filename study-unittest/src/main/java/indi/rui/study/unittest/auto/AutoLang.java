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

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://127.0.0.1:8040",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev01.ywork.me", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev01.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mktest.ywork.me", "jj01", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mktest.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkpre.ywork.me", "jm", "1", "pre");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkpre.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");


    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        // 开关
//        saveConfig();

        // 多语言编辑
//        findByMsgKeys();
//        updateByMsgKeys();
//        findByMsgKeys();

        // .json请求获取前端多语言
//        getFrontJson("http://127.0.0.1:8040/data/sys-lang/sys-console/desktop/locale/zh-cn.json?exactly=true");
//        getFrontJson("https://mkpre.ywork.me/data/sys-lang/sys-console/mobile/locale/en-us.json");
//        getFrontJson("https://mkpre.ywork.me/data/sys-lang/sys-console/manage/locale/en-us.json");
        getFrontJson("https://mkoppo.ywork.me/data/sys-lang/sys-formula/mobile/locale/zh-cn.json");
    }

//    /**
//     * 保存多语言编辑开关
//     */
//    private static void saveConfig() {
//        JSONObject body = new JSONObject();
//        body.put("langChangeSwitch", true);
//        log.info("save lang edit config: request={}", JSONObject.toJSONString(body, SerializerFeature.PrettyFormat));
//        MkResponse<?> mkResponse = mkDataRequestHelper.callDataForList(
//                "/data/sys-lang/langSwitchConfig/saveConfig", body, JSONObject.class);
//        log.info("save lang edit config: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
//    }
//
//    /**
//     * 查询多语言
//     */
//    private static void findByMsgKeys() {
//        String filePath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(
//                "json/AutoLang/find_by_msg_key.json")).getFile();
//        String jsonString = FileUtils.readFileToString(filePath, "utf-8");
//        JSONArray body = JSONArray.parseArray(jsonString);
//        log.info("find lang by msgKeys: request={}", JSONObject.toJSONString(body, SerializerFeature.PrettyFormat));
//        MkResponse<?> mkResponse = mkDataRequestHelper.callDataForList(
//                "/data/sys-lang/langEditPage/findByMsgKeys", body, JSONObject.class);
//        log.info("find lang by msgKeys: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
//    }
//
//    /**
//     * 更新多语言
//     */
//    private static void updateByMsgKeys() {
//        String filePath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource(
//                "json/AutoLang/update_by_msg_key.json")).getFile();
//        String jsonString = FileUtils.readFileToString(filePath, "utf-8");
//        JSONArray body = JSONArray.parseArray(jsonString);
//        log.info("update lang by msgKeys: request={}", JSONObject.toJSONString(body, SerializerFeature.PrettyFormat));
//        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
//                "/data/sys-lang/langEditPage/updateByMsgKeys", body);
//        log.info("update lang by msgKeys: response={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
//    }

    /**
     * 查询多语言
     */
    private static void getFrontJson(String url) {
        log.info("get lang json: url={}", url);
        MkDataRequestHelper  mkDataRequestHelper = new MkDataRequestHelper();
        String mkResponse = mkDataRequestHelper.httpGet(url);
        log.info("get lang json: response={}", JSONObject.parseObject(mkResponse).toString(SerializerFeature.PrettyFormat));
    }
}
