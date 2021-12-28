package indi.rui.study.unittest.callapi;

import indi.rui.study.unittest.support.MkApiRequestHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-12-20
 */
@Slf4j
public class CallApiDiscoveryCenter {

//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://127.0.0.1:8010", "73456775666d4c416f73776139584a4131432f6847413d3d");

    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://10.253.13.14:8010", "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) throws Exception {
        // 初始化权限
        String result = mkApiRequestHelper.callApi(MkApiRequestHelper.HttpMethod.GET, "/discovery", null);
        log.info("Get all applications: {}", result);
    }
}
