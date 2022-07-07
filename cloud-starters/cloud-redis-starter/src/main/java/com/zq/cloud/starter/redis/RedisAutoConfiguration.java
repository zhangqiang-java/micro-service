package com.zq.cloud.starter.redis;

import com.zq.cloud.starter.redis.serializer.CustomKeyRedisSerializer;
import com.zq.cloud.starter.redis.serializer.KryoRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisAutoConfiguration {

    @Value("${spring.application.name}")
    private String applicationName;

    @Autowired
    private RedisProperties redisProperties;

    /**
     * 用Kryo value替换默认的jdk序列话  key序列话时添加自定义前缀 做服务之间数据隔离
     *
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(new CustomKeyRedisSerializer(getRedisKeyPrefix()));
        template.setHashKeySerializer(RedisSerializer.string());
        template.setValueSerializer(new KryoRedisSerializer());
        template.setHashValueSerializer(new KryoRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setKeySerializer(new CustomKeyRedisSerializer(getRedisKeyPrefix()));
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    private String getRedisKeyPrefix() {
        String keyPrefix = redisProperties.getKeyPrefix();
        if (keyPrefix == null || keyPrefix.trim().length() == 0) {
            keyPrefix = applicationName;
        }
        return keyPrefix.toUpperCase().replaceAll("-", "_");
    }

}
