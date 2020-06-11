package indi.rui.study.websocket.common.query;

import indi.rui.study.websocket.common.dto.Condition;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: yaowr
 * @create: 2020-06-09
 */
@Getter
@Setter
public class QueryRequest {
    /**
     * 默认每页条数
     */
    private static final int DEFAULT_PAGE_SIZE = 20;
    /**
     * 默认起始页
     */
    private static final int DEFAULT_PAGE_NUM = 1;

    private int pageSize = DEFAULT_PAGE_SIZE;
    private int pageNum = DEFAULT_PAGE_NUM;
    private List<Condition> conditions = new ArrayList<>();
    private Map<String, String> sorts = new HashMap<>();

    public int getOffset() {
        pageNum = Math.max(1, pageNum);
        return (pageNum - 1) * pageSize;
    }
}
