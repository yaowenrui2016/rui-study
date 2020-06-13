package indi.rui.study.websocket.notify.log;

import indi.rui.study.common.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.util.Date;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Getter
@Setter
@Entity
public class NotifySenderLog extends AbstractEntity {
    private Date fdSendTime;
    private String fdSender;
    @Lob
    private String fdContent;
}
