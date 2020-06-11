package indi.rui.study.websocket.common.controller;

import indi.rui.study.websocket.common.IApi;
import indi.rui.study.websocket.common.Response;
import indi.rui.study.websocket.common.dto.IVO;
import indi.rui.study.websocket.common.dto.IdsVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
public interface IDeleteController<V extends IVO, A extends IApi<V>> extends IController<V, A> {

    @PostMapping("deleteByIds")
    default Response deleteByIds(@RequestBody IdsVO idsVO) {
        getApi().deleteByIds(idsVO);
        return Response.ok();
    }
}
