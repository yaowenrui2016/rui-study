package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-10-17
 */
@Slf4j
public class AutoApplicationDynamicPanel {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");
    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
            "http://127.0.0.1:8040",
            "73456775666d4c416f73776139584a4131432f6847413d3d");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev01.ywork.me", "jm", "1");
//    private static MkApiRequestHelper mkApiRequestHelper = new MkApiRequestHelper(
//            "http://mkdev01.ywork.me",
//            "73456775666d4c416f73776139584a4131432f6847413d3d");

    public static void main(String[] args) {
        // 查询面板扩展
        List<JSONObject> panels = panelProviders();
        if (!CollectionUtils.isEmpty(panels)) {
            // 获取应用面板
            panels(panels.get(0));
        }


    }


    /**
     * 查询面板扩展
     */
    private static List<JSONObject> panelProviders() {
        JSONObject body = new JSONObject();
        MkResponse<List<JSONObject>> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-application/dynamicPanel/panelProviders", body, JSONObject.class);
        log.info("panelProviders: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData();
    }


    /**
     * 获取应用面板
     */
    private static void panels(JSONObject panel) {
        JSONObject body = panel;
        MkResponse<List<JSONObject>> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-application/dynamicPanel/panels", body, JSONObject.class);
        log.info("panels: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }
}
