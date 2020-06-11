package indi.rui.study.websocket.common.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
@MappedSuperclass
public abstract class AbstractEntity implements IEntity {

    @Id
    @Access(AccessType.PROPERTY)
    private Long fdId;

    @Transient
    private ConcurrentHashMap dynamicProps;


    @Override
    public Long getFdId() {
        return fdId;
    }

    @Override
    public void setFdId(Long fdId) {
        this.fdId = fdId;
    }

    public String getFdStatus() {
        return (String) getDynamicProps().get("fdCreator");
    }

    public void setFdStatus(String creator) {
        getDynamicProps().put("fdCreator", creator);
    }


    @Override
    public ConcurrentHashMap<String, Object> getDynamicProps() {
        if (Objects.isNull(dynamicProps)) {
            dynamicProps = new ConcurrentHashMap();
        }
        return dynamicProps;
    }

    @Override
    public void setDynamicProps(ConcurrentHashMap<String, Object> dynamicProps) {
        this.dynamicProps = dynamicProps;
    }
}
