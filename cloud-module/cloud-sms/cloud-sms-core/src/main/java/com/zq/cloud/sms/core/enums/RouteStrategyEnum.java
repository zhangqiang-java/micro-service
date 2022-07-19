package com.zq.cloud.sms.core.enums;

import java.io.Serializable;

/**
 * 短信服务平台选择
 */
public enum RouteStrategyEnum implements Serializable {

    RANDOM(0, "随机"),
    POLLING(1, "轮询"),
    DESIGNATED(2, "指定");


    private final Integer code;
    private final String message;

    RouteStrategyEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    public Integer code() {
        return code;
    }


    public String message() {
        return message;
    }
}
