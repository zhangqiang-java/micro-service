package com.zq.cloud.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 动态路由配置
 */
@Data
@ConfigurationProperties(prefix = "zq.dynamic")
public class DynamicRouteProperties {


    /**
     * 动态路由开关
     */
    private Boolean dynamicEnable = true;

    /**
     * 路由配置的id
     */
    private String dataId = "gateway-route";

    /**
     * 配置的group
     */
    private String group = "DEFAULT_GROUP";

}
