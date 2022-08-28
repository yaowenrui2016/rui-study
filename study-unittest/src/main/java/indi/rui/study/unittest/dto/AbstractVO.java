package indi.rui.study.unittest.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

/**
 * 展现对象基类
 *
 * @author 叶中奇
 */
@Getter
@Setter
public abstract class AbstractVO {
    private String fdId;

    private Map<String, Object> extendProps;

    private Map<String, Object> dynamicProps;

    private Map<String, Object> mechanisms;

    private List<String> nullValueProps;
}
