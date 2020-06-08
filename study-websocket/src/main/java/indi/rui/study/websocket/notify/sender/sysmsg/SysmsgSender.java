package indi.rui.study.websocket.notify.sender.sysmsg;

import com.alibaba.fastjson.JSON;
import indi.rui.study.websocket.notify.NotifyContext;
import indi.rui.study.websocket.notify.sender.ISender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Slf4j
@Service
public class SysmsgSender implements ISender {

    @Autowired
    private SysmsgService studyRedissonNotifyService;

    @Transactional
    @Override
    public void send(NotifyContext notifyContext) {
        log.info("发送【系统消息】：{}", JSON.toJSONString(notifyContext));
        SysmsgVO notifyVO = new SysmsgVO();
        notifyVO.setFdSubject(notifyContext.getSubject());
        notifyVO.setFdAppName(notifyContext.getAppName());
        notifyVO.setFdModuleName(notifyContext.getModuleName());
        notifyVO.setFdEntityName(notifyContext.getEntityName());
        notifyVO.setFdEntityId(notifyContext.getEntityId());
        for (String target : notifyContext.getTargets()) {
            notifyVO.setFdTarget(target);
            studyRedissonNotifyService.save(notifyVO);
        }
    }

    @Override
    public void done(NotifyContext notifyContext) {
        System.out.println("SysmsgSender.done()");
    }

    @Override
    public void remove(NotifyContext notifyContext) {
        System.out.println("SysmsgSender.remove()");
    }
}
