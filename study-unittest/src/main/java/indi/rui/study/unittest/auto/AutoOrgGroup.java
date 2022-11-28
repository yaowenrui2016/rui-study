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

/**
 * @author: yaowr
 * @create: 2022-11-02
 */
@Slf4j
public class AutoOrgGroup {


    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "secadmin", "Password_1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev01.ywork.me", "jm", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev01.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) {
//        // 新增群组
//        add();
        // 查询群组列表
        List<JSONObject> groupList = list();
        if (!CollectionUtils.isEmpty(groupList)) {
            // 编辑群组详情
            edit(groupList.get(0));
            // 查询群组详情
            get(groupList.get(0));
            // 批量删除群组
            deleteAll(groupList.get(0));
        }
    }


    /**
     * 新增群组
     */
    private static void add() {
        JSONObject body = FileUtils.loadJSON("AutoOrgGroup/group.json");
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgGroup/add", body, JSONObject.class);
        log.info("add: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 新增群组
     */
    private static void edit(JSONObject person) {
        JSONObject body = FileUtils.loadJSON("AutoOrgGroup/edit.json");
        body.put("fdId", "1gi4tpn3bw3qwqdwp13nmf2gt9qkl1qaa8w0");
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgGroup/update", body, JSONObject.class);
        log.info("add: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 查询列表
     */
    private static List<JSONObject> list() {
        JSONObject body = new JSONObject();
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-org/sysOrgGroup/list", body, JSONObject.class);
        log.info("list: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData().getContent();
    }


    /**
     * 查询群组详情
     */
    private static void get(JSONObject person) {
        JSONObject body = new JSONObject();
        body.put("fdId", person.getString("fdId"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgGroup/get", body, JSONObject.class);
        log.info("get: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 批量删除群组
     */
    private static void deleteAll(JSONObject person) {
        JSONObject body = new JSONObject();
        body.put("fdIds", Collections.singletonList(person.getString("fdId")));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgGroup/deleteAll", body, JSONObject.class);
        log.info("deleteAll: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }
}
