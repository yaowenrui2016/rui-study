package indi.rui.study.websocket.notify.email;

import indi.rui.study.websocket.notify.NotifyContext;
import indi.rui.study.websocket.notify.sender.ISender;
import org.springframework.stereotype.Service;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Service
public class EmailSender implements ISender {
    @Override
    public void send(NotifyContext notifyContext) {
        System.out.println("EmailSender.send()");
    }

    @Override
    public void done(NotifyContext notifyContext) {
        System.out.println("EmailSender.done()");
    }

    @Override
    public void remove(NotifyContext notifyContext) {
        System.out.println("EmailSender.remove()");
    }
}
