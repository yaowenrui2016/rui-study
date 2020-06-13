package indi.rui.study.common.repository;

import indi.rui.study.common.entity.IEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author: yaowr
 * @create: 2020-06-09
 */
public interface IRepository<E extends IEntity> extends CrudRepository<E, Long> {
}
