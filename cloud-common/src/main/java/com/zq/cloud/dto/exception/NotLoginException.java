package com.zq.cloud.dto.exception;

import com.zq.cloud.constant.StaticFinalConstant;
import lombok.Data;

/**
 * 未登录异常
 */
@Data
public class NotLoginException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    public NotLoginException() {
        super();
        this.code = StaticFinalConstant.NOT_LOGIN_CODE;
        this.message = "用户未登录或已过期,请重新登录";
    }
}
