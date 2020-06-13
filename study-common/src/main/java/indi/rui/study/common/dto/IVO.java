package indi.rui.study.common.dto;

import indi.rui.study.common.IData;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
public interface IVO extends IData {
    /**
     * 读-空值字段
     *
     * @return
     */
    public List<String> getNullValueProps();

    /**
     * 写-空值字段
     *
     * @param props
     */
    public void setNullValueProps(List<String> props);

    /**
     * 加-空值字段
     * @param props
     */
    public void addNullValueProps(String... props);
}
