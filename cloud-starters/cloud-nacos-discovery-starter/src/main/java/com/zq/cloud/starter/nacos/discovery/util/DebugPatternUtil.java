package com.zq.cloud.starter.nacos.discovery.util;

import com.zq.cloud.starter.nacos.discovery.config.DebugPatternFromHttpServletRequest;
import com.zq.cloud.starter.nacos.discovery.config.DebugPatternFromCustomizeContext;
import com.zq.cloud.starter.nacos.discovery.config.ZqNacosDiscoveryProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * 获取 DebugPatternVersion 工具类
 */
public class DebugPatternUtil {

    private static ZqNacosDiscoveryProperties properties;

    private static DebugPatternFromHttpServletRequest debugPatternFromHttpServletRequest;

    @Autowired(required = false)
    public void setGreyVersionFromHttpServletRequest(DebugPatternFromHttpServletRequest debugPatternFromHttpServletRequest) {
        DebugPatternUtil.debugPatternFromHttpServletRequest = debugPatternFromHttpServletRequest;
    }

    @Autowired
    public void setNacosDiscoveryZdsProperties(ZqNacosDiscoveryProperties nacosDiscoveryZdsProperties) {
        DebugPatternUtil.properties = nacosDiscoveryZdsProperties;
    }


    public static String getDebugPatternKey() {
        return properties.getDebugPattern().getKey();
    }

    public static String getDebugPatternVersion() {
        String greyVersion = DebugPatternFromCustomizeContext.getDebugPatternVersion();
        if (!StringUtils.hasText(greyVersion) && debugPatternFromHttpServletRequest != null) {
            greyVersion = debugPatternFromHttpServletRequest.getDebugPatternVersionByRequestContextHolder();
        }
        return greyVersion;
    }



}
