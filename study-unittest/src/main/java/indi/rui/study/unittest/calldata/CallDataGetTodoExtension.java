package indi.rui.study.unittest.calldata;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-11-22
 */
@Slf4j
public class CallDataGetTodoExtension {

    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
            "http://127.0.0.1:8040", "yaowr", "1");

//    private static MkDataRequestHelper mkDataRequestHelper = new MkDataRequestHelper(
//            "http://mksmoke.ywork.me", "yuxd", "1");

    public static void main(String[] args) throws Exception {
        // 获取待办同步扩展
        MkResponse<List<?>> mkResponse = mkDataRequestHelper.callDataForList(
                "/data/sys-notify/sysNotifyTodoExt/getProvider", null);
        log.info("Get kk setting: {}", JSON.toJSONString(mkResponse, SerializerFeature.PrettyFormat));
    }
}
