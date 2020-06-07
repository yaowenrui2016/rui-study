package indi.rui.study.websocket.notify;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author: yaowr
 * @create: 2020-06-07
 */
@Getter
@Setter
@Entity
@Table(indexes = {@Index(columnList = "fdSubject")})
public class NotifyContext {
    @Id
    private Long fdId;
    private String fdSubject;
    private String fdType;
}
