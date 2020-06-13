package indi.rui.study.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: yaowr
 * @create: 2020-01-31
 */
public class AbstractVO implements IVO {
    private Long fdId;
    @JsonIgnore
    private List<String> nullValueProps;
    @JsonIgnore
    private ConcurrentHashMap dynamicProps;


    @Override
    public List<String> getNullValueProps() {
        return nullValueProps;
    }

    @Override
    public void setNullValueProps(List<String> nullValueProps) {
        this.nullValueProps = nullValueProps;
    }

    @Override
    public void addNullValueProps(String... props) {
        if (Objects.isNull(nullValueProps)) {
            nullValueProps = new ArrayList<>();
        }
        for (String prop : props) {
            nullValueProps.add(prop);
        }
    }

    @Override
    public Long getFdId() {
        return fdId;
    }

    @Override
    public void setFdId(Long fdId) {
        this.fdId = fdId;
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
