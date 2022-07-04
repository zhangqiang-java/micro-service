package com.zq.cloud.starter.nacos.discovery.config;

import com.zq.cloud.starter.nacos.discovery.util.DebugPatternUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * 从请求上下文中 获取顺序 cook  header parameter
 */
public class DebugPatternFromHttpServletRequest {

    public String getDebugPatternVersionByRequestContextHolder() {
        String debugPatternVersion = null;
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                debugPatternVersion = Arrays.stream(cookies).filter(cookie ->
                        DebugPatternUtil.getDebugPatternKey().equals(cookie.getName()))
                        .map(Cookie::getValue).findFirst().orElse(null);
            }
            if (StringUtils.hasText(debugPatternVersion)) {
                return debugPatternVersion;
            }
            debugPatternVersion = request.getHeader(DebugPatternUtil.getDebugPatternKey());

            if (StringUtils.hasText(debugPatternVersion)) {
                return debugPatternVersion;
            }

            debugPatternVersion = request.getParameter(DebugPatternUtil.getDebugPatternKey());

        }
        return debugPatternVersion;
    }

}
