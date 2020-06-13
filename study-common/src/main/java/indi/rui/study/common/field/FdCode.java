package indi.rui.study.common.field;

import indi.rui.study.common.IData;

import javax.persistence.Column;

/**
 * @author: yaowr
 * @create: 2020-06-13
 */
public interface FdCode extends IData, IField {

    @Column
    default String getFdCode() {
        return (String) getDynamicProps().get("fdCode");
    }

    default void setFdCode(String fdCode) {
        getDynamicProps().put("fdCode", fdCode);
    }
}
