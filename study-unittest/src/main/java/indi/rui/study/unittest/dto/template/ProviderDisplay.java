package indi.rui.study.unittest.dto.template;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 消息扩展点呈现样式（以及待办扩展）
 *
 * @author: yaowr
 * @create: 2022-06-06
 */
@Getter
@Setter
public class ProviderDisplay {

    /**
     * 扩展点ID（消息扩展或待办扩展）
     */
    private String provider;

    /**
     * 扩展点名称多语言字段
     */
    private String messageKey;

    /**
     * 扩展点名称
     */
    private String name;

    /**
     * 是否是待办扩展
     */
    private boolean todoExt;

    /**
     * 呈现样式
     */
    private List<DisplayStyle> displayStyles;
}
