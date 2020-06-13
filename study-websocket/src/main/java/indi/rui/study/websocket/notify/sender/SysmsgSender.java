package indi.rui.study.websocket.notify.sender;

import com.alibaba.fastjson.JSON;
import indi.rui.study.websocket.notify.NotifyContext;
import indi.rui.study.websocket.notify.sysmsg.SysmsgService;
import indi.rui.study.websocket.notify.sysmsg.SysmsgVO;
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
    private SysmsgService sysmsgService;

    @Transactional
    @Override
    public void send(NotifyContext notifyContext) {
        log.info("发送【系统消息】：{}", JSON.toJSONString(notifyContext));
        SysmsgVO sysmsgVO = new SysmsgVO();
        sysmsgVO.setFdSubject(notifyContext.getSubject());
        sysmsgVO.setFdAppName(notifyContext.getAppName());
        sysmsgVO.setFdModuleName(notifyContext.getModuleName());
        sysmsgVO.setFdEntityName(notifyContext.getEntityName());
        sysmsgVO.setFdEntityId(notifyContext.getEntityId());
        for (String target : notifyContext.getTargets()) {
            sysmsgVO.setFdTarget(target);
            sysmsgService.add(sysmsgVO);
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
