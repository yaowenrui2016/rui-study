package indi.rui.study.unittest.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: yaowr
 * @create: 2021-12-23
 */
@AllArgsConstructor
public enum PropType {
    SINGLE_LINE_TEXT("SINGLE_LINE_TEXT", "sys-notify:enum.showPropType.singleLineText"),
    MULTI_LINE_TEXT("MULTI_LINE_TEXT", "sys-notify:enum.showPropType.multiLineText"),
    RICH_TEXT("RICH_TEXT", "sys-notify:enum.showPropType.richText"),
    IMAGE("IMAGE", "sys-notify:enum.showPropType.image"),
    ;

    @Getter
    private String value;
    @Getter
    private String messageKey;
}
