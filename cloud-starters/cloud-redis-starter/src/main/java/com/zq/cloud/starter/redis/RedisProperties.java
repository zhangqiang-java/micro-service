package com.zq.cloud.starter.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "zq.cloud.redis")
public class RedisProperties {

    /**
     * 自定义前缀（用于不同项目隔离）
     */
    private String keyPrefix;
}
