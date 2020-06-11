package indi.rui.study.websocket.common.controller;

import indi.rui.study.websocket.common.IApi;
import indi.rui.study.websocket.common.dto.IVO;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
public interface IController<V extends IVO, A extends IApi<V>> {

    A getApi();
}
