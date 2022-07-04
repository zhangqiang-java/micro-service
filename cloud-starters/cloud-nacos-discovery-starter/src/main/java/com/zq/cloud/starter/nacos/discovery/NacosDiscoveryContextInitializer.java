package com.zq.cloud.starter.nacos.discovery;

import com.netflix.client.config.CommonClientConfigKey;
import com.netflix.client.config.DefaultClientConfigImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;

@Slf4j
public class NacosDiscoveryContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {


    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
        HashMap<String, Object> map = new HashMap<>();
        // 设置服务消费方ribbon刷新服务列表的间隔时间。 ribbon.ServerListRefreshInterval默认30S
        // 详见com.netflix.ribbon->ribbon-loadbalancer中PollingServerListUpdater.refreshIntervalMs
        String consumerRibbonRefreshKey = DefaultClientConfigImpl.DEFAULT_PROPERTY_NAME_SPACE + "." + CommonClientConfigKey.ServerListRefreshInterval.key();
        map.put(consumerRibbonRefreshKey, 2000);

        MapPropertySource propertySource = new MapPropertySource("NacosDiscoveryContextInitializer", map);
        environment.getPropertySources().addLast(propertySource);
        log.debug("初始化nacosDiscovery参数：{},{}", propertySource, map);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
