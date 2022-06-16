package com.zq.cloud.starter.nacos.discovery;

import com.zq.cloud.starter.nacos.config.DebugPatternFromHttpServletRequest;
import com.zq.cloud.starter.nacos.config.ZqNacosDiscoveryProperties;
import com.zq.cloud.starter.nacos.config.CustomNacosRibbonClientConfiguration;
import com.zq.cloud.starter.nacos.util.DebugPatternUtil;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Configuration
@EnableConfigurationProperties(ZqNacosDiscoveryProperties.class)
public class NacosDiscoveryAutoConfiguration {

    @Bean
    public DebugPatternUtil debugPatternUtil() {
        return new DebugPatternUtil();
    }

    @RibbonClients(defaultConfiguration = CustomNacosRibbonClientConfiguration.class)
    @ConditionalOnProperty(name = "spring.cloud.nacos.discovery.zq.debugPattern.enabled", matchIfMissing = true)
    public static class RibbonNacosAutoConfiguration {

    }


    @Configuration
    @ConditionalOnClass({RequestContextHolder.class, HttpServletRequest.class})
    public static class debugPatternFromHttpServletRequestAutoConfiguration {
        @Bean
        public DebugPatternFromHttpServletRequest debugPatternFromHttpServletRequest() {
            return new DebugPatternFromHttpServletRequest();
        }
    }


    @Bean
    public OpenFeignDebugPatternRequestInterceptor debugPatternVersionOpenFeignTransferInterceptor() {
        return new OpenFeignDebugPatternRequestInterceptor();
    }

    public static class OpenFeignDebugPatternRequestInterceptor implements RequestInterceptor {

        /**
         * openFeign 调用透传调试版本
         *
         * @param template
         */
        @Override
        public void apply(RequestTemplate template) {
            template.header(DebugPatternUtil.getDebugPatternKey(), DebugPatternUtil.getDebugPatternVersion());
        }
    }
}
