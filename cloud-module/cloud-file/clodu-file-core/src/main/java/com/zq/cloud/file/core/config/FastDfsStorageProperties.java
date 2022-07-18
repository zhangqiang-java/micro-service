package com.zq.cloud.file.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "zq.file.fastDfs")
public class FastDfsStorageProperties {
    /**
     * fastDfs存储 group
     */
    private String groupName = "group";
}
