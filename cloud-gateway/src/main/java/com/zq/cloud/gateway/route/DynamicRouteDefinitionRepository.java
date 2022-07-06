package com.zq.cloud.gateway.route;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.common.utils.JacksonUtils;
import com.alibaba.nacos.common.utils.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.zq.cloud.gateway.config.DynamicGatewayProperties;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 动态路由
 */
@Slf4j
@NoArgsConstructor
public class DynamicRouteDefinitionRepository implements RouteDefinitionRepository {

    private DynamicGatewayProperties dynamicGatewayProperties;

    private ApplicationEventPublisher publisher;

    private ConfigService configService;

    public DynamicRouteDefinitionRepository(NacosConfigManager nacosConfigManager, DynamicGatewayProperties dynamicGatewayProperties,
                                            ApplicationEventPublisher publisher) {
        this.dynamicGatewayProperties = dynamicGatewayProperties;
        this.configService = nacosConfigManager.getConfigService();
        this.publisher = publisher;
        this.configService = nacosConfigManager.getConfigService();
        addRouteChangeListener();
    }


    /**
     * 添加nacos 路由配置变化监听
     */
    private void addRouteChangeListener() {
        try {
            configService.addListener(dynamicGatewayProperties.getRouteDataId(), dynamicGatewayProperties.getGroup(),
                    new Listener() {

                //配置更新发布 route刷新事件
                @Override
                public void receiveConfigInfo(String configInfo) {
                    publisher.publishEvent(new RefreshRoutesEvent(this));
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("动态路由刷新异常", e);
        }
    }


    /**
     * 从nacos中获取最新配置
     *
     * @return
     */
    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        try {
            final String config = configService.getConfig(dynamicGatewayProperties.getRouteDataId(),
                    dynamicGatewayProperties.getGroup(), 5000);
            if (StringUtils.isNotBlank(config)){
                List<RouteDefinition> routeDefinitions = JacksonUtils.toObj(config, new TypeReference<List<RouteDefinition>>() {
                });
                return Flux.fromIterable(routeDefinitions);
            }
        } catch (NacosException e) {
            log.error("从nacos中获取路由配置信息异常", e);
        }
        
        return Flux.fromIterable(Collections.emptyList());
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }
}
