package indi.rui.study.websocket.common.query;

import indi.rui.study.websocket.common.dto.IVO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2020-06-09
 */
@Getter
@Setter
public class QueryResult<T extends IVO> extends QueryRequest {
    private int totalSize;
    private List<T> content;

    public static QueryResult of(QueryRequest request, int total, List<? extends IVO> content) {
        QueryResult result = new QueryResult();
        BeanUtils.copyProperties(request, result);
        result.setTotalSize(total);
        result.setContent(content);
        return result;
    }
}
