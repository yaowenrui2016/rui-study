package indi.rui.study.hibernate.entity;

import indi.rui.study.hibernate.generator.CreateTimeGenerator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: yaowr
 * @create: 2020-06-07
 */
@Getter
@Setter
@Entity
public class StudyHibernateAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginName;
    private String password;

    private Long userId;

    @GeneratorType(type = CreateTimeGenerator.class, when = GenerationTime.INSERT)
    private Date createTime;

    @UpdateTimestamp
    private Date updateOn;
}
