package indi.rui.study.common.controller;

import indi.rui.study.common.IApi;
import indi.rui.study.common.dto.IVO;
import indi.rui.study.common.dto.IdVO;
import indi.rui.study.common.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
public interface ICrudController<V extends IVO, A extends IApi<V>> extends IController<V, A> {

    @PostMapping("add")
    default Response add(@RequestBody V vo) {
        getApi().add(vo);
        return Response.ok();
    }

    @PostMapping("update")
    default Response update(@RequestBody V vo) {
        getApi().update(vo);
        return Response.ok();
    }

    @PostMapping("get")
    default Response get(@RequestBody IdVO idVO) {
        getApi().get(idVO);
        return Response.ok();
    }

    @PostMapping("delete")
    default Response delete(@RequestBody V vo) {
        getApi().update(vo);
        return Response.ok();
    }
}
