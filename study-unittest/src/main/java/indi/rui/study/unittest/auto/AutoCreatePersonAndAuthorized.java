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

    private static final String GROUP_NAME = "ALL_ROLES";

    private static final String LOGIN_NAME = "yaowr";

    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
            "http://127.0.0.1:8040", "secadmin", "Password_1");

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://mkdev01.ywork.me", "secadmin", "Password_1");

    public static void main(String[] args) {
        MkRightGroupVO group;
        if ((group = getGroup(GROUP_NAME)) == null) {
            group = initGroup();
            group.setFdName(GROUP_NAME);
            group.setFdCategory(null);
        }
        // 搜索用户
        List<IdNameProperty> orgs = searchOrg(LOGIN_NAME);
        if (orgs == null) {
            // 创建用户
//                orgs = addPerson(LOGIN_NAME);
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
        // 新增角色并授权给用户
        addGroup(group);
        log.info("Authorize rights success: {}", JSONObject.toJSONString(group, SerializerFeature.PrettyFormat));
    }

    private static List<IdNameProperty> searchOrg(String keyword) {
        JSONObject json = new JSONObject();
        json.put("key", keyword);
        json.put("orgType", 31);
        MkResponse<List<IdNameProperty>> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-org/sysOrgAddress/searchOrg", json, IdNameProperty.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Init group failed");
        }
        return mkResponse.getData();
    }

    private static MkRightGroupVO getGroup(String groupName) {
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
        MkResponse<?> mkResponse = mkDataRequestHelper.callData("/data/sys-right/sysRightGroup/add", json);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Add group failed");
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
