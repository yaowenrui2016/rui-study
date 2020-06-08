package indi.rui.study.websocket.notify.sender;

import indi.rui.study.websocket.notify.NotifyContext;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
public interface ISender {
    void send(NotifyContext notifyContext);

    void done(NotifyContext notifyContext);

    void remove(NotifyContext notifyContext);
}
