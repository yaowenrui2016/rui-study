package indi.rui.study.hibernate.repository;

import indi.rui.study.hibernate.entity.StudyHibernateAccountTableShard;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-07-05
 */
@Repository
public interface StudyHibernateAccountTableShardRepository extends CrudRepository<StudyHibernateAccountTableShard, Long> {
    @Query("from StudyHibernateAccountTableShard where fdIsCurrent = 1")
    List<StudyHibernateAccountTableShard> getCurrent();

    @Modifying
    @Query("update StudyHibernateAccountTableShard set isCurrent = 0")
    void updateCurrentStatusToFalse();

    @Query("select max(suffixNo) from StudyHibernateAccountTableShard")
    Integer getMaxSuffixNo();

    @Modifying
    @Query("update StudyHibernateAccountTableShard set isCurrent = 1 where suffixNo = ?1")
    void updateCurrent(Integer suffixNo);
}
