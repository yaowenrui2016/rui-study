package indi.rui.study.redisson.notify.sender.sysmsg;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author: yaowr
 * @create: 2020-06-03
 */
@Repository
public interface SystemMessageRepository extends CrudRepository<SystemMessage, Long> {
}
