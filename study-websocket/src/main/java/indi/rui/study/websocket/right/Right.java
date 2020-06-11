package indi.rui.study.websocket.right;

import indi.rui.study.websocket.common.entity.AbstractEntity;
import indi.rui.study.websocket.common.field.FdCreateTime;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
@Getter
@Setter
@Entity(name = "`right`")
@Access(AccessType.PROPERTY)
public class Right extends AbstractEntity implements FdCreateTime {
    private String fdName;

}
