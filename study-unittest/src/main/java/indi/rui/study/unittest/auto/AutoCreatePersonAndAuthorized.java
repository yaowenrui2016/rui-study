package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.*;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author: yaowr
 * @create: 2021-12-15
 */
@Slf4j
public class AutoCreatePersonAndAuthorized {

    //    private static final String GROUP_NAME = "ALL_ROLES";
    private static final String GROUP_NAME = "yao_test_1";

    private static final String[] USER_LOGIN_NAMES = new String[]{
            "yaowr", "姚文锐",
            "zhangyl", "张越良",
            "cuipx", "崔璞璇",
            "chenp", "陈鹏",
            "lizj", "李振佳",
            "weilq", "韦莉琦",
            "yuxd", "余小冬",
            "laow", "老王",};

//    private static final String[] USER_LOGIN_NAMES = new String[]{
//            "yaowr", "姚文锐",};

    /* ------------------ 各个环境 --------------- */

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://127.0.0.1:8040", "secadmin", "Password_1");

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://mktest.ywork.me", "secadmin", "Password_1");

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://mksmokemini.ywork.me", "secadmin", "Password_1");

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "https://mkdemo.landray.com.cn", "secadmin", "Password_1");

    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
            "http://mksmoke.ywork.me", "secadmin", "Password_1");

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://mkdev01.ywork.me", "secadmin", "Password_1");

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://mkdev02.ywork.me", "secadmin", "Password_1");

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://mkoppo.ywork.me", "secadmin", "Password_1");

    /* ---------------------- */

    public static void main(String[] args) {
        MkRightGroupVO group;
        boolean exists = true;
        // 查找角色
        if ((group = findGroup(GROUP_NAME)) == null) {
            group = initGroup();
            group.setFdName(GROUP_NAME);
            group.setFdCategory(null);
            exists = false;
        }
        // 获取用户
        List<IdNameProperty> orgs = new ArrayList<>();
        for (int i = 0; i < USER_LOGIN_NAMES.length; i += 2) {
            String loginName = USER_LOGIN_NAMES[i];
            String name = USER_LOGIN_NAMES[i + 1];
            // 查找用户
            IdNameProperty person = findPerson(loginName);
            if (person == null) {
                // 创建用户
                person = addPerson(loginName, name);
            }
            orgs.add(person);
        }
        group.setFdSysOrgElements(orgs);
        // 获取角色
        List<MkRightRolePanel> rolesPanel = getAllRoles();
        List<IdNameProperty> roles = new ArrayList<>();
        for (MkRightRolePanel rolePanel : rolesPanel) {
            List<MkRightRolePanel.Option> options = rolePanel.getOptions();
            for (MkRightRolePanel.Option option : options) {
                IdNameProperty role = new IdNameProperty();
                role.setFdId(option.getFdId());
                role.setFdName(option.getFdName());
                roles.add(role);
            }
        }
        group.setFdSysRightRoles(roles);
        if (exists) {
            // 更新角色并授权给用户
            updateGroup(group);
            log.info("Update right group success: {}",
                    JSONObject.toJSONString(group, SerializerFeature.PrettyFormat)
            );
        } else {
            // 新增角色并授权给用户
            addGroup(group);
            log.info("Add right group success: {}",
                    JSONObject.toJSONString(group, SerializerFeature.PrettyFormat)
            );
        }
    }

    private static IdNameProperty addPerson(String loginName, String name) {
        JSONObject json = initPerson();
        json.put("fdGender", "M");
        json.put("fdInner", true);
        json.put("fdIsAvailable", true);
        json.put("fdIsBusiness", true);
        json.put("fdIsLogin", true);
        json.put("fdLoginName", loginName);
        json.put("fdName", name);
        json.put("fdEmail", loginName + "@landray.com.cn");
        json.put("fdOrder", 0);
        json.put("fdPassword", 1);
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgPerson/addPersonAccount", json, JSONObject.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("add person account failed");
        }
        return IdNameProperty.of((String) json.get("fdId"), (String) json.get("fdName"));
    }

    private static JSONObject initPerson() {
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgPerson/init", null, JSONObject.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Init person failed");
        }
        return mkResponse.getData();
    }

    private static List<IdNameProperty> searchOrg(String key) {
        JSONObject json = new JSONObject();
        json.put("key", key);
        json.put("orgType", 31);
        MkResponse<List<IdNameProperty>> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-org/sysOrgAddress/searchOrg", json, IdNameProperty.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Search person failed");
        }
        List<IdNameProperty> rtnList = mkResponse.getData();
        if (rtnList == null) {
            rtnList = Collections.emptyList();
        }
        return rtnList;
    }

    private static IdNameProperty findPerson(String loginName) {
        JSONObject json = new JSONObject();
        Map<String, Object> conditions = (Map<String, Object>) json.computeIfAbsent(
                "conditions", (k) -> new HashMap<>());
        conditions.put("fdAccount.fdLoginName", Collections.singletonMap("$eq", loginName));
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-org/sysOrgPerson/list", json, JSONObject.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Find person failed");
        }
        IdNameProperty person = null;
        List<JSONObject> resultPersons = mkResponse.getData().getContent();
        if (resultPersons != null && !resultPersons.isEmpty()) {
            JSONObject resultPerson = resultPersons.get(0);
            person = IdNameProperty.of((String) resultPerson.get("fdId"), (String) resultPerson.get("fdName"));
        }
        return person;
    }

    private static MkRightGroupVO findGroup(String groupName) {
        JSONObject json = new JSONObject();
        Map<String, Object> conditions = (Map<String, Object>) json.computeIfAbsent(
                "conditions", (k) -> new HashMap<>());
        conditions.put("fdName", Collections.singletonMap("$contains", groupName));
        MkResponse<QueryResult<MkRightGroupVO>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-right/sysRightGroup/list", json, MkRightGroupVO.class);
        MkRightGroupVO rtnGroup = null;
        List<MkRightGroupVO> content;
        if (mkResponse.isSuccess() && (content = mkResponse.getData().getContent()) != null && !content.isEmpty()) {
            rtnGroup = content.get(0);
        }
        return rtnGroup;
    }

    private static MkRightGroupVO initGroup() {
        JSONObject json = new JSONObject();
        MkResponse<MkRightGroupVO> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-right/sysRightGroup/init", json, MkRightGroupVO.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Init group failed");
        }
        return mkResponse.getData();
    }

    private static void addGroup(MkRightGroupVO group) {
        JSONObject json = (JSONObject) JSONObject.toJSON(group);
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-right/sysRightGroup/add", json);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Add group failed");
        }
    }

    private static void updateGroup(MkRightGroupVO group) {
        JSONObject json = (JSONObject) JSONObject.toJSON(group);
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-right/sysRightGroup/update", json);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Update group failed");
        }
    }

    private static List<MkRightRolePanel> getAllRoles() {
        MkResponse<List<MkRightRolePanel>> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-right/sysRightRole/getAll", null, MkRightRolePanel.class);
        List<MkRightRolePanel> rtnList = null;
        if (mkResponse.isSuccess()) {
            rtnList = mkResponse.getData();
        }
        return rtnList;
    }
}
