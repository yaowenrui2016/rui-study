package indi.rui.study.websocket.common.service;

import indi.rui.study.websocket.common.IApi;
import indi.rui.study.websocket.common.dto.IVO;
import indi.rui.study.websocket.common.dto.IdVO;
import indi.rui.study.websocket.common.entity.IEntity;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

/**
 * @author: yaowr
 * @create: 2020-06-09
 */
public interface IService<E extends IEntity, V extends IVO> extends IApi<V> {

    @PostMapping("loadById")
    Optional<E> loadById(IdVO idVO);

    @PostMapping("deleteAll")
    void deleteAll();

    /**
     * 获取Entity的实现类
     *
     * @return
     */
    Class<E> getEntityClass();

    /**
     * 获取ViewObject的实现类
     *
     * @return
     */
    Class<V> getViewObjectClass();
}
