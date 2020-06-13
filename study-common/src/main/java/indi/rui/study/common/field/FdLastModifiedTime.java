package indi.rui.study.common.field;

import indi.rui.study.common.IData;

import javax.persistence.Column;
import java.util.Date;

/**
 * @author: yaowr
 * @create: 2020-06-13
 */
public interface FdLastModifiedTime extends IData, IField {

    @Column
    default Date getFdLastModifiedTime() {
        return (Date) getDynamicProps().get("fdLastModifiedTime");
    }

}
