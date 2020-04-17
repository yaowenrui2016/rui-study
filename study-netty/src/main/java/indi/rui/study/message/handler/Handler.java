package indi.rui.study.message.handler;

import indi.rui.study.message.Message;

/**
 * @author: yaowr
 * @create: 2019-10-31
 */
public interface Handler {
    void handle(Message context);
}
