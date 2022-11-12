package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONArray;
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
 * @create: 2022-08-10
 */
@Slf4j
public class AutoRight {

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

        // 角色列表
        List<JSONObject> groupList = groupList();

        // 获取角色
        if (!CollectionUtils.isEmpty(groupList)) {
            JSONObject group = getGroup(groupList.get(0));

            // 编辑角色
            editGroup(group);
        }

//        // 刷新权限
//        refresh2();

        // 获取权限
//        getRolesByOrgs();
    }


    /**
     * 角色列表
     */
    private static List<JSONObject> groupList() {
        JSONObject body = FileUtils.loadJSON("AutoRight/groupList.json");
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-right/sysRightGroup/list", body, JSONObject.class);
        log.info("groupList: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData().getContent();
    }


    /**
     * 获取角色
     */
    private static JSONObject getGroup(JSONObject group) {
        JSONObject body = new JSONObject();
        if (group != null) {
            body.put("fdId", group.getString("fdId"));
        }
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-right/sysRightGroup/get", body, JSONObject.class);
        log.info("getGroup: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData();
    }


    /**
     * 编辑角色
     */
    private static JSONObject editGroup(JSONObject group) {
        JSONObject body = group;
        if (group != null) {
            JSONObject personWangdh = FileUtils.loadJSON("AutoRight/person_wangdh.json");
            JSONArray persons = group.getJSONArray("fdSysOrgElements");
            boolean removed = persons.removeIf(person -> ((JSONObject) person).getString("fdId").equals(personWangdh.getString("fdId")));
            if (!removed) {
                persons.add(personWangdh);
            }
        }
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-right/sysRightGroup/update", body, JSONObject.class);
        log.info("editGroup: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData();
    }


    /**
     * 保存多语言编辑开关
     */
    private static void refresh2() {
        boolean result = mkApiRequestHelper.callApi(
                "/api/sys-right/sysRightRole/refresh2?needAddSysadmin=true", null, Boolean.class);
        log.info("refresh2: response={}", result);
    }


    /**
     * 保存多语言编辑开关
     */
    private static void getRolesByOrgs() {
        JSONObject body = new JSONObject();
        body.put("fdIds", Collections.singletonList("1gfqe90ujwiw34nw1t2pcplos371f2qka3w2"));
        log.info("getRolesByOrgs: request={}", JSONObject.toJSONString(body, SerializerFeature.PrettyFormat));
        List<String> rightList = mkApiRequestHelper.callApiForList(
                "/api/sys-right/sysRightRole/getRolesByOrgs", body, String.class);
        log.info("getRolesByOrgs: response={}", JSONObject.toJSONString(rightList, SerializerFeature.PrettyFormat));
    }
}
