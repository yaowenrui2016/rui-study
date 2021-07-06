package indi.rui.study.hibernate.service;

import indi.rui.study.hibernate.entity.StudyHibernateAccountTableShard;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-07-05
 */
public interface IStudyHibernateAccountTableShardService {
    @PostMapping("createShard")
    void createShard(@RequestBody String suffix);

    @PostMapping("findAll")
    List<StudyHibernateAccountTableShard> findAll();

    @PostMapping("getCurrent")
    StudyHibernateAccountTableShard getCurrent();

    @PostMapping("refreshCurrent")
    void refreshCurrent();

    @PostMapping("presetTable")
    void presetTable(Integer suffixNo);

    @PostMapping("presetDefaultTable")
    void presetDefaultTable();
}
