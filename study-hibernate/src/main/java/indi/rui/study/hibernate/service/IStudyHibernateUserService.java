package indi.rui.study.hibernate.service;

import indi.rui.study.hibernate.entity.StudyHibernateUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-04-22
 */
public interface IStudyHibernateUserService {

    @PostMapping("save")
    void save(@RequestBody StudyHibernateUser entity);

    @PostMapping("get")
    StudyHibernateUser get(@RequestBody Long id);

    @PostMapping("findAll")
    List<StudyHibernateUser> findAll();
}
