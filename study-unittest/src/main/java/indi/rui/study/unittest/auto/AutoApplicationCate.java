package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.QueryResult;
import indi.rui.study.unittest.support.MkApiRequestHelper;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: yaowr
 * @create: 2022-10-17
 */
@Slf4j
public class AutoApplicationCate {

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

        // 检查唯一字段值是否存在
        checkUniqueField();

        // 保存
        saveCategory();

        // 获取分类
        List<JSONObject> cates = categoryList();

        // 删除
        delete(cates);
    }


    /**
     * 检查唯一字段值是否存在
     */
    private static void checkUniqueField() {
        JSONObject body = new JSONObject();
        body.put("fdName", "分类1");
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-application/cate/checkUniqueField", body, JSONObject.class);
        log.info("category list: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 保存分类
     */
    private static void saveCategory() {
        JSONObject body = FileUtils.loadJSON("AutoApplicationCate/cate.json");
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-application/cate/save", body, JSONObject.class);
        log.info("category list: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
    }


    /**
     * 获取分类
     */
    private static List<JSONObject> categoryList() {
        JSONObject body = new JSONObject();
        MkResponse<QueryResult<JSONObject>> mkResponse = mkDataRequestHelper.callDataForMkQueryResult(
                "/data/sys-application/cate/list", body, JSONObject.class);
        log.info("category list: request={}, response={}",
                JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
        );
        return mkResponse.getData().getContent();
    }


    /**
     * 获取分类
     */
    private static void delete(List<JSONObject> cates) {
        if (!CollectionUtils.isEmpty(cates)) {
            List<String> ids = cates.stream().map(jsonObject -> jsonObject.getString("fdId"))
                    .collect(Collectors.toList());
            JSONObject body = new JSONObject();
            body.put("ids", ids);
            MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                    "/data/sys-application/cate/deleteAll", body, JSONObject.class);
            log.info("category list: request={}, response={}",
                    JSONObject.toJSONString(body, SerializerFeature.PrettyFormat),
                    JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat)
            );
        }
    }
}
