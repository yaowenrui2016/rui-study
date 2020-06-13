package indi.rui.study.websocket.notify.sender;

import indi.rui.study.websocket.notify.NotifyContext;

/**
 * 消息发送器
 *
 * @author: yaowr
 * @create: 2020-06-03
 */
public interface ISender {
    /**
     * 发送消息
     *
     * @param notifyContext
     */
    void send(NotifyContext notifyContext);

    /**
     * 置为已办
     *
     * @param notifyContext
     */
    void done(NotifyContext notifyContext);

    /**
     * 移除消息
     *
     * @param notifyContext
     */
    void remove(NotifyContext notifyContext);
}
