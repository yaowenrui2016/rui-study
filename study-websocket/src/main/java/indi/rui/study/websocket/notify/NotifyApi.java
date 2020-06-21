package indi.rui.study.websocket.notify;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
public interface NotifyApi {
    /**
     * 发送消息
     * 返回消息ID
     *
     * @param notifyContext
     * @return
     */
    @PostMapping("send")
    long send(@RequestBody NotifyContext notifyContext);

    /**
     * 置为已办
     * 指定消息ID和接收人，如未指定接收人则将所有接收人执行DONE操作
     *
     * @param notifyContext
     */
    @PostMapping("done")
    int done(@RequestBody NotifyContext notifyContext);

    @PostMapping("remove")
    int remove(@RequestBody NotifyContext notifyContext);
}
