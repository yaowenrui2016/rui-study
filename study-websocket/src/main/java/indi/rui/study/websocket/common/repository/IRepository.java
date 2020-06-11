package indi.rui.study.websocket.common.repository;

import indi.rui.study.websocket.common.entity.IEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author: yaowr
 * @create: 2020-06-09
 */
public interface IRepository<E extends IEntity> extends CrudRepository<E, Long> {
}
