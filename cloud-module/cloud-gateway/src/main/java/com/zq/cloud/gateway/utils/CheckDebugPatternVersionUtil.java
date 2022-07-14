package com.zq.cloud.gateway.utils;

import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.naming.utils.NamingUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CheckDebugPatternVersionUtil {

    /**
     * 注册进来的调试服务
     */
    public final static ConcurrentHashMap<String, List<String>> DEBUG_PATTERN_VERSION_MAP = new ConcurrentHashMap<>();

    /**
     * 已经订阅了的服务名称
     */
    public final static Set<String> SUBSCRIBE_SERVER_NAME_SET = Collections.synchronizedSet(new HashSet<>());


    public static boolean checkDebugPatternVersionMap(String debugPatternVersion) {
        return DEBUG_PATTERN_VERSION_MAP.values().stream().flatMap(Collection::stream).collect(Collectors.toList()).contains(debugPatternVersion);
    }

    public static boolean checkSubscribe(String serverId){
        return SUBSCRIBE_SERVER_NAME_SET.contains( NamingUtils.getGroupedName(serverId, Constants.DEFAULT_GROUP));
    }
}
