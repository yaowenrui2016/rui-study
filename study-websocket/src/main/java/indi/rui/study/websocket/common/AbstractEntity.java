package indi.rui.study.websocket.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @NotNull
    private Long fdId = IDGenerator.get();
    private Date fdCreateTime;
    private Date fdLastModifyTime;
}
