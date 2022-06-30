package com.zq.cloud.starter.web.aop;

import com.zq.cloud.starter.web.config.ControllerAopLogProperties;
import com.zq.cloud.starter.web.utils.RealIpAddressUtil;
import com.zq.cloud.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日志打印aop
 */
@Slf4j
@Aspect
public class ControllerLogAop {

    @Autowired
    private ControllerAopLogProperties properties;


    @Pointcut(value = "(@target(org.springframework.stereotype.Controller) || @target(org.springframework.web.bind.annotation.RestController))" +
            " && within(com.zq..*)" +
            " && execution(public * *(..))")
    public void logAround() {
    }


    @Around("logAround()")
    public Object logAround(ProceedingJoinPoint pjp) throws Throwable {
        long startTimeMillis = System.currentTimeMillis();

        //记录请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String requestUrl = request.getRequestURL().toString();
        String realIpAddress = RealIpAddressUtil.getIpAddress(request);
        String params = JacksonUtils.toJsonString(this.excludeFrameParameters(pjp));
        log.info("请求来源:{},地址：{}，入参：{}...", realIpAddress, requestUrl, StringUtils.left(params, properties.getLogLength()));

        Object result = pjp.proceed();

        //记录响应日志
        if (result != null && !(result instanceof ResponseEntity)) {
            String resultStr = JacksonUtils.toJsonString(result);
            log.info("响应：{}，出参：{}，耗时{}ms", requestUrl, StringUtils.left(resultStr, properties.getLogLength()), (System.currentTimeMillis() - startTimeMillis));
        } else {
            log.info("响应{}，出参为空或是文件，耗时{}ms", requestUrl, (System.currentTimeMillis() - startTimeMillis));
        }
        return result;
    }


    /**
     * 排除框架自带参数
     *
     * @param pjp
     * @return
     */
    private List<Object> excludeFrameParameters(ProceedingJoinPoint pjp) {
        return Arrays.stream(pjp.getArgs()).filter(arg -> !isFrameParameters(arg)).collect(Collectors.toList());
    }


    /**
     * 排除框架自带参数 未穷举
     *
     * @param arg
     * @return
     */
    private Boolean isFrameParameters(Object arg) {
        return arg instanceof HttpServletRequest
                || arg instanceof HttpServletResponse
                || arg instanceof HttpSession
                || arg instanceof MultipartFile
                || arg instanceof Model;
    }
}
