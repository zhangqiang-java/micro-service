package com.zq.cloud.enums;

import java.io.Serializable;

public enum CommonErrorTypeCode implements Serializable {
    BUSINESS_ERROR("001", "业务异常"),

    DB_ERROR("002", "数据库错误"),

    IO_ERROR("003", "IO异常"),

    NET_ERROR("004", "网络异常"),

    TRANSACTION_ERROR("005", "事务异常"),

    INVALID_PARAM("006", "请求参数非法"),

    PARAM_FORMAT_ERROR("007", "参数格式错误"),

    REQUEST_REPEATED("008", "重复的请求"),

    TIMEOUT("009", "请求超时"),

    UNKNOWN_ERROR("010", "未知异常"),
    ;


    private String code;

    private String message;


    CommonErrorTypeCode(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

}
