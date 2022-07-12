package com.zq.cloud.starter.web;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zq.cloud.starter.web.aop.ControllerLogAop;
import com.zq.cloud.starter.web.config.ControllerAopLogProperties;
import com.zq.cloud.starter.web.interceptor.UserContextHandlerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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

    /**
     * Jackson全局转化long类型为String，解决jackson序列化时long类型缺失精度问题
     *
     * @return Jackson2ObjectMapperBuilderCustomizer 注入的对象
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
        };
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 增加UserContext相关拦截器
        registry.addInterceptor(new UserContextHandlerInterceptor())
                .addPathPatterns("/**").order(0);
    }
}
