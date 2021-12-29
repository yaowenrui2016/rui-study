package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.UserInfo;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-12-24
 */
@Slf4j
public class AutoGetUserRoles {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://127.0.0.1:8040", "secadmin", "Password_1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://127.0.0.1:8040",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev02.ywork.me", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev02.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) {
        UserInfo userInfo = mkDataRequestHelper.getUserInfo();
        getRoles(userInfo.getUserId());
    }


    private static void getRoles(String userId) {
        // 查询我的待办
        JSONObject json = new JSONObject();
        json.put("fdIds", Collections.singletonList(userId));
        MkResponse<List<String>> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-right/sysRightRole/getRolesByOrgs", json, String.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get my todo error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Get user roles: {}", JSONObject.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
    }
}
