package indi.rui.study.redisson.person;

import java.util.Date;

import javax.persistence.*;

import indi.rui.study.redisson.common.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
@Getter
@Setter
@Entity
@Table(indexes = @Index(columnList = "fdName"))
public class Person extends AbstractEntity {
    @Column
    private String fdName;

    @Column
    @Convert(converter = GenderEnum.Convertor.class)
    private GenderEnum fdGender;

    @Column
    private Date fdBirthday;

    @Lob
    private String fdComment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fd_account_id")
    private Account fdAccount;
}
