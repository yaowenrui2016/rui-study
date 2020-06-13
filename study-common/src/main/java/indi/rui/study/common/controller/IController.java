package indi.rui.study.common.controller;

import indi.rui.study.common.IApi;
import indi.rui.study.common.dto.IVO;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
public interface IController<V extends IVO, A extends IApi<V>> {

    A getApi();
}
