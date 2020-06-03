package indi.rui.study.redisson.notify.sender;

import indi.rui.study.redisson.notify.NotifyContext;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
public interface ISender {
    void send(NotifyContext notifyContext);

    void done(NotifyContext notifyContext);

    void remove(NotifyContext notifyContext);
}
