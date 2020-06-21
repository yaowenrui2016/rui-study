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
    public long send(NotifyContext notifyContext) {
        System.out.println("EmailSender.send()");
        return 0;
    }

    @Override
    public int done(NotifyContext notifyContext) {
        System.out.println("EmailSender.done()");
        return 0;
    }

    @Override
    public int remove(NotifyContext notifyContext) {
        System.out.println("EmailSender.remove()");
        return 0;
    }
}
