package indi.rui.study.hibernate.service;

import indi.rui.study.hibernate.entity.StudyHibernateAccount;
import indi.rui.study.hibernate.entity.StudyHibernateUser;
import indi.rui.study.hibernate.repository.StudyHibernateAccountRepository;
import indi.rui.study.hibernate.repository.StudyHibernateUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-04-22
 */
@Slf4j
@Service
public class StudyHibernateUserServiceImpl implements IStudyHibernateUserService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private StudyHibernateUserRepository repository;

    @Override
    public void save(StudyHibernateUser entity) {
        StudyHibernateUser oldEntity = get(entity.getId());
        if (oldEntity == null) {
            oldEntity = new StudyHibernateUser();
        }
        BeanUtils.copyProperties(entity, oldEntity);
        repository.save(oldEntity);
    }

    @Override
    public StudyHibernateUser get(Long id) {
        if (id == null) {
            return null;
        }
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<StudyHibernateUser> list() {
        Iterator<StudyHibernateUser> iterator = repository.findAll().iterator();
        List<StudyHibernateUser> rtnList = new ArrayList<>();
        while (iterator.hasNext()) {
            rtnList.add(iterator.next());
        }
        return rtnList;
    }
}
