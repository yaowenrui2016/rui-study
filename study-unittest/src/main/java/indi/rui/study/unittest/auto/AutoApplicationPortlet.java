package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.support.UserHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: yaowr
 * @create: 2022-10-17
 */
@Slf4j
public class AutoApplicationPortlet {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "weilq", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev01.ywork.me", "jm", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev01.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) {
        // 查询用户可使用的应用列表
        userPanels();
//        // 查询用户应用列表
//        userApplications();
    }


    /**
     * 查询用户应用列表
     */
    private static void userApplications() {
        JSONObject body = new JSONObject();
        body.put("pageId", "1");
        body.put("componentId", "1");
        MkResponse<List<JSONObject>> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-application/portlet/userApplications", body, JSONObject.class);
        log.info("userApplications: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 查询用户可使用的应用列表
     */
    private static void userPanels() {
        JSONObject body = new JSONObject();
        body.put("pageId", "1");
        body.put("componentId", "1");
        MkResponse<List<JSONObject>> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-application/portlet/userPanels", body, JSONObject.class);
        log.info("userApplications: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }
}
