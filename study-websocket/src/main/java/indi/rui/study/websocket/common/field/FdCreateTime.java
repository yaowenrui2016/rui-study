package indi.rui.study.websocket.common.field;

import indi.rui.study.websocket.common.IData;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Date;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
@MappedSuperclass
@Access(AccessType.PROPERTY)
public interface FdCreateTime extends IData, IField {

    @Column
    default Date getFdCreateTime() {
        return (Date) getDynamicProps().get("fdCreateTime");
    }

    default void setFdCreateTime(Date fdCreateTime) {
        getDynamicProps().put("fdCreateTime", fdCreateTime);
    }
}
