package indi.rui.study.hibernate.repository;

import indi.rui.study.hibernate.entity.StudyHibernateAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: yaowr
 * @create: 2020-04-22
 */
@Repository
public interface StudyHibernateAccountRepository extends CrudRepository<StudyHibernateAccount, Long> {
}
