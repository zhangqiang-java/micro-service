package com.zq.cloud.starter.nacos.discovery.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 配置
 */
@Data
@ConfigurationProperties("spring.cloud.nacos.discovery.zq")
public class ZqNacosDiscoveryProperties {

    private DebugPattern debugPattern = new DebugPattern();

    @Data
    public static class DebugPattern {
        /**
         * 是否开启调试模式
         */
        private Boolean enabled = true;

        /**
         * 调试的版本
         */
        private String Key = "debugVersion";
    }
}
