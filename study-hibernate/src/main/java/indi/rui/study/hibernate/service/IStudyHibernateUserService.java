package indi.rui.study.hibernate.service;

import indi.rui.study.hibernate.entity.StudyHibernateUser;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-04-22
 */
public interface IStudyHibernateUserService {

    void save(StudyHibernateUser entity);

    StudyHibernateUser get(Long id);

    List<StudyHibernateUser> list();
}
