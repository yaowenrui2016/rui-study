package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.dto.e2e.E2EContext;
import indi.rui.study.unittest.dto.e2e.MkSysMechTptEnvToEnvTaskVO;
import indi.rui.study.unittest.dto.e2e.ReferGroup;
import indi.rui.study.unittest.dto.e2e.ReferTreeNode;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2022-04-07
 */
@Slf4j
public class AutoAdminE2E {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "lqtest002", "1");
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
        // 异步导出功能
        asyncLoad("exportRequest.json");

        // 轮询导出进度
        pollProgress();

//        // 取消导出
//        abortAsyncLoad();

        // 离线导出（本地）
        E2EContext context = offLineLoadReferTree("exportRequest.json");

        String filename = offLineExport(context.getReferGroupList());
//        String filename = "EnvToEnv_ExampleEntity.zip";
//        uploadAttach("C:\\Users\\yaowr\\Pictures\\temp\\2.jpg");
//        uploadAttach("D:\\project\\rui-study\\downloadTemp\\EnvToEnv_ExampleEntity_20220609160427.zip");

        // 离线导入
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
//        E2EContext context = offLineLoadReferTree("test/request_body.json");
//        String filename = offLineExport(context.getReferGroupList());
    }

    /**
     * 异步导出请求
     */
    private static void asyncLoad(String filename) {
        JSONObject body = FileUtils.loadJSON("AutoAdminE2E/" + filename);
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/offline/source/asyncLoad", body, E2EContext.class);
        log.info("asyncLoad: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }

    /**
     * 轮询导出进度
     */
    private static void pollProgress() {
        JSONObject body = new JSONObject();
        MkResponse<E2EContext> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/offline/source/pollProgress", body, E2EContext.class);
        log.info("pollProgress: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }

    /**
     * 轮询导出进度
     */
    private static void abortAsyncLoad() {
        JSONObject body = new JSONObject();
        MkResponse<E2EContext> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/offline/source/abortAsyncLoad", body, E2EContext.class);
        log.info("abortAsyncLoad: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }

    /**
     * 离线获取关联树
     */
    private static E2EContext offLineLoadReferTree(String filename) {
        JSONObject json = FileUtils.loadJSON("AutoAdminE2E/" + filename);
        MkResponse<E2EContext> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/offline/source/loadReferTree", json, E2EContext.class);
        log.info("loadReferTree: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    /**
     * 离线获取关联树
     */
    private static String offLineExport(List<ReferGroup> referGroupList) {
        List<ReferTreeNode> referOperList = new ArrayList<>();
        for (ReferGroup group : referGroupList) {
            for (ReferTreeNode node : group.getReferNodes()) {
                ReferTreeNode operNode = new ReferTreeNode();
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
                "/data/sys-attach/upload", new File(filePath), "fdFile", null);
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
    private static E2EContext loadEntityNodes(String attachId) {
        JSONObject json = new JSONObject();
        json.put("attachId", attachId);
        MkResponse<E2EContext> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/offline/target/loadEntityNodes", json, E2EContext.class);
        log.info("loadEntityNodes: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    /**
     * 离线导出关联数据
     */
    private static E2EContext loadReferTree(List<ReferTreeNode> entityNodes) {
        List<ReferTreeNode> referOperList = new ArrayList<>();
        for (ReferTreeNode node : entityNodes) {
            ReferTreeNode operNode = new ReferTreeNode();
            operNode.setReferType(node.getReferType());
            operNode.setFdId(node.getFdId());
            operNode.setOperation("NEW");
            referOperList.add(operNode);
        }
        JSONObject json = new JSONObject();
        json.put("nodeOperList", referOperList);
        MkResponse<E2EContext> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/offline/target/loadReferTree", json, E2EContext.class);
        log.info("loadReferTree: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    /**
     * 离线导出组织架构
     */
    private static E2EContext loadOrgNodes(List<ReferGroup> referGroupList) {
        List<ReferTreeNode> nodeOperList = new ArrayList<>();
        for (ReferGroup group : referGroupList) {
            for (ReferTreeNode node : group.getReferNodes()) {
                ReferTreeNode operNode = new ReferTreeNode();
                operNode.setReferType(node.getReferType());
                operNode.setFdId(node.getFdId());
                operNode.setOperation("NEW");
                nodeOperList.add(operNode);
            }
        }
        JSONObject json = new JSONObject();
        json.put("nodeOperList", nodeOperList);
        MkResponse<E2EContext> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-admin/e2e/offline/target/loadOrgNodes", json, E2EContext.class);
        log.info("loadOrgNodes: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
        return mkResponse.getData();
    }

    /**
     * 离线导入数据
     */
    private static String offLineImportData(String attachId, List<ReferTreeNode> orgNodes) {
        JSONObject replaceOrg = new JSONObject();
        replaceOrg.put("fdId", "11111");
        replaceOrg.put("fdName", "张三");
        List<ReferTreeNode> nodeOperList = new ArrayList<>();
        for (ReferTreeNode node : orgNodes) {
            ReferTreeNode operNode = new ReferTreeNode();
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
