package indi.rui.study.unittest.auto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import indi.rui.study.unittest.dto.MkLoginResult;
import indi.rui.study.unittest.support.MkLoginHelper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: yaowr
 * @create: 2022-10-17
 */
@Slf4j
public class AutoMkLogin {

    private static final String RSA_PUB_KEY = "30819f300d06092a864886f70d010101050003818d0030818902818100a55ea926840ebb0099f4f671d4f461ed7f5d646ffe6a390ff8bd3c267e2aa65216b734cc2a0dfab6bb145c9d848cc6fb9acdedbece12ad0ff6b36fecfac70552665cc2e7af8ea3669be9e86b265afbaf3806118bc77da43051d78b8fe46aad724587dc2f53c72e96776ad0833f0f0e8d4fc43cafe474756d8b6bae92abb9a1850203010001";

//        private static final String ADDRESS = "http://127.0.0.1:8040";
    private static final String ADDRESS = "http://mkdev01.ywork.me";
//    private static final String ADDRESS = "http://p.landray.com.cn";

    public static void main(String[] args) {
//        MkLoginResult loginResult = MkLoginHelper.doLoginWithPubKey(
//                ADDRESS, "yaowr", "2", null, RSA_PUB_KEY);
        MkLoginResult loginResult = MkLoginHelper.login(
                ADDRESS, "yaowr", "2", "R4D01", RSA_PUB_KEY);
        log.info("login result: {}", JSONObject.toJSONString(loginResult, SerializerFeature.PrettyFormat));
    }
}
