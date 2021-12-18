package indi.rui.study.unittest.calldata;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2021-12-15
 */
@Slf4j
public class CallDataDonePersonal {

    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
            "http://mksmoke.ywork.me", "yuxd001", "1");

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://127.0.0.1:8040", "yaowr", "1");

    public static void main(String[] args) {
        JSONObject json = new JSONObject();
        json.put("fdId", "123");
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/sysNotifyTodo/donePersonal?todoType=TODO", json);
        log.info("Done personal: {}", JSONObject.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }
}
