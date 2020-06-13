package indi.rui.study.common.controller;

import indi.rui.study.common.IApi;
import indi.rui.study.common.dto.IVO;
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
