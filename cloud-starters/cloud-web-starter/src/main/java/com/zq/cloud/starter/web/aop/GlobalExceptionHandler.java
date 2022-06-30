package com.zq.cloud.starter.web.aop;

import com.zq.cloud.constant.StaticFinalConstant;
import com.zq.cloud.dto.exception.BusinessException;
import com.zq.cloud.dto.result.ResultError;
import com.zq.cloud.enums.CommonErrorTypeCode;
import com.zq.cloud.utils.ErrorCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public ResultError<Void> handleException(BusinessException ex, HttpServletRequest request, HttpServletResponse response) {
        log.warn("业务异常：{}", ex.getMessage());
        return ResultError.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResultError<Void> handleException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response) {
        String errorCode = "";

        CommonErrorTypeCode[] values = CommonErrorTypeCode.values();
        for (CommonErrorTypeCode errorTypeCode : values) {
            if (StringUtils.equalsAny(ex.getClass().getName(), errorTypeCode.getErrorClassArray())) {
                errorCode = ErrorCodeUtil.crateErrorCode(errorTypeCode);
                break;
            }
        }
        //未知异常
        if (StringUtils.isBlank(errorCode)) {
            errorCode = ErrorCodeUtil.crateErrorCode(CommonErrorTypeCode.UNKNOWN_ERROR);
        }
        return ResultError.error(errorCode, StaticFinalConstant.OPEN_ERROR_MESSAGE);
    }
}
