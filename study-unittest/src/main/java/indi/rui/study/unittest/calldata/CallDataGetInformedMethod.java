package indi.rui.study.unittest.calldata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.InformedMethodDTO;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import indi.rui.study.unittest.util.FileUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class CallDataGetInformedMethod {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkpro.ywork.me", "liq", "1");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev02.ywork.me", "yaowr", "1");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkoppo.ywork.me", "liq", "1");

    public static void main(String[] args) {
//        getInformedMethodRPC();
        getInformedMethodForInnerRPC();
        open();
        getInformedMethodForInnerRPC();
    }

    private static void getInformedMethodRPC() {
        // 拉取来源系统和模块
        MkResponse<InformedMethodDTO> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/config/getInformedMethod", null, InformedMethodDTO.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get informed method error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Get informed method: {}", JSON.toJSONString(mkResponse.getData(),
                SerializerFeature.PrettyFormat));
    }

    private static void getInformedMethodForInnerRPC() {
        // 拉取来源系统和模块
        MkResponse<InformedMethodDTO> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/config/getInformedMethodForInner", null, InformedMethodDTO.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get informed method error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Get informed method for inner: {}", JSON.toJSONString(mkResponse.getData(),
                SerializerFeature.PrettyFormat));
    }

    private static void open() {
        JSONObject json = FileUtils.loadJSON("CallDataGetInformedMethod/open.json");
        // 拉取来源系统和模块
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/config/open", json);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Open informed method error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Open informed method: {}", mkResponse.isSuccess());
    }
}
