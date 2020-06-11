package indi.rui.study.websocket.common;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Id;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: yaowr
 * @create: 2020-06-10
 */
public interface IData {
    Long getFdId();

    void setFdId(Long fdId);

    ConcurrentHashMap<String, Object> getDynamicProps();

    void setDynamicProps(ConcurrentHashMap<String, Object> dynamicProps);
}
