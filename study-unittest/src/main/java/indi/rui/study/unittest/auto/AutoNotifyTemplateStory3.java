package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.original.SysNotifyOriginalVO;
import indi.rui.study.unittest.dto.template.SysNotifyTemplateVO;
import indi.rui.study.unittest.dto.template.TemplateMetaDTO;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class AutoNotifyTemplateStory3 {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yuxd", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkoppo.ywork.me", "yaowr", "1", "oppo");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkoppo.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

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
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

    private static final int MAX_TIMEOUT_MS = 10000;

    public static void main(String[] args) {
        getTemplateMeta();
        String code = save("new_system_template.json");
        findByCode(code);
        String snid = send(code);
        timeComputing(snid);
    }

    /**
     * 获取消息模板元数据
     */
    private static void getTemplateMeta() {
        MkResponse<TemplateMetaDTO> repVars = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyTemplate/getTemplateMeta", null, TemplateMetaDTO.class);
        log.info("getTemplateMeta: {}", JSONObject.toJSONString(repVars, SerializerFeature.PrettyFormat));
    }

    /**
     * 新建模板
     */
    private static String save(String filename) {
        JSONObject json = FileUtils.loadJSON("AutoNotifyTemplateStory3/" + filename);
        String code = (String) json.get("fdCode");
        if (!checkExist(code)) {
            MkResponse<?> mkResponse = mkDataRequestHelper.callData("/data/sys-notify/sysNotifyTemplate/save", json);
            log.info("save template: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        } else {
            log.warn("The template code [{}] have already exists.", code);
        }
        return code;
    }

    /**
     * 检查编码存在
     *
     * @param code
     */
    private static boolean checkExist(String code) {
        JSONObject body = new JSONObject();
        body.put("fdCode", code);
        MkResponse<Boolean> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyTemplate/checkCodeExist", body, Boolean.class);
        boolean exist = mkResponse.getData();
        return exist;
    }

    /**
     * 根据编码查找模板
     */
    private static void findByCode(String code) {
        JSONObject json = new JSONObject();
        json.put("fdCode", code);
        MkResponse<SysNotifyTemplateVO> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyTemplate/findByCode", json, SysNotifyTemplateVO.class);
        log.info("findByCode: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    private static String send(String code) {
        JSONObject json = FileUtils.loadJSON("AutoNotifyTemplateStory3/send.json");
        json.put("entityKey", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        json.put("template", code);
        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/sysNotifyComponent/send",
                json, String.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Send or done todo error! errMsg=" + mkResponse.getMsg());
        }
        log.info("send notify success! snid={}", mkResponse.getData());
        return mkResponse.getData();
    }

    private static void timeComputing(String snid) {
        SysNotifyOriginalVO originalVO;
        while (true) {
            originalVO = mkApiRequestHelper.callApi(
                    "/api/sys-notify/sysNotifyOriginal/timeComputing?snid=" + snid,
                    null, SysNotifyOriginalVO.class);
            if (originalVO != null) {
                log.info("Load original timeComputing: {}", JSONObject.toJSONString(originalVO, SerializerFeature.PrettyFormat));
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            if (originalVO != null && originalVO.getTimeSpends().size() == originalVO.getFdAdditional().size()) {
                break;
            }
        }
    }

//    private static List<MkNotifyTemplate> listNotifyTemplateDataRPC() {
//        JSONObject request = FileUtils.loadJSON("AutoNotifyTemplateStory3/findAll.json");
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

//    private static void edit(SysNotifyTemplateVO template) {
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

//    private static void deleteAll(List<String> ids) {
//        JSONObject json = new JSONObject();
//        json.put("fdIds", ids);
//        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
//                "/data/sys-notify/sysNotifyTemplate/deleteAll", json);
//        log.info("delete template {}: {}",
//                mkResponse.isSuccess() ? "success" : "failed",
//                JSONObject.toJSONString(ids));
//    }
}
