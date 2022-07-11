package com.zq.cloud.authuser.dal.enums;


import java.io.Serializable;

/**
 * 资源类型接口
 */
public enum ResourceTypeEnum implements Serializable {
    MENU("MENU", "菜单"),
    INTERFACE("INTERFACE", "接口"),
    ;


    private final String code;
    private final String message;

    ResourceTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String code() {
        return code;
    }


    public String message() {
        return message;
    }
}
