package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.dto.IdNameProperty;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.MkRightGroupVO;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-12-15
 */
@Slf4j
public class AutoCreatePerson {

    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
            "http://127.0.0.1:8040", "secadmin", "Password_1");

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://mkdev01.ywork.me", "secadmin", "Password_1");

    public static void main(String[] args) {
        // 获取角色
        MkRightGroupVO group;
        if ((group = getGroup("超管Y")) == null) {

        }
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

    private static List<IdNameProperty> getRoles() {
        MkResponse<List<IdNameProperty>> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-right/sysRightRole/getAll", null, IdNameProperty.class);
        List<IdNameProperty> rtnList = null;
        if (mkResponse.isSuccess()) {
            rtnList = mkResponse.getData();
        }
        return rtnList;
    }
}
