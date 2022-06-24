package com.zq.cloud.gateway.utils;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CheckDebugPatternVersionUtil {


    public final static ConcurrentHashMap<String, List<String>> DEBUG_PATTERN_VERSION_MAP = new ConcurrentHashMap<>();


    public static boolean checkDebugPatternVersionMap(String debugPatternVersion) {
        return DEBUG_PATTERN_VERSION_MAP.values().stream().flatMap(Collection::stream).collect(Collectors.toList()).contains(debugPatternVersion);
    }
}
