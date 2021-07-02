package indi.rui.study.hibernate.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author: yaowr
 * @create: 2021-07-01
 */
@Getter
@Setter
@Entity
public class StudyHibernateRight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
