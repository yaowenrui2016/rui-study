package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author: yaowr
 * @create: 2022-04-07
 */
@Slf4j
public class AutoMechTptEnvToEnv {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mktest.ywork.me", "jj01", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mktest.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmokemini.ywork.me", "yaowr", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmokemini.ywork.me",
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
        offLineExport();
//        offLineLoadEntity();
//        offLineImportData();
    }


    /**
     * 离线导出
     */
    private static void offLineExport() {
        JSONObject json = FileUtils.loadJSON("AutoMechTptEnvToEnv/exportRequest.json");
        String downloadPath = System.getProperty("user.dir") + File.separator + "downloadTemp";
        mkDataRequestHelper.callDataDownload(
                "/data/sys-mech-transport/envToEnv/offline/export", json, downloadPath);
    }


    /**
     * 离线模式-获取主文档
     */
    private static void offLineLoadEntity() {
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-mech-transport/envToEnv/offline/loadEntity", null, JSONObject.class);
    }


    /**
     * 离线模式-导入数据
     */
    private static void offLineImportData() {
        MkResponse<JSONObject> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-mech-transport/envToEnv/offline/importData", null, JSONObject.class);
    }

}
