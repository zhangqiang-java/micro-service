package com.zq.cloud.file.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("zq.file.storage")
public class FileProperties {

    /**
     * 文件服务提供方  目前支持fastDfs，阿里云oss，本地存储，
     */
    private String provider;

    private String previewDomainName;
}
