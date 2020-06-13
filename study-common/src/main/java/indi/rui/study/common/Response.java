package indi.rui.study.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

/**
 * @author: yaowr
 * @create: 2020-06-09
 */
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private String code;
    private String msg;
    private T data;

    public Response(ErrorCode errorCode, T data) {
        this.code = errorCode.getCode();
        this.msg = errorCode.getMsg();
        this.data = data;
    }

    public static <T> Response ok(T data) {
        return new Response(ErrorCode.SUCCESS, data);
    }

    public static <T> Response ok() {
        return ok(null);
    }
}
