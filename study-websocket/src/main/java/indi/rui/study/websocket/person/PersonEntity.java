package indi.rui.study.websocket.person;

import indi.rui.study.websocket.common.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author: yaowr
 * @create: 2020-06-08
 */
@Getter
@Setter
@Entity
public class PersonEntity extends AbstractEntity {
    private String fdName;
    private String fdLoginName;
    private String fdPassword;
    private String fdGender;
}
