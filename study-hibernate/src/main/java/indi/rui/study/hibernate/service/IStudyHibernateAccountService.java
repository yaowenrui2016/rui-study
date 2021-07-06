package indi.rui.study.hibernate.service;

import indi.rui.study.hibernate.entity.StudyHibernateAccount;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-04-22
 */
public interface IStudyHibernateAccountService {

    @PostMapping("save")
    void save(@RequestBody StudyHibernateAccount entity);

    @PostMapping("get")
    StudyHibernateAccount get(@RequestBody Long id);

    @PostMapping("findAll")
    List<StudyHibernateAccount> findAll(@PathVariable(value = "suffixNo",required = false) Integer suffixNo);
}
