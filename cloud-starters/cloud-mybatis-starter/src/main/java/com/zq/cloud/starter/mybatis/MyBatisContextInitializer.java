package com.zq.cloud.starter.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;

@Slf4j
public class MyBatisContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext>, Ordered {
    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment();
        HashMap<String, Object> map = new HashMap<>();
        map.put("mybatis.mapper-locations", "classpath*:mybatis/*.xml");
        map.put("mybatis.configuration.map-underscore-to-camel-case", "true");
        map.put("zds.cloud.mysql.url.params", "useSSL=false&useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull&serverTimezone=Asia%2FShanghai&allowMultiQueries=true");
        map.put("spring.datasource.druid.initialSize", 10);
        map.put("spring.datasource.druid.minIdle", 1);
        map.put("spring.datasource.druid.maxActive", 100);
        map.put("spring.datasource.druid.maxWait", 10000);
        map.put("spring.datasource.druid.timeBetweenEvictionRunsMillis", 60000);
        map.put("spring.datasource.druid.minEvictableIdleTimeMillis", 300000);
        map.put("spring.datasource.druid.testWhileIdle", true);
        map.put("spring.datasource.druid.testOnBorrow", false);
        map.put("spring.datasource.druid.testOnReturn", false);
        map.put("spring.datasource.druid.defaultAutoCommit", true);
        map.put("spring.datasource.druid.validationQuery", "select 1");
        map.put("spring.datasource.druid.filters", "stat");
        map.put("spring.datasource.druid.filter.stat.log-slow-sql", true);
        map.put("spring.datasource.druid.filter.stat.merge-sql", true);
        map.put("spring.datasource.druid.filter.stat.slow-sql-millis", 2000);

        MapPropertySource propertySource = new MapPropertySource("MyBatisContextInitializer", map);
        environment.getPropertySources().addLast(propertySource);
        log.debug("初始化mybatis参数：{},{}", propertySource, map);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
