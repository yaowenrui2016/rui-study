package indi.rui.study.hibernate.service;

import indi.rui.study.hibernate.entity.StudyHibernateAccount;
import indi.rui.study.hibernate.repository.StudyHibernateAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/study/hibernate/account")
public class StudyHibernateAccountServiceImpl implements IStudyHibernateAccountService {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private StudyHibernateAccountRepository repository;
    @Autowired
    private IStudyHibernateAccountTableShardService studyHibernateAccountTableShardService;

    @Override
    public void save(StudyHibernateAccount entity) {
        // 设置表名称
        studyHibernateAccountTableShardService.presetDefaultTable();
        StudyHibernateAccount oldEntity = get(entity.getId());
        if (oldEntity == null) {
            oldEntity = new StudyHibernateAccount();
        }
        BeanUtils.copyProperties(entity, oldEntity);
        repository.save(oldEntity);
    }

    @Override
    public StudyHibernateAccount get(Long id) {
        if (id == null) {
            return null;
        }
        studyHibernateAccountTableShardService.presetTable(suffixNo);
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<StudyHibernateAccount> findAll(Integer suffixNo) {
        studyHibernateAccountTableShardService.presetTable(suffixNo);
        Iterator<StudyHibernateAccount> iterator = repository.findAll().iterator();
        List<StudyHibernateAccount> rtnList = new ArrayList<>();
        while (iterator.hasNext()) {
            rtnList.add(iterator.next());
        }
        return rtnList;
    }
}
