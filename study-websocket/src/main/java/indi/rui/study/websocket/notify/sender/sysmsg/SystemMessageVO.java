package indi.rui.study.websocket.notify.sender.sysmsg;

import indi.rui.study.redisson.common.AbstractVO;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Getter
@Setter
public class SystemMessageVO extends AbstractVO {
    private String fdSubject;
    private String fdAppName;
    private String fdModuleName;
    private String fdEntityName;
    private String fdEntityId;
    private String fdType;
    private String fdTarget;
}
