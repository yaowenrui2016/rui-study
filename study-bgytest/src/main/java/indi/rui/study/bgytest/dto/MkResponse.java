package indi.rui.study.bgytest.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2021-10-25
 */
@Getter
@Setter
public class MkResponse<T> {
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 成功/错误的messageKey
     */
    private String code;
    /**
     * 成功/错误的提示信息
     */
    private String msg;
    /**
     * 数据
     */
    private T data;
    /**
     * 追踪ID
     */
    private String traceId;
}
