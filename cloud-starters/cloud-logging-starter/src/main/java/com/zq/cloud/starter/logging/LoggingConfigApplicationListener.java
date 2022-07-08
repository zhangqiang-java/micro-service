package com.zq.cloud.starter.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;

@Slf4j
public class LoggingConfigApplicationListener implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
        HashMap<String, Object> map = new HashMap<>();
        String applicationName = environment.getProperty("spring.application.name");
        map.put("logging.file.path", "/var/log/webapps/" + applicationName);
        map.put("logging.file.name", "${logging.file.path}/" + applicationName);
        map.put("logging.file.max-history", "30");
        map.put("logging.file.max-size", "150MB");
        map.put("logging.level.com.alibaba.nacos", "warn");
        // 生产环境全保留由运维归档
        if (isOnlineOrPre(environment)) {
            map.put("logging.file.max-history", "0");
        }
        MapPropertySource propertySource = new MapPropertySource("LoggingConfigApplicationListener", map);
        environment.getPropertySources().addLast(propertySource);
        log.debug("初始化logging参数：{},{}", propertySource, map);
    }


    @Override
    public int getOrder() {
        return 0;
    }


    private boolean isOnlineOrPre(ConfigurableEnvironment environment) {
        String env = environment.getProperty("spring.profiles.active");
        if ("online".equalsIgnoreCase(env) || "pre".equalsIgnoreCase(env)) {
            return true;
        }
        return false;
    }

}
