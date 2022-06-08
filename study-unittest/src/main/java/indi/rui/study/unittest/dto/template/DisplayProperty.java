package indi.rui.study.unittest.dto.template;

import indi.rui.study.unittest.constant.PropType;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 消息呈现
 *
 * @author: yaowr
 * @create: 2022-06-06
 */
@Getter
@Setter
public class DisplayProperty {

    /**
     * 属性名（也叫字段名或编码）
     *
     * @return
     */
    private String property;

    /**
     * 显示名
     *
     * @return
     */
    private String name;

    /**
     * 属性值
     */
    private String value;

    /**
     * 多语言属性值
     * 前端编辑模板内容使用
     */
    private Map<String, String> value4Lang;

    /**
     * 属性值类型
     */
    private PropType type;

    /**
     * 是否必填
     */
    private Boolean required;
}
