package indi.rui.study.websocket.common.controller;

import indi.rui.study.websocket.common.IApi;
import indi.rui.study.websocket.common.query.QueryRequest;
import indi.rui.study.websocket.common.Response;
import indi.rui.study.websocket.common.dto.IVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
public interface IFindController<V extends IVO, A extends IApi<V>> extends IController<V, A> {

    @PostMapping("find")
    default Response update(@RequestBody QueryRequest request) {
        return Response.ok(getApi().find(request));
    }
}
