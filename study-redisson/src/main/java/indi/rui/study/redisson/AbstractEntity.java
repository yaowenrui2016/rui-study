package indi.rui.study.redisson;

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
@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @NotNull
    private Long fdId = IDGenerator.get();
}
