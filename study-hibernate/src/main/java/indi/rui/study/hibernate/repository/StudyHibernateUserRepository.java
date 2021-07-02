package indi.rui.study.hibernate.repository;

import indi.rui.study.hibernate.entity.StudyHibernateUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: yaowr
 * @create: 2020-04-22
 */
@Repository
public interface StudyHibernateUserRepository extends CrudRepository<StudyHibernateUser, Long> {
}
