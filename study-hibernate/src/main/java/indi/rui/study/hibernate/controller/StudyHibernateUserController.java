package indi.rui.study.hibernate.controller;

import indi.rui.study.hibernate.entity.StudyHibernateUser;
import indi.rui.study.hibernate.service.IStudyHibernateUserService;
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
@RequestMapping("/study/hibernate/user")
public class StudyHibernateUserController {

    @Autowired
    private IStudyHibernateUserService studyHibernateUserService;

    @PostMapping("add")
    public void save(@RequestBody StudyHibernateUser entity) {
        studyHibernateUserService.save(entity);
    }

    @PostMapping("get")
    public StudyHibernateUser get(@RequestBody Long id) {
        return studyHibernateUserService.get(id);
    }

    @PostMapping("list")
    public List<StudyHibernateUser> list() {
        return studyHibernateUserService.list();
    }
}
