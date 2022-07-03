package com.zq.cloud.starter.web.aop;

import com.zq.cloud.constant.StaticFinalConstant;
import com.zq.cloud.dto.exception.BusinessException;
import com.zq.cloud.dto.result.ResultError;
import com.zq.cloud.enums.CommonErrorTypeCode;
import com.zq.cloud.utils.ErrorCodeUtil;
import com.zq.cloud.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 通用异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public Object handleException(BusinessException ex, HttpServletRequest request, HttpServletResponse response) {
        log.warn("业务异常：{}", ex.getMessage());
         ResultError<Void> error = ResultError.error(ex.getCode(), ex.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(Objects.requireNonNull(JacksonUtils.toJsonString(error)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response) {
        // String errorCode = "";
        //
        // CommonErrorTypeCode[] values = CommonErrorTypeCode.values();
        // for (CommonErrorTypeCode errorTypeCode : values) {
        //     if (StringUtils.equalsAny(ex.getClass().getName(), errorTypeCode.getErrorClassArray())) {
        //         errorCode = ErrorCodeUtil.crateErrorCode(errorTypeCode);
        //         break;
        //     }
        // }
        // //未知异常
        // if (StringUtils.isBlank(errorCode)) {
        //     errorCode = ErrorCodeUtil.crateErrorCode(CommonErrorTypeCode.UNKNOWN_ERROR);
        // }
        // return ResultError.error(errorCode, StaticFinalConstant.OPEN_ERROR_MESSAGE);

        log.warn("业务异常：{}", ex.getMessage());
        ResultError<Void> error = ResultError.error("ex.getCode()", ex.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(Objects.requireNonNull(JacksonUtils.toJsonString(error)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
