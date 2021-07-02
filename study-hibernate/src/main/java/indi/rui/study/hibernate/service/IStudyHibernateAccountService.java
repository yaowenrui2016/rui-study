package indi.rui.study.hibernate.service;

import indi.rui.study.hibernate.entity.StudyHibernateAccount;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-04-22
 */
public interface IStudyHibernateAccountService {

    void save(StudyHibernateAccount entity);

    StudyHibernateAccount get(Long id);

    List<StudyHibernateAccount> list();

    void createShard(String suffix);
}
