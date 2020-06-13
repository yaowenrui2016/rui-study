package indi.rui.study.websocket.notify.sender.sysmsg;

import indi.rui.study.common.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Getter
@Setter
@Entity
public class SysmsgEntity extends AbstractEntity {
    private String fdSubject;
    private String fdAppName;
    private String fdModuleName;
    private String fdEntityName;
    private String fdEntityId;
    private String fdType;
    private String fdTarget;
}
