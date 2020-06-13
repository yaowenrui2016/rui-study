package indi.rui.study.common.field;

import indi.rui.study.common.IData;

import javax.persistence.Column;
import javax.persistence.Lob;
import java.util.Date;

/**
 * @author: yaowr
 * @create: 2020-06-13
 */
public interface FdContent extends IData, IField {

    @Column
    @Lob
    default String getFdContent() {
        return (String) getDynamicProps().get("fdContent");
    }
}
