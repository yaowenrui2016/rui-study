package indi.rui.study.hibernate.controller;

import indi.rui.study.hibernate.entity.StudyHibernateAccount;
import indi.rui.study.hibernate.service.IStudyHibernateAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-04-17
 */
@RestController
@RequestMapping("/study/hibernate/account")
public class StudyHibernateAccountController {

    @Autowired
    private IStudyHibernateAccountService studyHibernateAccountService;

    @PostMapping("add")
    public void save(@RequestBody StudyHibernateAccount entity) {
        studyHibernateAccountService.save(entity);
    }

    @PostMapping("get")
    public StudyHibernateAccount get(@RequestBody Long id) {
        return studyHibernateAccountService.get(id);
    }

    @PostMapping("list")
    public List<StudyHibernateAccount> list() {
        return studyHibernateAccountService.list();
    }

    @PostMapping("createShard")
    public void createShard(@RequestBody String suffix) {
        studyHibernateAccountService.createShard(suffix);
    }
}
