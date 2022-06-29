package com.zq.cloud.gateway.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.zq.cloud.gateway.route.DynamicRouteDefinitionRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableConfigurationProperties(DynamicRouteProperties.class)
@Configuration
public class GateWayConfig {

    @Bean
    @ConditionalOnProperty(value = "zq.dynamic.dynamicEnable", matchIfMissing = true)
    public DynamicRouteDefinitionRepository dynamicRouteDefinitionRepository(NacosConfigManager nacosConfigManager,
                                                                             DynamicRouteProperties dynamicRouteProperties,
                                                                             ApplicationEventPublisher publisher) {
        return new DynamicRouteDefinitionRepository(nacosConfigManager, dynamicRouteProperties, publisher);
    }
}
