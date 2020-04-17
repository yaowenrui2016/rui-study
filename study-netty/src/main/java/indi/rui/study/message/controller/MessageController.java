package indi.rui.study.message.controller;

import indi.rui.common.base.dto.Response;
import indi.rui.study.message.Message;
import indi.rui.study.message.handler.HandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yaowr
 * @create: 2019-10-31
 */
@RestController
@RequestMapping("/data/message")
public class MessageController {

    @Autowired
    private HandlerContext handlerContext;

    @PostMapping("send")
    public Response send(@RequestBody Message message) {
        handlerContext.getHandler(message.getType()).handle(message);
        return Response.ok();
    }
}
