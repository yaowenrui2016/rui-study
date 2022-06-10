package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkEnvToEnvContext;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.dto.e2e.MkReferGroup;
import indi.rui.study.unittest.dto.e2e.MkReferTreeNode;
import indi.rui.study.unittest.dto.e2e.MkSysMechTptEnvToEnvTaskVO;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
//            = new MkDataRequestHelper("http://mkdev02.ywork.me", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev02.ywork.me",
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


    private static final String DOWNLOAD_PATH = System.getProperty("user.dir") + File.separator + "downloadTemp";


    public static void main(String[] args) throws InterruptedException {
//        loadApi("com.landray.sys.transport.demo.core.entity.ExampleEntity");

//        // 在线
//        String encOlCmd = onlineSourceGenerate();                             // 第1步：生成口令
////        String encOlCmd = "eyJjYWNoZUtleSI6InN5cy1tZWNoLXRyYW5zcG9ydDplbnYtdG8tZW52OmV4cG9ydFJlcXVlc3Q6eWFvd3I6Y29tLmxhbmRyYXkuc3lzLnRyYW5zcG9ydC5kZW1vLmNvcmUuZW50aXR5LkV4YW1wbGVFbnRpdHkiLCJkbnMiOiJodHRwOi8vMTI3LjAuMC4xOjgwNDAifQ==";
//        MkEnvToEnvContext context = onlineTargetLoadEntity(encOlCmd);         // 第2步：获取主文档
//        context = onlineTargetExportData(encOlCmd, context);
//        onlineTargetStartImport(encOlCmd, context);

        // 离线导出（本地）
        MkEnvToEnvContext context = offLineLoadReferTree("exportRequest.json");
        String filename = offLineExport(context.getReferGroupList());
//        String filename = "EnvToEnv_ExampleEntity.zip";
//        uploadAttach("C:\\Users\\yaowr\\Pictures\\temp\\2.jpg");
//        uploadAttach("D:\\project\\rui-study\\downloadTemp\\EnvToEnv_ExampleEntity_20220609160427.zip");
        String attachId = uploadAttach(DOWNLOAD_PATH + File.separator + filename);
//        String attachId = "1g1umpa28wofw17l7w2mdapn1tu4i532j6w0";
        context = loadEntityNodes(attachId);
        context = loadReferTree(context.getEntityNodes());
        context = loadOrgNodes(context.getReferGroupList());
        String taskId = offLineImportData(attachId, context.getOrgNodes());

        // 加载任务
        Thread.sleep(3000);
        findTask(taskId);

//        // 离线导出（dev02）
//        MkEnvToEnvContext context = offLineLoadReferTree("test/request_body.json");
//        String filename = offLineExport(context.getReferGroupList());
    }

    private static void loadApi(String entityName) {
        String response = mkApiRequestHelper.callApi(
                "/api/sys-admin/e2e/online/source/loadApi?entityName=" + entityName,
                null);
        log.info("load api: {}", JSONObject.toJSONString(response, SerializerFeature.PrettyFormat));
    }

    private static String onlineSourceGenerate() {
        JSONObject json = FileUtils.loadJSON("AutoMechTptEnvToEnv/exportRequest.json");
        MkResponse<String> response = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/online/source/generate",
                json, String.class);
        log.info("online generate: {}", response.getData());
        return response.getData();
    }

    private static MkEnvToEnvContext onlineTargetLoadEntity(String encOlCmd) {
        JSONObject json = new JSONObject();
        json.put("encOlCmd", encOlCmd);
        MkResponse<JSONObject> response = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/online/target/loadBriefEntity",
                json, JSONObject.class);
        log.info("online load brief entity data: {}", JSONObject.toJSONString(response.getData(), SerializerFeature.PrettyFormat));
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
        JSONObject json = (JSONObject) JSONObject.toJSON(context);
        json.put("encOlCmd", encOlCmd);
        MkResponse<MkEnvToEnvContext> response = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/online/target/exportData",
                json, MkEnvToEnvContext.class);
        log.info("Export data: {}", JSONObject.toJSONString(response, SerializerFeature.PrettyFormat));
        return response.getData();
    }

    private static MkEnvToEnvContext onlineTargetStartImport(String encOlCmd, MkEnvToEnvContext context) {
        JSONObject json = (JSONObject) JSONObject.toJSON(context);
        json.put("encOlCmd", encOlCmd);
        MkResponse<MkEnvToEnvContext> response = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/online/target/startImport",
                json, MkEnvToEnvContext.class);
        log.info("Start import: {}", JSONObject.toJSONString(response, SerializerFeature.PrettyFormat));
        return response.getData();
    }

    // ======================== 离线 ========================= //

    /**
     * 离线获取关联树
     */
    private static MkEnvToEnvContext offLineLoadReferTree(String filename) {
        JSONObject json = FileUtils.loadJSON("AutoMechTptEnvToEnv/" + filename);
        MkResponse<MkEnvToEnvContext> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/offline/source/loadReferTree", json, MkEnvToEnvContext.class);
        log.info("loadReferTree: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    /**
     * 离线获取关联树
     */
    private static String offLineExport(List<MkReferGroup> referGroupList) {
        List<MkReferTreeNode> referOperList = new ArrayList<>();
        for (MkReferGroup group : referGroupList) {
            for (MkReferTreeNode node : group.getReferNodes()) {
                MkReferTreeNode operNode = new MkReferTreeNode();
                operNode.setReferType(node.getReferType());
                operNode.setFdId(node.getFdId());
                operNode.setOperation("CHECKED");
                referOperList.add(operNode);
            }
        }
        JSONObject json = new JSONObject();
        json.put("nodeOperList", referOperList);
        String filename = mkDataRequestHelper.callDataDownload(
                "/data/sys-admin/e2e/offline/source/export", json, DOWNLOAD_PATH);
        log.info("export: {}", filename);
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
    private static MkEnvToEnvContext loadEntityNodes(String attachId) {
        JSONObject json = new JSONObject();
        json.put("attachId", attachId);
        MkResponse<MkEnvToEnvContext> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/offline/target/loadEntityNodes", json, MkEnvToEnvContext.class);
        log.info("loadEntityNodes: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    /**
     * 离线导出关联数据
     */
    private static MkEnvToEnvContext loadReferTree(List<MkReferTreeNode> entityNodes) {
        List<MkReferTreeNode> referOperList = new ArrayList<>();
        for (MkReferTreeNode node : entityNodes) {
            MkReferTreeNode operNode = new MkReferTreeNode();
            operNode.setReferType(node.getReferType());
            operNode.setFdId(node.getFdId());
            operNode.setOperation("NEW");
            referOperList.add(operNode);
        }
        JSONObject json = new JSONObject();
        json.put("nodeOperList", referOperList);
        MkResponse<MkEnvToEnvContext> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/offline/target/loadReferTree", json, MkEnvToEnvContext.class);
        log.info("loadReferTree: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    /**
     * 离线导出组织架构
     */
    private static MkEnvToEnvContext loadOrgNodes(List<MkReferGroup> referGroupList) {
        List<MkReferTreeNode> nodeOperList = new ArrayList<>();
        for (MkReferGroup group : referGroupList) {
            for (MkReferTreeNode node : group.getReferNodes()) {
                MkReferTreeNode operNode = new MkReferTreeNode();
                operNode.setReferType(node.getReferType());
                operNode.setFdId(node.getFdId());
                operNode.setOperation("NEW");
                nodeOperList.add(operNode);
            }
        }
        JSONObject json = new JSONObject();
        json.put("nodeOperList", nodeOperList);
        MkResponse<MkEnvToEnvContext> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/offline/target/loadOrgNodes", json, MkEnvToEnvContext.class);
        log.info("loadOrgNodes: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    /**
     * 离线导入数据
     */
    private static String offLineImportData(String attachId, List<MkReferTreeNode> orgNodes) {
        JSONObject replaceOrg = new JSONObject();
        replaceOrg.put("fdId", "11111");
        replaceOrg.put("fdName", "张三");
        List<MkReferTreeNode> nodeOperList = new ArrayList<>();
        for (MkReferTreeNode node : orgNodes) {
            MkReferTreeNode operNode = new MkReferTreeNode();
            operNode.setReferType(node.getReferType());
            operNode.setFdId(node.getFdId());
            operNode.setOperation("REPLACE");
            operNode.setReplaceOrg(replaceOrg);
            nodeOperList.add(operNode);
        }
        JSONObject json = new JSONObject();
        json.put("attachId", attachId);
        json.put("nodeOperList", nodeOperList);
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/offline/target/startImport", json, JSONObject.class);
        log.info("start import: {}", mkResponse.getData().toString(SerializerFeature.PrettyFormat));
        return mkResponse.getData().getString("taskId");
    }

    private static void findTask(String taskId) {
        Map<String, String> conditions = new LinkedHashMap<>();
        conditions.put("fdId", taskId);
        JSONObject json = new JSONObject();
        json.put("pageSize", 1000);
        json.put("conditions", conditions);
        MkResponse<QueryResult<MkSysMechTptEnvToEnvTaskVO>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-admin/e2e/importTask/list", json, MkSysMechTptEnvToEnvTaskVO.class);
        log.info("findTask: {}", JSONObject.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
    }
}
