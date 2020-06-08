package indi.rui.study.websocket.person;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: yaowr
 * @create: 2020-06-08
 */
@Repository
public interface PersonRepository extends CrudRepository<PersonEntity, Long> {
}
