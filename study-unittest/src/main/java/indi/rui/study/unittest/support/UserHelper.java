package indi.rui.study.unittest.support;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.dto.IdNameProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-11-12
 */
@Slf4j
public class UserHelper {


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


    /**
     * 查询人员
     */
    public static List<IdNameProperty> getUsers(String... loginNames) {
        List<IdNameProperty> persons = new ArrayList<>();
        if (loginNames != null && loginNames.length > 0) {
            JSONObject body = new JSONObject();
            JSONObject conditions = new JSONObject();
            JSONObject operator = new JSONObject();
            operator.put("$in", Arrays.asList(loginNames));
            conditions.put("fdLoginName", operator);
            body.put("conditions", conditions);
            body.put("pageSize", 1000);
            body.put("count", false);
            JSONObject queryResult = mkApiRequestHelper.callApi(
                    "/api/sys-org/sysOrgElementSummary/findAll", body, JSONObject.class);
            JSONArray content = queryResult.getJSONArray("content");
            for (int i = 0; i < content.size(); i++) {
                JSONObject person = content.getJSONObject(i);
                persons.add(IdNameProperty.of(person.getString("fdId"), person.getString("fdName")));
            }
        }
        return persons;
    }
}
