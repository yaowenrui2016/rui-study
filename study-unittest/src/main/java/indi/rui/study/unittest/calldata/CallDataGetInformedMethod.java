package indi.rui.study.unittest.calldata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.InformedMethodDTO;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class CallDataGetInformedMethod {

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yaowr", "1");

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://mkoppo.ywork.me", "liq", "1");

    public static void main(String[] args) {
        getInformedMethodRPC();
    }

    private static void getInformedMethodRPC() {
        // 拉取来源系统和模块
        MkResponse<InformedMethodDTO> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/config/getInformedMethod", null, InformedMethodDTO.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get informed method error! errMsg=" + mkResponse.getMsg());
        }
        log.info("Get informed method: {}", JSON.toJSONString(mkResponse.getData().getNotifyTypes(),
                SerializerFeature.PrettyFormat));
    }
}
