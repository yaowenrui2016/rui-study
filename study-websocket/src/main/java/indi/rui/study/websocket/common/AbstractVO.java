package indi.rui.study.websocket.common;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
@Getter
@Setter
public abstract class AbstractVO {
    private Long fdId;
    private Date fdCreateTime;
    private Date fdLastModifyTime;
}
