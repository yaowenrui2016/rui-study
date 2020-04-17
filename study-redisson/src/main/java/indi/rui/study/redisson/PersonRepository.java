package indi.rui.study.redisson;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    @Modifying
    @Query("delete from Person where fdGender = :gender")
    int deleteByGender(@Param("gender") GenderEnum gender);

    @Modifying
    @Query("delete from Person where fdName = :name")
    int deleteByName(@Param("name") String name);

    @Modifying
    @Query("delete from Person")
    int deleteByAll();

    @Modifying
    @Query("delete from Person where fdId in (:ids)")
    int deleteByIds(@Param("ids") List<Long> ids);

    @Modifying
    @Query("delete from Person where fdId >= ?1 and fdId <= ?2")
    int deleteBetweenIds(Long start, Long end);
}
