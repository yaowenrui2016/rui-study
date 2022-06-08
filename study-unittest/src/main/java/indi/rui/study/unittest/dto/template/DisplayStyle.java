package indi.rui.study.unittest.dto.template;

import indi.rui.study.unittest.constant.DispStyle;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author: yaowr
 * @create: 2022-06-06
 */
@Getter
@Setter
public class DisplayStyle {

    /**
     * 展现样式
     */
    private DispStyle style;

    /**
     * 展现字段
     */
    private List<DisplayProperty> dispProps;
}
