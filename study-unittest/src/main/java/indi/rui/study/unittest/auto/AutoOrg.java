package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.IdNameProperty;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.support.UserHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: yaowr
 * @create: 2022-11-02
 */
@Slf4j
public class AutoOrg {


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
        // 查询人员
        List<JSONObject> persons = list();
        if (!CollectionUtils.isEmpty(persons)) {
//            // 恢复启用
//            restoreEnabled(persons.get(0));
//            // 置为无效
//            invalidated(persons.get(0));
            // 根据人员ID获取人员与账号信息
            getPersonAccount(persons.get(0));
        }
//        // 新增岗位
//        addPost();
        // 启用所有
        enableAll(persons);
        // 修改排序号
//        updateOrders();
    }


    /**
     * 查询人员
     */
    private static List<JSONObject> list() {
        JSONObject body = new JSONObject();
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-org/sysOrgPerson/list", body, JSONObject.class);
        log.info("list: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData().getContent();
    }


    /**
     * 恢复启用
     */
    private static void restoreEnabled(JSONObject person) {
        JSONObject body = new JSONObject();
        body.put("fdId", person.getString("fdId"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgPerson/restoreEnabled", body, Boolean.class);
        log.info("restoreEnabled: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 置为无效
     */
    private static void invalidated(JSONObject person) {
        JSONObject body = new JSONObject();
        body.put("fdId", person.getString("fdId"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgPerson/invalidated", body, Boolean.class);
        log.info("invalidated: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 根据人员ID获取人员与账号信息
     */
    private static void getPersonAccount(JSONObject person) {
        JSONObject body = new JSONObject();
        body.put("fdId", person.getString("fdId"));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgPerson/getPersonAccount", body, JSONObject.class);
        log.info("getPersonAccount: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 新增岗位
     */
    private static void addPost() {
        JSONObject body = FileUtils.loadJSON("AutoOrg/addPost.json");
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgPost/add", body, Boolean.class);
        log.info("addPost: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 启用所有
     */
    private static void enableAll(List<JSONObject> persons) {
        if (CollectionUtils.isEmpty(persons)) {
            return;
        }
        JSONObject body = new JSONObject();
        body.put("fdIds", persons.stream().map(
                json -> json.getString("fdId"))
                .collect(Collectors.toList()));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgPerson/enableAll", body, Boolean.class);
        log.info("enableAll: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }

    private static void updateOrders() {
        JSONArray body = new JSONArray();
        List<IdNameProperty> idNameList = UserHelper.getUsers("yaowr", "chenp");
        for (int i = 0; i < idNameList.size(); i++) {
            JSONObject person = new JSONObject();
            person.put("fdId", idNameList.get(i).getFdId());
            person.put("fdOrder", i);
            body.add(person);
        }
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgPerson/updateOrders", body, Boolean.class);
        log.info("updateOrders: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }
}
