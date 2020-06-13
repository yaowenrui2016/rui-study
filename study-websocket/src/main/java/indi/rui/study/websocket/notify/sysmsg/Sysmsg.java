package indi.rui.study.websocket.notify.sysmsg;

import indi.rui.study.common.entity.AbstractEntity;
import indi.rui.study.common.field.FdCreateTime;
import indi.rui.study.websocket.person.Person;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Getter
@Setter
@Entity
public class Sysmsg extends AbstractEntity implements FdCreateTime {
    private String fdSubject;
    private String fdAppName;
    private String fdModuleName;
    private String fdEntityName;
    private String fdEntityId;
    private String fdType;
    private String fdTarget;
}
