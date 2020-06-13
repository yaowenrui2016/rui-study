package indi.rui.study.common.field;

import indi.rui.study.common.IData;

import javax.persistence.Column;

/**
 * @author: yaowr
 * @create: 2020-06-12
 */
public interface FdStatus extends IData, IField {

    @Column
    default Integer getFdStatus() {
        return (Integer) getDynamicProps().get("fdStatus");
    }

    default void setFdStatus(Integer fdStatus) {
        getDynamicProps().put("fdStatus", fdStatus);
    }
}
