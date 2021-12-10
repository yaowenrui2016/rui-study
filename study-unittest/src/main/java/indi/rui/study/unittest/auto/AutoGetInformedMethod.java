package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.InformedMethodDTO;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.NotifyTypeVO;
import indi.rui.study.unittest.support.MkDataRequestHelper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class AutoGetInformedMethod {

    private static MkDataRequestHelper mkDataRequestHelper
            = new MkDataRequestHelper("http://127.0.0.1:8040", "yaowr", "1");

//    private static MkDataRequestHelper mkDataRequestHelper
//            = new MkDataRequestHelper("http://mksmoke.ywork.me", "yuxd", "1");

    public static void main(String[] args) {
        // 用例1.更改启动状态为false
        updateInformedMethodEnable(false);
        getInformedMethod(1);

        // 用例2.更改启动状态为true
        updateInformedMethodEnable(true);
        getInformedMethod(2);
    }

    private static void updateInformedMethodEnable(boolean enable) {
        InformedMethodDTO informedMethodDTO = getInformedMethodRPC();
        List<NotifyTypeVO> notifyTypeVOs = informedMethodDTO.getNotifyTypes();
        for (NotifyTypeVO typeVO : notifyTypeVOs) {
            typeVO.setEnabled(enable);
        }
        saveInformedMethodRPC(notifyTypeVOs);
    }

    private static void getInformedMethod(int useCaseNo) {
        // 获取通知方式
        List<NotifyTypeVO> notifyTypes = getInformedMethodRPC().getNotifyTypes();
        log.info("use case {} => {}", useCaseNo,JSON.toJSONString(notifyTypes, SerializerFeature.PrettyFormat));
    }

    private static InformedMethodDTO getInformedMethodRPC() {
        // 拉取来源系统和模块
        MkResponse<InformedMethodDTO> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/config/getInformedMethod", null, InformedMethodDTO.class);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get informed method error! errMsg=" + mkResponse.getMsg());
        }
        return mkResponse.getData();
    }

    private static void saveInformedMethodRPC(List<NotifyTypeVO> notifyTypes) {
        JSONObject body = new JSONObject();
        body.put("notifyTypes", notifyTypes);
        MkResponse<?> mkResponse = mkDataRequestHelper.callData(
                "/data/sys-notify/config/saveInformedMethod", body);
        if (!mkResponse.isSuccess()) {
            throw new RuntimeException("Get informed method error! errMsg=" + mkResponse.getMsg());
        }
    }
}
