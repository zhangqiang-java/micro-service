package com.zq.cloud.enums;


import com.zq.cloud.constant.CommonStaticFinalConstant;


import java.io.Serializable;


public enum CommonErrorTypeCode implements Serializable {
    BUSINESS_ERROR("001", "业务异常", new String[]{}),

    DB_ERROR("002", "数据库错误", new String[]{CommonStaticFinalConstant.BAD_SQL_GRAMMAR_EXCEPTION,
            CommonStaticFinalConstant.DATA_INTEGRITY_VIOLATION_EXCEPTION,
            CommonStaticFinalConstant.DUPLICATE_KEY_EXCEPTION,
            CommonStaticFinalConstant.MYBATIS_SYSTEM_EXCEPTION}),

    IO_ERROR("003", "IO异常", new String[]{CommonStaticFinalConstant.IO_EXCEPTION}),

    NET_ERROR("004", "网络异常", new String[]{CommonStaticFinalConstant.CONNECT_EXCEPTION, CommonStaticFinalConstant.SOCKET_EXCEPTION}),

    INVALID_PARAM("005", "请求参数非法", new String[]{CommonStaticFinalConstant.ILLEGAL_ARGUMENT_EXCEPTION,
            CommonStaticFinalConstant.METHOD_ARGUMENT_TYPE_MISMATCH_EXCEPTION,
            CommonStaticFinalConstant.METHOD_ARGUMENT_NOT_VALID_EXCEPTION, CommonStaticFinalConstant.VALIDATION_EXCEPTION,
            CommonStaticFinalConstant.CONSTRAINT_VIOLATION_EXCEPTION, CommonStaticFinalConstant.MAX_UPLOAD_SIZE_EXCEEDED_EXCEPTION}),

    TIMEOUT("006", "请求超时", new String[]{CommonStaticFinalConstant.SOCKET_TIMEOUT_EXCEPTION}),
    SERVER_NOT_FOUND("007", "服务未发现", new String[]{CommonStaticFinalConstant.SERVER_NOT_FOUND_EXCEPTION}),
    UNKNOWN_ERROR("008", "未知异常", new String[]{}),
    BLOCK_EXCEPTION("009", "熔断限流异常", new String[]{}),
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
