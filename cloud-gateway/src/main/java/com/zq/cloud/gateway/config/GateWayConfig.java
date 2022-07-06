package com.zq.cloud.gateway.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zq.cloud.gateway.route.DynamicRouteDefinitionRepository;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@EnableConfigurationProperties(DynamicGatewayProperties.class)
@Configuration
public class GateWayConfig {

    @Bean
    @ConditionalOnProperty(value = "zq.dynamic.routeEnable", matchIfMissing = true)
    public DynamicRouteDefinitionRepository dynamicRouteDefinitionRepository(NacosConfigManager nacosConfigManager,
                                                                             DynamicGatewayProperties dynamicGatewayProperties,
                                                                             ApplicationEventPublisher publisher) {
        return new DynamicRouteDefinitionRepository(nacosConfigManager, dynamicGatewayProperties, publisher);
    }


    /**
     * gateway pom文件的是spring-boot-starter-webflux，而springboot用的是spring-boot-starter-web 加入转换器
     * @param converters
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        List<HttpMessageConverter<?>> messageConverters = converters.orderedStream().collect(Collectors.toList());
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleDateFormat smt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        objectMapper.setDateFormat(smt);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mappingJackson2HttpMessageConverter.setObjectMapper(objectMapper);
        //设置中文编码格式
        List<MediaType> list = new ArrayList<>();
        list.add(MediaType.APPLICATION_JSON);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(list);
        messageConverters.add(mappingJackson2HttpMessageConverter);
        return new HttpMessageConverters(messageConverters);
    }
}
