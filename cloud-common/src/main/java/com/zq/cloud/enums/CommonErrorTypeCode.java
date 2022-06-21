package com.zq.cloud.enums;


import com.zq.cloud.constant.StaticFinalConstant;


import java.io.Serializable;


public enum CommonErrorTypeCode implements Serializable {
    BUSINESS_ERROR("001", "业务异常", new String[]{}),

    DB_ERROR("002", "数据库错误", new String[]{StaticFinalConstant.BAD_SQL_GRAMMAR_EXCEPTION,
            StaticFinalConstant.DATA_INTEGRITY_VIOLATION_EXCEPTION,
            StaticFinalConstant.DUPLICATE_KEY_EXCEPTION,
            StaticFinalConstant.MYBATIS_SYSTEM_EXCEPTION}),

    IO_ERROR("003", "IO异常", new String[]{StaticFinalConstant.IO_EXCEPTION}),

    NET_ERROR("004", "网络异常", new String[]{StaticFinalConstant.CONNECT_EXCEPTION, StaticFinalConstant.SOCKET_EXCEPTION}),

    INVALID_PARAM("005", "请求参数非法", new String[]{StaticFinalConstant.ILLEGAL_ARGUMENT_EXCEPTION,
            StaticFinalConstant.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION,
            StaticFinalConstant.METHOD_ARGUMENT_NOT_VALID_EXCEPTION, StaticFinalConstant.VALIDATION_EXCEPTION,
            StaticFinalConstant.CONSTRAINT_VIOLATION_EXCEPTION, StaticFinalConstant.MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION}),

    TIMEOUT("006", "请求超时", new String[]{StaticFinalConstant.SOCKET_TIMEOUT_EXCEPTION}),

    UNKNOWN_ERROR("007", "未知异常", new String[]{}),
    ;


    private String code;

    private String message;

    private String[] errorClassArray;


    CommonErrorTypeCode(String code, String message, String[] errorClassArray) {
        this.code = code;
        this.message = message;
        this.errorClassArray = errorClassArray;
    }


    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public String[] getErrorClassArray() {
        return this.errorClassArray;
    }
}
