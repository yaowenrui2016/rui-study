package indi.rui.study.unittest.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模板样式
 *
 * @author: yaowr
 * @create: 2021-12-23
 */
@AllArgsConstructor
public enum TemplateType {
    SYSTEM("SYSTEM", "sys-notify:enum.templateType.system"),
    CUSTOM("CUSTOM", "sys-notify:enum.templateType.custom"),
    PRIVATE("PRIVATE", "sys-notify:enum.templateType.private"),
    ;

    @Getter
    private String value;
    @Getter
    private String messageKey;

}
