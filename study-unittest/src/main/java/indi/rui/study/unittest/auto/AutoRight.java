package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-08-10
 */
@Slf4j
public class AutoRight {

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://127.0.0.1:8040",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "65456258397a644b43322b576c673932574d646b704c547747552f6964456d6144466c73454163307768453d");
//        private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkt2.ywork.me", "yuxd", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://mkt2.ywork.me",
            "73456775666d4c416f73776139584a4131432f6847413d3d");


    /**
     * Main
     *
     * @param args
     */
    public static void main(String[] args) {
//        // 刷新权限
//        refresh2();

        // 获取权限
        getRolesByOrgs();
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
