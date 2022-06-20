package com.zq.cloud.starter.nacos.config;

import com.alibaba.cloud.nacos.ribbon.NacosRule;
import com.netflix.loadbalancer.IRule;
import com.zq.cloud.starter.nacos.rule.DebugPatternNacosRule;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置负载均衡策略
 */
@Data
@Configuration()
@RefreshScope
public class CustomNacosRibbonClientConfiguration {

    @Autowired
    private ZqNacosDiscoveryProperties discoveryProperties;


    @Bean
    @RefreshScope
    public IRule getRibbonRule() {
        if (discoveryProperties.getDebugPattern().getEnabled()) {
            return new DebugPatternNacosRule();
        }
        //todo 自定义切换多种负载均衡策略
        return new NacosRule();
    }

}