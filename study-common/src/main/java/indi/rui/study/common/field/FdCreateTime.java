package indi.rui.study.common.field;

import indi.rui.study.common.IData;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
public interface FdCreateTime extends IData, IField {

    @Column
    default Date getFdCreateTime() {
        return (Date) getDynamicProps().get("fdCreateTime");
    }

    default void setFdCreateTime(Date fdCreateTime) {
        getDynamicProps().put("fdCreateTime", fdCreateTime);
    }
}
