package com.zq.cloud.starter.web.interceptor;

import com.zq.cloud.constant.CommonStaticFinalConstant;
import com.zq.cloud.starter.web.context.UserContext;
import com.zq.cloud.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class UserContextHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserContext userContext = UserContext.get();
        String userInfo = request.getHeader(CommonStaticFinalConstant.USER_INFO_HEADER_NAME);
        if (StringUtils.hasText(userInfo)) {
            userInfo = URLDecoder.decode(userInfo, StandardCharsets.UTF_8.name());
            userContext.setUser(JacksonUtils.fromJson(userInfo, UserContext.CurrentUser.class));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserContext.clear();
    }
}
