//package indi.rui.study.unittest.auto;
//
//import com.alibaba.fastjson.JSONObject;
//import com.alibaba.fastjson.serializer.SerializerFeature;
//import indi.rui.study.unittest.dto.MkResponse;
//import indi.rui.study.unittest.dto.QueryResult;
//import indi.rui.study.unittest.dto.template.MkNotifyTemplate;
//import indi.rui.study.unittest.dto.template.NotifyTemplateDetailVO;
//import indi.rui.study.unittest.dto.template.NotifyTemplateMetaDTO;
//import indi.rui.study.unittest.support.MkApiRequestHelper;
//import indi.rui.study.unittest.support.MkDataRequestHelper;
//import indi.rui.study.unittest.util.FileUtils;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//
///**
// * @author: yaowr
// * @create: 2021-11-12
// */
//@Slf4j
//public class AutoNotifyTemplateStory2 {
//
////    private static MkDataRequestHelper mkDataRequestHelper
////            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
////    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
////            "http://127.0.0.1:8040",
////            "73456775666d4c416f73776139584a4131432f6847413d3d");
//
//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkoppo.ywork.me", "yaowr", "1", "oppo");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkoppo.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");
//
////    private static MkDataRequestHelper mkDataRequestHelper
////            = new MkDataRequestHelper("http://mksmokemini.ywork.me", "yaowr", "1");
////    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
////            "http://mksmokemini.ywork.me",
////            "73456775666d4c416f73776139584a4131432f6847413d3d");
//
////    private static MkDataRequestHelper mkDataRequestHelper
////            = new MkDataRequestHelper("http://mkdev02.ywork.me", "yaowr", "1");
////    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
////            "http://mkdev02.ywork.me",
////            "73456775666d4c416f73776139584a4131432f6847413d3d");
//
////    private static MkDataRequestHelper mkDataRequestHelper
////            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");
////    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
////            "http://mksmoke.ywork.me",
////            "73456775666d4c416f73776139584a4131432f6847413d3d");
//
//    private static final int MAX_TIMEOUT_MS = 10000;
//
//    public static void main(String[] args) {
////        loadPreset();
////        save("save.json");
////        save("save_front3.json");
////        save("save_front4.json");
////        List<MkNotifyTemplate> templates = listNotifyTemplateDataRPC();
////        if (templates != null && !templates.isEmpty()) {
////            for (MkNotifyTemplate template : templates) {
////                getTemplate(template.getFdId());
////            }
////        }
////        MkNotifyTemplate template = loadByCode("test_code_1001");
////        MkNotifyTemplate template = getByCode("test_code_1001");
////        if (template != null) {
////            updateStatus(template.getFdId(), true);
////            loadByCode(template.getFdCode());
////            updateStatus(template.getFdId(), false);
////            loadByCode(template.getFdCode());
////            edit(template);
////            loadByCode(template.getFdCode());
////        }
//        sendTemplateNotify();
////        deleteAll(templates.stream().map(MkNotifyTemplate::getFdId).collect(Collectors.toList()));
//    }
//
//    private static List<MkNotifyTemplate> listNotifyTemplateDataRPC() {
//        JSONObject request = FileUtils.loadJSON("AutoNotifyTemplateStory2/findAll.json");
//        MkResponse<QueryResult<MkNotifyTemplate>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
//                "/data/sys-notify/sysNotifyTemplate/list", request, MkNotifyTemplate.class);
//        if (!mkResponse.isSuccess()) {
//            throw new RuntimeException("list notify template error! errMsg=" + mkResponse.getMsg());
//        }
//        log.info("find notify template by '/data' interface: req={}, res={}",
//                JSONObject.toJSONString(request),
//                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
//        );
//        return mkResponse.getData().getContent();
//    }
//
//    private static void listNotifyTemplateApiRPC() {
//        JSONObject request = FileUtils.loadJSON("AutoNotifyTemplateStory2/notify-template-request.json");
//        QueryResult result = mkApiRequestHelper.callApi(
//                "/api/sys-notify/sysNotifyTemplate/findAll", request, QueryResult.class);
//        log.info("find notify template by '/api' interface: req={}, res={}",
//                JSONObject.toJSONString(request),
//                JSONObject.toJSONString(result, SerializerFeature.PrettyFormat)
//        );
//    }
//
//    /**
//     * 检查编码存在
//     *
//     * @param code
//     */
//    private static boolean checkExist(String code) {
//        MkResponse<Boolean> mkResponse = mkDataRequestHelper.callData(
//                "/data/sys-notify/sysNotifyTemplate/checkCodeExist?code=" + code, null, Boolean.class);
//        boolean exist = mkResponse.getData();
//        return exist;
//    }
//
//    /**
//     * 检查编码存在
//     */
//    private static void getTemplate(String fdId) {
//        JSONObject json = new JSONObject();
//        json.put("fdId", fdId);
//        MkResponse<MkNotifyTemplate> mkResponse = mkDataRequestHelper.callData(
//                "/data/sys-notify/sysNotifyTemplate/get", json, MkNotifyTemplate.class);
//        log.info("get template: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
//    }
//
//    /**
//     * 保存模板
//     */
//    private static void save(String jsonFileName) {
//        JSONObject json = FileUtils.loadJSON("AutoNotifyTemplateStory2/" + jsonFileName);
////        String code = (String) json.get("fdCode");
////        if (checkExist(code)) {
////            log.warn("The template code [{}] have already exists.", code);
////            return;
////        }
//        MkResponse<?> mkResponse = mkDataRequestHelper.callData("/data/sys-notify/sysNotifyTemplate/save", json);
//        log.info("save template: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
//    }
//
//    private static void edit(MkNotifyTemplate template) {
//        template.setFdEnabled(false);
//        template.setFdName(template.getFdName() + "_edit");
//        List<NotifyTemplateDetailVO> details = template.getFdDetails();
//        for (NotifyTemplateDetailVO detailVO : details) {
//            List<String> notifyTypes = detailVO.getNotifyTypes();
//            if (notifyTypes.contains("todo")) {
//                notifyTypes.add("sms");
//                continue;
//            }
//            if (notifyTypes.contains("email")) {
//                notifyTypes.add("TT");
//            }
//        }
//        JSONObject json = (JSONObject) JSONObject.toJSON(template);
//        MkResponse<?> mkResponse = mkDataRequestHelper.callData("/data/sys-notify/sysNotifyTemplate/edit", json);
//        log.info("edit template: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
//    }
//
//    private static void updateStatus(String fdId, boolean enabled) {
//        MkNotifyTemplate _template = new MkNotifyTemplate();
//        _template.setFdId(fdId);
//        _template.setFdEnabled(enabled);
//        JSONObject json = (JSONObject) JSONObject.toJSON(_template);
//        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
//                "/data/sys-notify/sysNotifyTemplate/updateStatus", json);
//        log.info("update template status for [{}] {}",
//                enabled,
//                mkResponse.isSuccess() ? "success" : "failed");
//    }
//
//    /**
//     * 根据编码获取模板
//     *
//     * @param code
//     */
//    private static MkNotifyTemplate loadByCode(String code) {
//        MkNotifyTemplate template = mkApiRequestHelper.callApi(
//                "/api/sys-notify/sysNotifyTemplate/loadByCode?code=" + code, null, MkNotifyTemplate.class);
//        log.info("load template by code: {}", JSONObject.toJSONString(template, SerializerFeature.PrettyFormat));
//        return template;
//    }
//
//    private static MkNotifyTemplate getByCode(String code) {
//        JSONObject json = new JSONObject();
//        json.put("fdCode", code);
//        MkResponse<MkNotifyTemplate> mkResponse = mkDataRequestHelper.callData(
//                "/data/sys-notify/sysNotifyTemplate/getByCode", json, MkNotifyTemplate.class);
//        MkNotifyTemplate template = mkResponse.getData();
//        log.info("getByCode template: {}",
//                JSONObject.toJSONString(template, SerializerFeature.PrettyFormat));
//        return template;
//    }
//
//    /**
//     * 获取预置模板
//     */
//    private static void loadPreset() {
//        MkResponse<NotifyTemplateMetaDTO> repVars = mkDataRequestHelper.callData(
//                "/data/sys-notify/sysNotifyTemplate/loadPreset", null, NotifyTemplateMetaDTO.class);
//        log.info("load preset template: {}", JSONObject.toJSONString(repVars, SerializerFeature.PrettyFormat));
//    }
//
//    private static void sendTemplateNotify() {
//        JSONObject json = FileUtils.loadJSON("AutoNotifyTemplateStory2/self_notify_template.json");
//        long timestamp = System.currentTimeMillis();
//        json.put("timestamp", timestamp);
//        json.put("entityKey", timestamp);
////        json.put("template", "paas.lbpm:lbpm.notify.template.review");
//        json.put("template", "paas.lbpm:lbpm.notify.template.append");
//        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
//                "/api/sys-notifybus/sysNotifyComponent/send",
//                json, String.class);
//        if (!mkResponse.isSuccess()) {
//            throw new RuntimeException("Send or done todo error! errMsg=" + mkResponse.getMsg());
//        }
//        log.info("send notify with template success!");
//    }
//
//    private static void deleteAll(List<String> ids) {
//        JSONObject json = new JSONObject();
//        json.put("fdIds", ids);
//        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
//                "/data/sys-notify/sysNotifyTemplate/deleteAll", json);
//        log.info("delete template {}: {}",
//                mkResponse.isSuccess() ? "success" : "failed",
//                JSONObject.toJSONString(ids));
//    }
//}
