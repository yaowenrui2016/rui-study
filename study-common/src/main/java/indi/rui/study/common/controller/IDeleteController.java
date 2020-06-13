package indi.rui.study.common.controller;

import indi.rui.study.common.IApi;
import indi.rui.study.common.Response;
import indi.rui.study.common.dto.IVO;
import indi.rui.study.common.dto.IdsVO;
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
