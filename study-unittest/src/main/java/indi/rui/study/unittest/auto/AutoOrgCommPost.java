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
public class AutoOrgCommPost {


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
        // 新增公共岗
        add();
        // 查询公共岗列表
        List<JSONObject> staffingLevels = list();
        if (!CollectionUtils.isEmpty(staffingLevels)) {
            // 查询公共岗详情
            get(staffingLevels.get(0));
            // 删除公共岗
            delete(staffingLevels.get(0));
//            // 批量删除公共岗
//            deleteAll(staffingLevels.get(0));
        }
    }


    /**
     * 新增公共岗
     */
    private static void add() {
        JSONObject body = FileUtils.loadJSON("AutoOrgCommPost/add.json");
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgCommPost/add", body, JSONObject.class);
        log.info("get: request={}, response={}",
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
                "/data/sys-org/sysOrgCommPost/list", body, JSONObject.class);
        log.info("list: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData().getContent();
    }


    /**
     * 查询公共岗详情
     */
    private static void get(JSONObject person) {
        JSONObject body = new JSONObject();
        body.put("fdId", person.getString("fdId"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgCommPost/get", body, JSONObject.class);
        log.info("get: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 删除公共岗
     */
    private static void delete(JSONObject person) {
        JSONObject body = new JSONObject();
        body.put("fdId", person.getString("fdId"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgCommPost/delete", body, JSONObject.class);
        log.info("delete: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 批量删除公共岗
     */
    private static void deleteAll(JSONObject person) {
        JSONObject body = new JSONObject();
        body.put("fdIds", Collections.singletonList(person.getString("fdId")));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgCommPost/deleteAll", body, JSONObject.class);
        log.info("deleteAll: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }
}
