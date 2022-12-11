package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: yaowr
 * @create: 2022-08-10
 */
@Slf4j
public class AutoNotifySourceApp {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "65456258397a644b43322b576c673932574d646b704c547747552f6964456d6144466c73454163307768453d");


    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
        // 拉取来源系统模块
        pull();
//        // 新增来源系统
//        addApp();
        // 查询系统
        List<JSONObject> appList = getAppList();
        if (!CollectionUtils.isEmpty(appList)) {
//            // 新增来源模块
//            addModule(appList.get(0));
//            // 删除来源系统
//            deleteAll(appList);
        }
    }

    private static void pull() {
        JSONObject body = null;
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifySourceApp/pull/true", body, JSONObject.class);
        log.info("pull: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }

    /**
     * 新增来源系统
     */
    private static void addApp() {
        JSONObject body = FileUtils.loadJSON("AutoNotifySourceApp/addApp.json");
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifySourceApp/add", body, JSONObject.class);
        log.info("addApp: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }

    /**
     * 删除来源系统
     */
    private static void deleteAll(List<JSONObject> sourceApps) {
        JSONObject body = new JSONObject();
        body.put("fdIds", sourceApps.stream().map(json -> json.getString("fdId")).collect(Collectors.toList()));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifySourceApp/deleteAll", body, JSONObject.class);
        log.info("deleteAll: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }

    /**
     * 查询系统
     */
    private static List<JSONObject> getAppList() {
        JSONObject body = new JSONObject();
        JSONObject conditions = new JSONObject();
        conditions.put("fdSourceId", "Admin Pull");
        body.put("conditions", conditions);
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-notify/sysNotifySourceApp/list", body, JSONObject.class);
        log.info("getAppList: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData().getContent();
    }

    /**
     * 新增来源模块
     */
    private static void addModule(JSONObject app) {
        JSONObject body = FileUtils.loadJSON("AutoNotifySourceApp/addModule.json");
        body.put("fdSourceApps", Collections.singletonList(app.getString("fdId")));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifySourceModule/addV2", body, JSONObject.class);
        log.info("addModule: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }
}
