package indi.rui.study.unittest.callapi;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import indi.rui.study.unittest.dto.InformedMethodDTO;
import indi.rui.study.unittest.dto.MkResponse;
import indi.rui.study.unittest.dto.NotifyTypeVO;
import indi.rui.study.unittest.netty.HttpClient;
import indi.rui.study.unittest.netty.HttpResult;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpHeaders;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Slf4j
public class CallApiDoneAllOfThem {

    private static final String address = "Http://127.0.0.1:8040";
    private static final String xServiceName = "73456775666d4c416f73776139584a4131432f6847413d3d";

    public static void main(String[] args) throws Exception {
        // 将所有待办置已办
        doneAllOfThem();
        log.info("doneAllOfThem success");
    }

    private static List<NotifyTypeVO> doneAllOfThem() throws Exception {
        // 拉取来源系统和模块
        String url = address + "/api/sys-notify/sysNotifyTodo/doneAllOfThem";
        HttpHeaders httpHeaders = new DefaultHttpHeaders(true);
        httpHeaders.set("X-SERVICE-NAME", xServiceName);
        HttpResult httpResult = HttpClient.post(url, null, httpHeaders);
        List<NotifyTypeVO> resultContent = JSON.parseObject(httpResult.getContent(),
                new TypeReference<MkResponse<InformedMethodDTO>>() {
                }).getData().getNotifyTypes();
        return resultContent;
    }
}
