package com.zq.cloud.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JacksonUtils {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        OBJECT_MAPPER.registerModule(module);
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String toJsonString(Object data) {
        try {
            return OBJECT_MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            log.error("json转换异常：{}", data);
            return null;
        }
    }

    public static <T> T fromJson(String jsonData, Class<T> beanType) {
        try {
            return OBJECT_MAPPER.readValue(jsonData, beanType);
        } catch (Exception e) {
            log.error("json转换异常：{}", jsonData);
            return null;
        }
    }

    public static <T> List<T> parseList(String jsonData, Class<T> beanClass) {
        try {
            CollectionType collectionType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, beanClass);
            return OBJECT_MAPPER.readValue(jsonData, collectionType);
        } catch (Exception e) {
            log.error("json转换异常：{}", jsonData, e);
            return null;
        }
    }

    public static <K, V> Map<K, V> parseMap(String jsonData, Class<K> keyClass, Class<V> valueClass) {
        try {
            MapType mapType = OBJECT_MAPPER.getTypeFactory().constructMapType(HashMap.class, keyClass, valueClass);
            return OBJECT_MAPPER.readValue(jsonData, mapType);
        } catch (Exception e) {
            log.error("json转换异常：{}", jsonData, e);
            return null;
        }
    }

    public static <T> T parseObjectByType(String jsonData, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(jsonData, typeReference);
        } catch (Exception e) {
            log.error("json转换异常：{}", jsonData, e);
            return null;
        }
    }

    public static ObjectMapper getObjectMapper() {
        return OBJECT_MAPPER;
    }

}

