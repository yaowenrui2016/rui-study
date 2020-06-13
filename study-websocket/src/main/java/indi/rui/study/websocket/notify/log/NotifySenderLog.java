package indi.rui.study.websocket.notify.log;

import indi.rui.study.common.entity.AbstractEntity;
import indi.rui.study.common.field.FdContent;
import indi.rui.study.common.field.FdCreateTime;
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
public class NotifySenderLog extends AbstractEntity implements FdContent, FdCreateTime {
    private String fdSender;
}
