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
public enum DispStyle {
    PLAIN_TEXT("PLAIN_TEXT", "sys-notify:enum.dispStyle.plainText"),
    TEXT_CARD("TEXT_CARD", "sys-notify:enum.dispStyle.textCard"),
    IMAGE_CARD("IMAGE_CARD", "sys-notify:enum.dispStyle.imageCard"),
    ;

    @Getter
    private String value;
    @Getter
    private String messageKey;
}
