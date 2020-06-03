package indi.rui.study.redisson.common;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
@Getter
@Setter
public abstract class AbstractVO {
    private Long fdId;
}
