package indi.rui.study.bgytest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2021-10-25
 */
@Getter
@Setter
public class QueryResult<V> {

    /**
     * 返回内容
     */
    private List<V> content;

    /**
     * 分页位移量
     */
    private int offset;

    /**
     * 每页数目
     */
    private int pageSize;

    /**
     * 总数
     */
    private long totalSize;
}
