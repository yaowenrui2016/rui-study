package indi.rui.study.websocket.notify;

import indi.rui.study.websocket.notify.email.EmailSender;
import indi.rui.study.websocket.notify.sender.ISender;
import indi.rui.study.websocket.notify.sender.SysmsgSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@RestController
@RequestMapping("/api/notify")
public class NotifyService implements NotifyApi {

    @Autowired
    private SysmsgSender sysmsgSender;
    @Autowired
    private EmailSender emailSender;

    @Override
    public long send(NotifyContext notifyContext) {
        return choose(notifyContext).send(notifyContext);
    }

    @Override
    public int done(NotifyContext notifyContext) {
        return choose(notifyContext).done(notifyContext);
    }

    @Override
    public int remove(NotifyContext notifyContext) {
        return choose(notifyContext).remove(notifyContext);
    }

    private ISender choose(NotifyContext notifyContext) {
        String sender = notifyContext.getSender();
        ISender iSender;
        switch (sender) {
            case "sysmsg":
                iSender = sysmsgSender;
                break;
            case "email":
                iSender = emailSender;
                break;
            default:
                throw new RuntimeException("不支持的消息发送方式");
        }
        return iSender;
    }
}
