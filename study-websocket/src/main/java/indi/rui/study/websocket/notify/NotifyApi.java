package indi.rui.study.websocket.notify;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
public interface NotifyApi {
    @PostMapping("send")
    void send(@RequestBody NotifyContext notifyContext);

    @PostMapping("done")
    void done(@RequestBody NotifyContext notifyContext);

    @PostMapping("remove")
    void remove(@RequestBody NotifyContext notifyContext);
}
