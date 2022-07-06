package com.zq.cloud.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 动态路由配置
 */
@Data
@ConfigurationProperties(prefix = "zq.gateway.dynamic")
public class DynamicGatewayProperties {


    /**
     * 动态路由开关
     */
    private Boolean routeEnable = true;

    /**
     * 路由配置的id
     */
    private String routeDataId = "gateway-route";

    /**
     * 配置的group
     */
    private String group = "DEFAULT_GROUP";

    /**
     * 限流配置id
     */
    private String flowLimitDateId = "gateway-flow-limit";


    /**
     * 熔断降级配置id
     */
    private String degradeDateId = "gateway-degrade";

}
