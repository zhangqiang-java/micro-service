package com.zq.cloud.starter.nacos.config;

import org.springframework.core.NamedThreadLocal;
import org.springframework.lang.Nullable;

/**
 * 调试模式中的 Version维护
 */
public class DebugPatternFromCustomizeContext {

    private static final ThreadLocal<String> debugPatternVersionContextHolder =
            new NamedThreadLocal<>(" debugPatternVersion CustomizeContext");

    public static void setDebugPatternVersion(@Nullable String greyVersion) {
        debugPatternVersionContextHolder.set(greyVersion);
    }


    public static String getDebugPatternVersion() {
        return debugPatternVersionContextHolder.get();
    }


    public static void clear() {
        debugPatternVersionContextHolder.remove();
    }
}
