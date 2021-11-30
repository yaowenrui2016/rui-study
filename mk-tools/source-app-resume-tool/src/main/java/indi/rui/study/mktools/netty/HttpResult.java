package indi.rui.study.mktools.netty;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
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
    private Map<String, List<String>> headers;
    private String content;
}
