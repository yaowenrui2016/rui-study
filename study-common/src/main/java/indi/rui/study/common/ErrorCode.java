package indi.rui.study.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: yaowr
 * @create: 2020-06-09
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {
    SUCCESS("ok", "操作成功"),
    FAILED("failed", "操作失败"),
    ;

    private String code;
    private String msg;
}
