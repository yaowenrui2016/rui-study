package indi.rui.study.unittest.calldata;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.OpenDingSetting;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class CallDataGetOpenDingSetting {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "jm", "1");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mkdev01.ywork.me", "yuxd", "1");

    public static void main(String[] args) {
        // 拉取来源系统和模块
        MkResponse<OpenDingSetting> mkResponse = mkDataRequestHelper.callData(
                "/data/tic-opending/ticOpenDingSetting/get", null, OpenDingSetting.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get open ding setting error! errMsg=" + mkResponse.getMsg());
        }
        log.info("open ding setting: {}", JSONObject.toJSONString(mkResponse.getData(), SerializerFeature.PrettyFormat));
    }
}
