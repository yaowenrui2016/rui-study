package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.*;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class AutoNotifyTemplateStory2 {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmokemini.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmokemini.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev02.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev02.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

    private static final int MAX_TIMEOUT_MS = 10000;

    public static void main(String[] args) {
//        loadPreset();
//        checkExist();
//        save();
//        List<MkNotifyTemplate> templates = listNotifyTemplateDataRPC();
//        if (templates != null && !templates.isEmpty()) {
//            getTemplate(templates.get(0).getFdId());
//        }
//        loadByCode("test_code_1001");
//        sendTemplateNotify();
        edit("test_code_1001");
    }

    private static List<MkNotifyTemplate> listNotifyTemplateDataRPC() {
//        JSONObject request = FileUtils.loadJSON("notify-template-request.json", AutoNotifyTemplateStory2.class);
        JSONObject request = FileUtils.loadJSON("findAll.json", AutoNotifyTemplateStory2.class);
        MkResponse<QueryResult<MkNotifyTemplate>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-notify/sysNotifyTemplate/list", request, MkNotifyTemplate.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("list notify template error! errMsg=" + mkResponse.getMsg());
        }
        log.info("find notify template by '/data' interface: req={}, res={}",
                JSONObject.toJSONString(request),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData().getContent();
    }

    private static void listNotifyTemplateApiRPC() {
        JSONObject request = FileUtils.loadJSON("notify-template-request.json", AutoNotifyTemplateStory2.class);
        QueryResult result = mkApiRequestHelper.callApi(
                "/api/sys-notify/sysNotifyTemplate/findAll", request, QueryResult.class);
        log.info("find notify template by '/api' interface: req={}, res={}",
                JSONObject.toJSONString(request),
                JSONObject.toJSONString(result, SerializerFeature.PrettyFormat)
        );
    }

    /**
     * 检查编码存在
     */
    private static void checkExist() {
        String code = "module001";
        MkResponse<Boolean> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyTemplate/checkCodeExist?code=" + code, null, Boolean.class);
        log.info("check exist: {}", JSONObject.toJSONString(mkResponse));
    }

    /**
     * 检查编码存在
     */
    private static void getTemplate(String fdId) {
        JSONObject json = new JSONObject();
        json.put("fdId", fdId);
        MkResponse<MkNotifyTemplate> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyTemplate/get", json, MkNotifyTemplate.class);
        log.info("get template: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    /**
     * 保存模板
     */
    private static void save() {
        JSONObject json = FileUtils.loadJSON("save.json", AutoNotifyTemplateStory2.class);
        MkResponse<?> mkResponse = mkDataRequestHelper.callData("/data/sys-notify/sysNotifyTemplate/save", json);
        log.info("save template: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    private static void edit(String code) {
        MkNotifyTemplate template = loadByCode(code);
        template.setFdEnabled(false);
        template.setFdName(template.getFdName() + "_edit");
        List<MkNotifyTemplateDetailVO> details = template.getFdDetails();
        for (MkNotifyTemplateDetailVO detailVO : details) {
            List<String> notifyTypes = detailVO.getNotifyTypes();
            if (notifyTypes.contains("todo")) {
                notifyTypes.add("sms");
                continue;
            }
            if (notifyTypes.contains("email")) {
                notifyTypes.add("TT");
            }
        }
        JSONObject json = (JSONObject) JSONObject.toJSON(template);
        MkResponse<?> mkResponse = mkDataRequestHelper.callData("/data/sys-notify/sysNotifyTemplate/edit", json);
        log.info("edit template: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    /**
     * 根据编码获取模板
     *
     * @param code
     */
    private static MkNotifyTemplate loadByCode(String code) {
        MkNotifyTemplate template = mkApiRequestHelper.callApi(
                "/api/sys-notify/sysNotifyTemplate/loadByCode?code=" + code, null, MkNotifyTemplate.class);
        log.info("load template by code: {}", JSONObject.toJSONString(template, SerializerFeature.PrettyFormat));
        return template;
    }

    /**
     * 获取预置模板
     */
    private static void loadPreset() {
        MkResponse<MkNotifyTemplateMetaDTO> repVars = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyTemplate/loadPreset", null, MkNotifyTemplateMetaDTO.class);
        log.info("load preset template: {}", JSONObject.toJSONString(repVars, SerializerFeature.PrettyFormat));
    }

    private static void sendTemplateNotify() {
        JSONObject json = FileUtils.loadJSON("self_notify_template.json", AutoNotifyTemplateStory2.class);
        json.put("timestamp", System.currentTimeMillis());
        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/sysNotifyComponent/send",
                json, String.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Send or done todo error! errMsg=" + mkResponse.getMsg());
        }
    }
}
