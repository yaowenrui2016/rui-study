package indi.rui.study.websocket.notify;

import indi.rui.study.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author: yaowr
 * @create: 2020-06-15
 */
@RestController
@RequestMapping("/service/notify")
public class NotifyController {

    @Autowired
    private NotifyService notifyService;

    @PostMapping("send")
    public Response send(@RequestBody @Valid() NotifyContext notifyContext) {
        return Response.ok(notifyService.send(notifyContext));
    }

    @PostMapping("done")
    public Response done(@RequestBody NotifyContext notifyContext) {
        return Response.ok(notifyService.done(notifyContext));
    }

    @PostMapping("remove")
    public Response remove(@RequestBody NotifyContext notifyContext) {
        return Response.ok(notifyService.remove(notifyContext));
    }
}
