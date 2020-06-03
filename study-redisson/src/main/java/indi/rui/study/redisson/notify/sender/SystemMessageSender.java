package indi.rui.study.redisson.notify.sender;

import indi.rui.study.redisson.notify.StudyRedissonNotifyService;
import indi.rui.study.redisson.notify.StudyRedissonNotifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@RestController
@RequestMapping("/api/notify")
public class SystemMessageSender implements ISender {

    @Autowired
    private StudyRedissonNotifyService studyRedissonNotifyService;

    @Transactional
    @Override
    public void send(NotifyContext notifyContext) {
        StudyRedissonNotifyVO notifyVO = new StudyRedissonNotifyVO();
        notifyVO.setFdSubject(notifyContext.getSubject());
        notifyVO.setFdAppName(notifyContext.getAppName());
        notifyVO.setFdModuleName(notifyContext.getModuleName());
        notifyVO.setFdEntityName(notifyContext.getEntityName());
        notifyVO.setFdEntityId(notifyContext.getEntityId());
        for (int i = 0; i < 10000; i++) {
            notifyVO.setFdTarget("person_" + i);
            studyRedissonNotifyService.save(notifyVO);
        }
    }
}
