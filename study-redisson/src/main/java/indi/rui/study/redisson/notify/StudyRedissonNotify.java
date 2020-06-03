package indi.rui.study.redisson.notify;

import indi.rui.study.redisson.common.AbstractEntity;
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
public class StudyRedissonNotify extends AbstractEntity {
    private String fdSubject;
    private String fdAppName;
    private String fdModuleName;
    private String fdEntityName;
    private String fdEntityId;
    private String fdTarget;
}
