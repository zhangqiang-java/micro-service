package com.zq.cloud.file.dal.enums;

import java.io.Serializable;

/**
 * 存储来源
 */
public enum StorageSourceType implements Serializable {


    OSS("OOS", "阿里云存储"),

    FAST_DFS("FAST_DFS", "fastDfs存储"),

    LOCAL("LOCAL", "本地存储");


    private String code;

    private String message;

    StorageSourceType(String code, String message) {
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
