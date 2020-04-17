package indi.rui.study.message.handler;

import indi.rui.study.message.Message;
import indi.rui.study.message.annotation.HandlerType;
import lombok.extern.slf4j.Slf4j;

/**
 * 邮件
 * @author: yaowr
 * @create: 2019-10-31
 */
@Slf4j
@HandlerType("email")
public class EmailHandler implements Handler {
    @Override
    public void handle(Message context) {
        log.info("email");
    }
}
