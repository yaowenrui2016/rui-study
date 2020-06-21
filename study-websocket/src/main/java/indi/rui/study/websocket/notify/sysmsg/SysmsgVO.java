package indi.rui.study.websocket.notify.sysmsg;

import indi.rui.study.common.dto.AbstractVO;
import indi.rui.study.common.field.FdCreateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Getter
@Setter
public class SysmsgVO extends AbstractVO implements FdCreateTime {
    private String fdSubject;
    private Long fdNotifyId;
    private String fdAppName;
    private String fdModuleName;
    private String fdEntityName;
    private String fdEntityId;
    private String fdType;
    private String fdTarget;
}
