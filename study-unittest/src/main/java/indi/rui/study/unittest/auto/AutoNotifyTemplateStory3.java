package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.dto.original.SysNotifyOriginalVO;
import indi.rui.study.unittest.dto.template.SysNotifyTemplateVO;
import indi.rui.study.unittest.dto.template.TemplateMetaDTO;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class AutoNotifyTemplateStory3 {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://mkpre.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://10.251.9.250:8080",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev02.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev02.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkoppo.ywork.me", "yaowr", "1", "oppo");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkoppo.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkoppore.ywork.me", "liq", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkoppore.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkpro.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkpro.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mktest.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://10.251.7.103:8080",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://10.253.2.132:8080",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkzszq.ywork.me", "liq", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkzszq.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper = null;
//    //    private static MkDataRequestHelper mkDataRequestHelper
////            = new MkDataRequestHelper("http://mkyfdyf.ywork.me", "liq", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "https://mkyfdyf.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");


    public static void main(String[] args) {
//        // 获取模板元数据
//        getTemplateMeta();
//        // 新建系统模板
//        String code = save("create_notifyTemplate.json");
//        // 根据编码查找模板
//        findByCode("P001");
//        // 查询模板列表
//        findAll();


//        // 创建会议将消息模板作为机制保存
//        String conferenceId = createConference();
//        // 查询会议将消息模板作为机制加载
//        getConference(conferenceId);
//        // 修改会议并修改消息模板机制
//        editConference(conferenceId);
//        // 查询会议将消息模板作为机制加载
//        getConference(conferenceId);
//        // 删除会议并删除消息模板机制
//        deleteConference(conferenceId);


        // 使用模板发送待办
        String snid = send("P001");
//        // 查看待办原始记录
//        timeComputing(snid);


//        // 获取默认邮件底板
//        getDefaultBaseplate();
//        // 保存默认邮件底板
//        setDefaultBaseplate();


//        // 使用模板发送邮件
//        String snid = sendEmail("$common:10001");
//        // 查看邮件原始记录
//        timeComputing(snid);


//        // 发送待办
//        String snid = sendTodo("test/send.json");
//        // 查看待办原始记录
//        timeComputing(snid);


//        // 根据编码查找模板
//        SysNotifyTemplateVO templateVO = findByCode("P001");
//        // 删除模板
//        delete(templateVO.getFdId());


//        // 测试导入模板
//        testImport();
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
    private static SysNotifyTemplateVO findByCode(String code) {
        JSONObject json = new JSONObject();
        json.put("fdCode", code);
        MkResponse<SysNotifyTemplateVO> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyTemplate/findByCode", json, SysNotifyTemplateVO.class);
        log.info("findByCode: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    private static void findAll() {
        JSONObject queryRequest = new JSONObject();
        queryRequest.put("pageSize", 1000);
        MkResponse<QueryResult<SysNotifyTemplateVO>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-notify/sysNotifyTemplate/list", queryRequest, SysNotifyTemplateVO.class);
        log.info("findAll: {}", JSONObject.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
    }

    private static void delete(String fdId) {
        JSONObject idsDTO = new JSONObject();
        idsDTO.put("fdIds", Collections.singletonList(fdId));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyTemplate/deleteAll", idsDTO);
        log.info("delete: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    /**
     * 创建会议将消息模板作为机制保存
     */
    private static String createConference() {
        JSONObject newConference = FileUtils.loadJSON("AutoNotifyTemplateStory3/mechanisms/createTemplateByMechanisms.json");
        newConference.put("fdCode", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        MkResponse<JSONObject> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/data/sys-notify-example/conference/create", newConference, JSONObject.class);
        if (mkResponse.isSuccess()) {
            log.info("createConference success: {}", JSONObject.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
        } else {
            log.error("createConference failed!");
        }
        return mkResponse.getData().getString("fdId");
    }

    /**
     * 修改会议并修改消息模板机制
     */
    private static void editConference(String conferenceId) {
        JSONObject editConference = FileUtils.loadJSON("AutoNotifyTemplateStory3/mechanisms/editTemplateByMechanisms.json");
        editConference.put("fdId", conferenceId);
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify-example/conference/edit", editConference);
        if (mkResponse.isSuccess()) {
            log.info("editConference success: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        } else {
            log.error("editConference failed!");
        }
    }

    /**
     * 删除会议并删除消息模板机制
     */
    private static void deleteConference(String conferenceId) {
        JSONObject idVO = new JSONObject();
        idVO.put("fdId", conferenceId);
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify-example/conference/delete", idVO);
        if (mkResponse.isSuccess()) {
            log.info("deleteConference success: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        } else {
            log.error("deleteConference failed!");
        }
    }

    /**
     * 获取默认邮件底板
     */
    private static JSONObject getDefaultBaseplate() {
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/emailSetting/getDefaultBaseplate", null, JSONObject.class);
        log.info("getDefaultBaseplate: {}", JSONObject.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }


    /**
     * 保存默认邮件底板
     */
    private static void setDefaultBaseplate() {
        String filepath = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource(
                "json/AutoNotifyTemplateStory3/email/default_email_base.html")).getFile();
        String html = FileUtils.readFileToString(filepath, "utf-8");
        JSONObject body = new JSONObject();
        body.put("fdBaseplate", html);
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/emailSetting/setDefaultBaseplate", body, JSONObject.class);
        log.info("setDefaultBaseplate: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    /**
     * 查询会议将消息模板作为机制加载
     */
    private static JSONObject getConference(String conferenceId) {
        Map<String, Object> mechanisms = new HashMap<>();
        mechanisms.put("load", "*");
        JSONObject idVO = new JSONObject();
        idVO.put("fdId", conferenceId);
        idVO.put("mechanisms", mechanisms);
        JSONObject conference = mkApiRequestHelper.callApi(
                "/api/sys-notify-example/conference/loadById", idVO, JSONObject.class);
        if (conference != null) {
            log.info("getConference success: {}", JSONObject.toJSONString(conference, SerializerFeature.PrettyFormat));
        } else {
            log.error("getConference failed!");
        }
        return conference;
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
        log.info("send notify success! mkResponse={}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    private static void testImport() {
        JSONObject template = FileUtils.loadJSON("AutoNotifyTemplateStory3/import/data.json");
        JSONObject body = new JSONObject();
        body.put("complete", template);
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyTemplate/testImport", body);
        log.info("testImport: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }

    private static String sendTodo(String filename) {
        JSONObject json = FileUtils.loadJSON("AutoNotifyTemplateStory3/" + filename);
        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/sysNotifyComponent/send",
                json, String.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Send todo error! errMsg=" + mkResponse.getMsg());
        }
        log.info("send notify success! snid={}", mkResponse.getData());
        return mkResponse.getData();
    }

    private static String sendEmail(String templateCode) {
        JSONObject json = FileUtils.loadJSON("AutoNotifyTemplateStory3/email/send.json");
        json.put("entityKey", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        if (templateCode != null) {
            json.put("template", templateCode);
        }
        MkResponse<String> mkResponse = mkApiRequestHelper.callApiForMkResponse(
                "/api/sys-notifybus/sysNotifyComponent/send",
                json, String.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Send or done todo error! errMsg=" + mkResponse.getMsg());
        }
        log.info("send email success! snid={}", mkResponse.getData());
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
}
