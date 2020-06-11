package indi.rui.study.websocket.person;

import indi.rui.study.websocket.common.entity.AbstractEntity;
import indi.rui.study.websocket.common.field.FdCreateTime;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author: yaowr
 * @create: 2020-06-08
 */
@Getter
@Setter
@Entity
public class Person extends AbstractEntity implements FdCreateTime {
    private String fdName;
    private String fdLoginName;
    private String fdPassword;
    private String fdGender;
}
