package indi.rui.study.message.handler;

import indi.rui.study.message.Message;
import indi.rui.study.message.annotation.HandlerType;
import lombok.extern.slf4j.Slf4j;

/**
 * 通知
 * @author: yaowr
 * @create: 2019-10-31
 */
@Slf4j
@HandlerType("inform")
public class InformHandler implements Handler {
    @Override
    public void handle(Message context) {
        log.info("inform");
    }
}
