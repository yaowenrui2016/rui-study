package indi.rui.study.websocket.common;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface IData {
    Long getFdId();

    void setFdId(Long fdId);

    ConcurrentHashMap<String, Object> getDynamicProps();

    void setDynamicProps(ConcurrentHashMap<String, Object> dynamicProps);
}
