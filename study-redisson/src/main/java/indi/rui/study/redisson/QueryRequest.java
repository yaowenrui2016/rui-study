package indi.rui.study.redisson;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2020-02-01
 */
@Getter
@Setter
public class QueryRequest {
    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int DEFAULT_START_PAGE = 1;

    private int startPage = DEFAULT_START_PAGE;
    private int pageSize = DEFAULT_PAGE_SIZE;
    private Map<String, Object> conditions = new HashMap<>();

    public int getOffset() {
        startPage = startPage < 1 ? 1 : startPage;
        return (startPage - 1) * pageSize;
    }
}
