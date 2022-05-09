package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkEnvToEnvContext;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-04-07
 */
@Slf4j
public class AutoMechTptEnvToEnv {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev02.ywork.me", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev02.ywork.me",
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


    private static final String DOWNLOAD_PATH = System.getProperty("user.dir") + File.separator + "downloadTemp";


    public static void main(String[] args) {
//        loadApi("com.landray.sys.transport.demo.core.entity.ExampleEntity");

        // 在线
//        String encOlCmd = onlineSourceGenerate();                             // 第1步：生成口令
//        String encOlCmd = "eyJjYWNoZUtleSI6InN5cy1tZWNoLXRyYW5zcG9ydDplbnYtdG8tZW52OmV4cG9ydFJlcXVlc3Q6eWFvd3I6Y29tLmxhbmRyYXkuc3lzLnRyYW5zcG9ydC5kZW1vLmNvcmUuZW50aXR5LkV4YW1wbGVFbnRpdHkiLCJkbnMiOiJodHRwOi8vMTI3LjAuMC4xOjgwNDAifQ==";
//        MkEnvToEnvContext context = onlineTargetLoadEntity(encOlCmd);         // 第2步：获取主文档
//        context = onlineTargetExportData(encOlCmd, context);

        // 离线
        String filename = offLineExport();
//        String filename = "EnvToEnv_ExampleEntity.zip";
//        uploadAttach("C:\\Users\\yaowr\\Pictures\\temp\\2.jpg");
        String attachId = uploadAttach(DOWNLOAD_PATH + File.separator + filename);
//        String attachId = "1g1umpa28wofw17l7w2mdapn1tu4i532j6w0";
        MkEnvToEnvContext context = loadBriefEntity(attachId);
        context = exportData(attachId, context.getBriefEntityList());
//        replaceOrg(context);
        offLineImportData(attachId, context.getBriefReferList());
    }

//    private static void replaceOrg(MkEnvToEnvContext context) {
//        List<MkEnvToEnvContext.ReferDataGroup> referDataGroups = context.getBriefReferList();
//        for (MkEnvToEnvContext.ReferDataGroup group : referDataGroups) {
//            for (JSONObject referData : group.getReferData()) {
//                referData.put("")
//            }
//        }
//    }

    private static void loadApi(String entityName) {
        String response = mkApiRequestHelper.callApi(
                "/api/sys-mech-transport/envToEnv/online/source/loadApi?entityName=" + entityName,
                null);
        log.info("load api: {}", JSONObject.toJSONString(response, SerializerFeature.PrettyFormat));
    }

    private static String onlineSourceGenerate() {
        JSONObject json = FileUtils.loadJSON("AutoMechTptEnvToEnv/exportRequest.json");
        MkResponse<String> response = mkDataRequestHelper.callData(
                "/data/sys-mech-transport/envToEnv/online/source/generate",
                json, String.class);
        log.info("generate: {}", JSONObject.toJSONString(response, SerializerFeature.PrettyFormat));
        return response.getData();
    }

    //
    private static MkEnvToEnvContext onlineTargetLoadEntity(String encOlCmd) {
        JSONObject json = new JSONObject();
        json.put("encOlCmd", encOlCmd);
        MkResponse<JSONObject> response = mkDataRequestHelper.callData(
                "/data/sys-mech-transport/envToEnv/online/target/loadBriefEntity",
                json, JSONObject.class);
        log.info("Load brief entity data: {}", JSONObject.toJSONString(response.getData(), SerializerFeature.PrettyFormat));
        MkEnvToEnvContext context = new MkEnvToEnvContext();
        try {
            BeanUtils.copyProperties(context, response.getData());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return context;
    }

    private static MkEnvToEnvContext onlineTargetExportData(String encOlCmd, MkEnvToEnvContext context) {
        JSONObject json = FileUtils.loadJSON("AutoMechTptEnvToEnv/loadEntityResult.json");
        if (json.isEmpty()) {
            json = (JSONObject) JSONObject.toJSON(context);
        }
        json.put("encOlCmd", encOlCmd);
        MkResponse<MkEnvToEnvContext> response = mkDataRequestHelper.callData(
                "/data/sys-mech-transport/envToEnv/online/target/exportData",
                json, MkEnvToEnvContext.class);
        log.info("Export data: {}", JSONObject.toJSONString(response, SerializerFeature.PrettyFormat));
        return response.getData();
    }

    // ======================== 离线 ========================= //

    /**
     * 离线导出
     */
    private static String offLineExport() {
        JSONObject json = FileUtils.loadJSON("AutoMechTptEnvToEnv/exportRequest.json");
        String filename = mkDataRequestHelper.callDataDownload(
                "/data/sys-mech-transport/envToEnv/offline/export", json, DOWNLOAD_PATH);
        log.info("download: {}", filename);
        return filename;
    }

    /**
     * 上传附件
     */
    private static String uploadAttach(String filePath) {
        String response = mkDataRequestHelper.callDataUpload(
                "/data/sys-attach/upload", new File(filePath));
        MkResponse<JSONObject> mkResponse = JSONObject.parseObject(response, new TypeReference<MkResponse<JSONObject>>(JSONObject.class) {
        });
        // {"fdAttachFileId":"1g1n3qka3w1vw3sw1sct9s8ojnci633s6dw0","fdFileName":"EnvToEnv_ExampleEntity.zip"}
        // {"fdAttachFileId":"1g1pth0i7w2ew13wd4n8k23m82o782c46bw0","fdFileName":"2.jpg"}
        JSONObject attachInfo = mkResponse.getData();
        log.info("upload: attachId={}", attachInfo.toString());
        return (String) attachInfo.get("fdAttachFileId");
    }

    /**
     * 离线加载主文档
     */
    private static MkEnvToEnvContext loadBriefEntity(String attachId) {
        JSONObject json = new JSONObject();
        json.put("attachId", attachId);
        MkEnvToEnvContext context = mkDataRequestHelper.callData(
                "/data/sys-mech-transport/envToEnv/offline/loadBriefEntity", json, MkEnvToEnvContext.class).getData();
        log.info("load brief entity: {}", JSONObject.toJSONString(context.getBriefEntityList(), SerializerFeature.PrettyFormat));
        return context;
    }

    /**
     * 离线导出关联数据
     */
    private static MkEnvToEnvContext exportData(String attachId, List<JSONObject> briefEntityList) {
        JSONObject json = new JSONObject();
        json.put("attachId", attachId);
        json.put("briefEntityList", briefEntityList);
        MkEnvToEnvContext context = mkDataRequestHelper.callData(
                "/data/sys-mech-transport/envToEnv/offline/exportData", json, MkEnvToEnvContext.class).getData();
        log.info("export data: {}", JSONObject.toJSONString(context.getBriefReferList(), SerializerFeature.PrettyFormat));
        return context;
    }

    /**
     * 离线导入数据
     */
    private static void offLineImportData(String attachId, List<MkEnvToEnvContext.ReferDataGroup> briefReferList) {
        JSONObject json = new JSONObject();
        json.put("attachId", attachId);
        json.put("briefReferList", briefReferList);
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-mech-transport/envToEnv/offline/startImport", json, JSONObject.class);
        log.info("start import: {}", mkResponse.getData().toString(SerializerFeature.PrettyFormat));
    }
}
