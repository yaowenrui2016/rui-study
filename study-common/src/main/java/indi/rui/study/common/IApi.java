package indi.rui.study.common;

import indi.rui.study.common.dto.*;
import indi.rui.study.common.query.QueryRequest;
import indi.rui.study.common.query.QueryResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
public interface IApi<V extends IVO> {

    @PostMapping("add")
    void add(@RequestBody V vo);

    @PostMapping("update")
    void update(@RequestBody V vo);

    @PostMapping("find")
    QueryResult<V> find(@RequestBody QueryRequest request);

    @PostMapping("get")
    V get(@RequestBody IdVO idVO);

    @PostMapping("delete")
    void delete(@RequestBody IdVO idVO);

    @PostMapping("deleteByIds")
    void deleteByIds(@RequestBody IdsVO idsVO);
}
