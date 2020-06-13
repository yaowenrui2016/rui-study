package indi.rui.study.redisson.person;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import indi.rui.study.common.entity.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
@Getter
@Setter
@Entity
@Table
public class Account extends AbstractEntity {
    @Column
    private String fdUsername;

    @Column
    private String fdPassword;
}
