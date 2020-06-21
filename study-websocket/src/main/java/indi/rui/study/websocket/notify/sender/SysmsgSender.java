package indi.rui.study.websocket.notify.sender;

import com.alibaba.fastjson.JSON;
import indi.rui.study.common.dto.Condition;
import indi.rui.study.common.query.QueryRequest;
import indi.rui.study.common.utils.IDGenerator;
import indi.rui.study.websocket.notify.NotifyContext;
import indi.rui.study.websocket.notify.sysmsg.SysmsgService;
import indi.rui.study.websocket.notify.sysmsg.SysmsgVO;
import indi.rui.study.websocket.person.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Slf4j
@Service
public class SysmsgSender implements ISender {

    @Autowired
    private SysmsgService sysmsgService;
    @Autowired
    private PersonService personService;

    @Transactional
    @Override
    public long send(NotifyContext notifyContext) {
        log.info("发送【系统消息】：{}", JSON.toJSONString(notifyContext));
        if (Objects.isNull(notifyContext.getNotifyId())) {
            notifyContext.setNotifyId(IDGenerator.get());
        }
        // 构建接收人ID
        buildTargets(notifyContext);
        // 转换消息
        SysmsgVO sysmsgVO = convert(notifyContext);
        // 按接收人逐条发送
        for (String target : notifyContext.getTargets()) {
            sysmsgVO.setFdTarget(target);
            sysmsgService.add(sysmsgVO);
        }
        return notifyContext.getNotifyId();
    }

    private SysmsgVO convert(NotifyContext notifyContext) {
        SysmsgVO sysmsgVO = new SysmsgVO();
        sysmsgVO.setFdSubject(notifyContext.getSubject());
        sysmsgVO.setFdNotifyId(notifyContext.getNotifyId());
        sysmsgVO.setFdAppName(notifyContext.getAppName());
        sysmsgVO.setFdModuleName(notifyContext.getModuleName());
        sysmsgVO.setFdEntityName(notifyContext.getEntityName());
        sysmsgVO.setFdEntityId(notifyContext.getEntityId());
        return sysmsgVO;
    }

    private void buildTargets(NotifyContext notifyContext) {
        QueryRequest request = new QueryRequest();
        if (Objects.isNull(notifyContext.getTargetProperty())) {
            notifyContext.setTargetProperty("fdId");
        }
        Condition condition = new Condition(notifyContext.getTargetProperty(), Condition.Operator.in, notifyContext.getTargets());
        request.setConditions(Collections.singletonList(condition));
        List<String> targets = personService.find(request).getContent().stream()
                .map(personVO -> String.valueOf(personVO.getFdId()))
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(targets)) {
            throw new RuntimeException("未找到有效接收人");
        }
        notifyContext.setTargets(targets);
    }

    @Override
    public int done(NotifyContext notifyContext) {
        System.out.println("SysmsgSender.done()");
        return 0;
    }

    @Override
    public int remove(NotifyContext notifyContext) {
        System.out.println("SysmsgSender.remove()");
        return 0;
    }
}
