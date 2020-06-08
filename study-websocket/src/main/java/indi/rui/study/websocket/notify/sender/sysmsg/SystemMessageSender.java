package indi.rui.study.websocket.notify.sender.sysmsg;

import indi.rui.study.websocket.notify.NotifyContext;
import indi.rui.study.websocket.notify.sender.ISender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Service
public class SystemMessageSender implements ISender {

    @Autowired
    private SystemMessageService studyRedissonNotifyService;

    @Transactional
    @Override
    public void send(NotifyContext notifyContext) {
        System.out.println("SystemMessageSender.send()");
//        SystemMessageVO notifyVO = new SystemMessageVO();
//        notifyVO.setFdSubject(notifyContext.getSubject());
//        notifyVO.setFdAppName(notifyContext.getAppName());
//        notifyVO.setFdModuleName(notifyContext.getModuleName());
//        notifyVO.setFdEntityName(notifyContext.getEntityName());
//        notifyVO.setFdEntityId(notifyContext.getEntityId());
//        for (String target : notifyContext.getTargets()) {
//            notifyVO.setFdTarget(target);
//            studyRedissonNotifyService.save(notifyVO);
//        }
    }

    @Override
    public void done(NotifyContext notifyContext) {
        System.out.println("SystemMessageSender.done()");
    }

    @Override
    public void remove(NotifyContext notifyContext) {
        System.out.println("SystemMessageSender.remove()");
    }
}
