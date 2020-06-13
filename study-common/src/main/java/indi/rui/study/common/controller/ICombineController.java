package indi.rui.study.common.controller;

import indi.rui.study.common.IApi;
import indi.rui.study.common.dto.IVO;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
public interface ICombineController<V extends IVO, A extends IApi<V>>
        extends ICrudController<V, A>, IFindController<V, A>, IDeleteController<V, A> {
}
