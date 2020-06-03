package indi.rui.study.redisson.notify.sender;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
public interface ISender {
    @PostMapping("send")
    void send(@RequestBody NotifyContext notifyContext);
}
