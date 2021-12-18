package indi.rui.study.unittest.calldata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-11-22
 */
@Slf4j
public class CallDataGetKKSetting {

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://127.0.0.1:8040", "yaowr", "1");

    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
            "http://mksmoke.ywork.me", "yuxd", "1");

    public static void main(String[] args) throws Exception {
        // 获取KK设置
        MkResponse<?> mkResponse = mkDataRequestHelper.callData("/data/tic-kk/kkSetting/get", null);
        log.info("Get kk setting: {}", JSON.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
    }
}
