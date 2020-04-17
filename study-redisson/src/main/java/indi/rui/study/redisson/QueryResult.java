package indi.rui.study.redisson;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-02-02
 */
@Getter
@Setter
public class QueryResult<T> {
    private int startPage;
    private int pageSize;
    private int total;
    private List<T> content;

    public static QueryResult of(QueryRequest request, int total, List<Person> content) {
        QueryResult result = new QueryResult();
        result.setStartPage(request.getStartPage());
        result.setPageSize(request.getPageSize());
        result.setTotal(total);
        result.setContent(content);
        return result;
    }
}
