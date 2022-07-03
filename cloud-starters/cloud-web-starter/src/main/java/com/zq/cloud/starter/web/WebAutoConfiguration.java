package com.zq.cloud.starter.web;

import com.zq.cloud.starter.web.aop.ControllerLogAop;
import com.zq.cloud.starter.web.config.ControllerAopLogProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableConfigurationProperties(ControllerAopLogProperties.class)
@ComponentScan(basePackageClasses = WebAutoConfiguration.class)
public class WebAutoConfiguration implements WebMvcConfigurer {

    @Bean
    @ConditionalOnProperty(value = "zq.cloud.web.log.enabled", matchIfMissing = true)
    public ControllerLogAop controllerLogAop() {
        return new ControllerLogAop();
    }
}
