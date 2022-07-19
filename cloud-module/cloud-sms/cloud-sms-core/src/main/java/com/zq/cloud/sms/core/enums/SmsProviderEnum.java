package com.zq.cloud.sms.core.enums;

import java.io.Serializable;

/**
 * 服务提供方枚举
 */
public enum SmsProviderEnum implements Serializable {


    ALI_YUN("aliYun", "aliYunSmsProvider", "阿里云短信服务"),
    TENCENT("tencent", "tencentSmsProvider", "腾讯云短信服务"),
    ;


    private final String code;
    private final String serviceCode;
    private final String message;


    SmsProviderEnum(String code, String serviceCode, String message) {

        this.code = code;
        this.message = message;
        this.serviceCode = serviceCode;
    }


    public String code() {
        return code;
    }


    public String message() {
        return message;
    }

    public String serviceCode() {
        return serviceCode;
    }
}
