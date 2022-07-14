package com.zq.cloud.file.core.enums;

/**
 * 服务提供者
 */
public enum StorageProviderType {

    OSS("oss", "阿里云服务oss存储"),
    LOCAL("local", "本地存储"),
    FAST_DFS("fastDfs", "fastDfs存储");


    private final String code;
    private final String message;

    StorageProviderType(String code, String message) {
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
