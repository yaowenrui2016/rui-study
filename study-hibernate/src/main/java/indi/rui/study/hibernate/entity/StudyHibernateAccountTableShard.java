package indi.rui.study.hibernate.entity;

import indi.rui.study.hibernate.generator.CreateTimeGenerator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author: yaowr
 * @create: 2021-07-05
 */
@Getter
@Setter
@Entity
public class StudyHibernateAccountTableShard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer suffixNo;
    private Boolean isCurrent;
    @GeneratorType(type = CreateTimeGenerator.class, when = GenerationTime.INSERT)
    private Date createTime;
}
