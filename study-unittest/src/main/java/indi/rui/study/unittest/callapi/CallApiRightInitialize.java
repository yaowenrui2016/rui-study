package indi.rui.study.unittest.callapi;

import indi.rui.study.unittest.support.MkApiRequestHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class CallApiRightInitialize {

    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040", "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev01.ywork.me", "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) throws Exception {
        // 初始化权限
        Boolean rtnResult = Boolean.valueOf(mkApiRequestHelper.callApi("/api/sys-right/initialize", null));
        log.info("Initialize right [{}]", rtnResult ? "success" : "failure");
    }
}
