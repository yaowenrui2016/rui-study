package indi.rui.study.websocket.notify.sender.sysmsg;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Getter
@Setter
public class SysmsgVO {
    private String fdId;
    private String fdSubject;
    private String fdAppName;
    private String fdModuleName;
    private String fdEntityName;
    private String fdEntityId;
    private String fdType;
    private String fdTarget;
}
