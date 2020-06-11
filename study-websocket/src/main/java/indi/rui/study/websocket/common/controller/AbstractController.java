package indi.rui.study.websocket.common.controller;

import indi.rui.study.websocket.common.IApi;
import indi.rui.study.websocket.common.dto.IVO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author: yaowr
 * @create: 2020-06-09
 */
public class AbstractController<V extends IVO, A extends IApi<V>> implements IController<V, A> {

    @Autowired
    private A api;

    @Override
    public A getApi() {
        return api;
    }
}
