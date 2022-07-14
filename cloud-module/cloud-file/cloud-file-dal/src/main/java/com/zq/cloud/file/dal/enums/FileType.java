package com.zq.cloud.file.dal.enums;

public enum FileType {

    IMAGE("IMAGE", "图片"),

    VIDEO("VIDEO", "视频"),

    AUDIO("AUDIO", "音频"),

    DOC("DOC", "文档"),


    OTHER("OTHER", "其他");


    private String code;

    private String message;

    FileType(String code, String message) {
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
