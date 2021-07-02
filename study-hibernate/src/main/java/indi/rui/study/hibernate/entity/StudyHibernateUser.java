package indi.rui.study.hibernate.entity;

import indi.rui.study.hibernate.generator.CreateTimeGenerator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Date;

/**
 * @author: yaowr
 * @create: 2020-04-21
 */
@Getter
@Setter
@Entity
public class StudyHibernateUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nickName;

    @GeneratorType(type = CreateTimeGenerator.class, when = GenerationTime.INSERT)
    private Date createTime;

    @UpdateTimestamp
    private Date updateOn;
}
