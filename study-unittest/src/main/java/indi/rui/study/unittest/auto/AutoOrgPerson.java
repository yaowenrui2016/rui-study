package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.support.UserHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-11-02
 */
@Slf4j
public class AutoOrgPerson {


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
//        // 管理员重置密码
//        resetPassword();
        // 调换上级
        updateParent();
    }


    /**
     * 管理员重置密码
     */
    private static void resetPassword() {
        JSONObject body = new JSONObject();
        body.put("accountId", "1ge6mn1fowlw492w7tkphd1e9phbr135o4w0");
        body.put("newPassword", "1");
        body.put("reNewPassword", "1");
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgPersonUpdatePwd/resetPassword", body, Boolean.class);
        log.info("resetPassword: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 调换上级
     */
    private static void updateParent() {
        JSONObject body = new JSONObject();
        body.put("fdParentId", UserHelper.getUserId("yaowr"));
        String[] loginNames = new String[9];
        for (int i = 1; i < loginNames.length; i++) {
            loginNames[i] = "thousand" + i;
        }
        body.put("fdIds", UserHelper.getUserIds(loginNames));
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-org/sysOrgPerson/updateParent", body, Boolean.class);
        log.info("updateParent: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }
}
