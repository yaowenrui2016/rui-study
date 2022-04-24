package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class AutoNotifyFindByPerson {

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

    //    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yuxd", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mksmoke.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkoppo.ywork.me", "yaowr", "1", "oppo");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkoppo.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

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

    private static final int MAX_TIMEOUT_MS = 10000;

    public static void main(String[] args) {
        findByPerson();
    }

    private static void findByPerson() {
        JSONObject json = FileUtils.loadJSON("AutoNotifyFindByPerson/find_by_person.json");
        QueryResult result = mkApiRequestHelper.callApi(
                "/api/sys-notify/baseSysNotify/findByPerson", json, QueryResult.class);
        log.warn("request={}, response={}",
                JSONObject.toJSONString(json, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(result, SerializerFeature.PrettyFormat));
    }
}
