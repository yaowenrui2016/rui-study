package indi.rui.study.unittest.netty;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * @author: yaowr
 * @create: 2021-11-12
 */
@Getter
@Setter
public class HttpResult {
    private String version;
    private Integer status;
    private Map<String, Object> headers;
    private Object content;
}
