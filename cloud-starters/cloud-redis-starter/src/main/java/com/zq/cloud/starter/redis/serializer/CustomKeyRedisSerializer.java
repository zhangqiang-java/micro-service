package com.zq.cloud.starter.redis.serializer;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 自定义key序列化  参考 StringRedisSerializer
 */
public class CustomKeyRedisSerializer implements RedisSerializer<String> {
    private final Charset charset;
    private final String keyPrefix;

    public CustomKeyRedisSerializer() {
        this(StandardCharsets.UTF_8, null);
    }

    public CustomKeyRedisSerializer(String keyPrefix) {
        this(StandardCharsets.UTF_8, keyPrefix);
    }

    public CustomKeyRedisSerializer(Charset charset, String keyPrefix) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
        this.keyPrefix = keyPrefix;
    }


    @Override
    public String deserialize(@Nullable byte[] bytes) {
        return (bytes == null ? null : new String(bytes, charset));
    }


    @Override
    public byte[] serialize(@Nullable String redisKey) {
        if (StringUtils.isEmpty(redisKey)) {
            return null;
        }
        if (StringUtils.isEmpty(keyPrefix) || redisKey.startsWith(keyPrefix)) {
            return redisKey.getBytes(charset);
        }

        return (keyPrefix + redisKey).getBytes(charset);
    }

    @Override
    public Class<?> getTargetType() {
        return String.class;
    }


}
