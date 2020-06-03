package indi.rui.study.redisson.notify.sender.sysmsg;

import indi.rui.study.redisson.notify.NotifyContext;
import indi.rui.study.redisson.notify.sender.ISender;
import indi.rui.study.redisson.notify.sender.sysmsg.SystemMessageService;
import indi.rui.study.redisson.notify.sender.sysmsg.SystemMessageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
