package com.zq.cloud.starter.web.aop;

import com.zq.cloud.constant.CommonStaticFinalConstant;
import com.zq.cloud.dto.exception.BusinessException;
import com.zq.cloud.dto.exception.NotLoginException;
import com.zq.cloud.dto.result.ResultError;
import com.zq.cloud.enums.CommonErrorTypeCode;
import com.zq.cloud.starter.web.utils.RealIpAddressUtil;
import com.zq.cloud.utils.ErrorCodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ValidationException;

/**
 * 通用异常处理类
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 处理业务异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResultError<Void> handleBusinessException(BusinessException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("来自{}的请求：{}，发生业务异常：{}", RealIpAddressUtil.getIpAddress(request), request.getRequestURI(), ex.getMessage());
        return ResultError.error(ex.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NotLoginException.class)
    public ResultError<Void> handleException(NotLoginException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("来自{}的请求：{}，发生未登录异常：{}", RealIpAddressUtil.getIpAddress(request), request.getRequestURI(), ex);
        return ResultError.error(ex.getCode(), ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public Object handleException(ValidationException e, HttpServletRequest request, HttpServletResponse response) {
        log.error("参数错误：url={}，{}", request.getRequestURI(), e.getMessage());
        return ResultError.error(CommonErrorTypeCode.INVALID_PARAM.getCode(), e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public ResultError<Void> handleException(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("来自{}的请求：{}，发生未知异常：{}", RealIpAddressUtil.getIpAddress(request), request.getRequestURI(), ex);
        return ResultError.error(ErrorCodeUtil.crateErrorCode(CommonErrorTypeCode.UNKNOWN_ERROR), CommonStaticFinalConstant.OPEN_ERROR_MESSAGE);
    }


    /**
     * 处理运行时异常
     *
     * @param ex
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResultError<Void> handleRuntimeException(RuntimeException ex, HttpServletRequest request, HttpServletResponse response) {
        log.error("来自{}的请求：{}，发生运行时异常：{}", RealIpAddressUtil.getIpAddress(request), request.getRequestURI(), ex);
        String errorCode = "";

        CommonErrorTypeCode[] values = CommonErrorTypeCode.values();
        for (CommonErrorTypeCode errorTypeCode : values) {
            if (StringUtils.equalsAny(ex.getClass().getName(), errorTypeCode.getErrorClassArray())) {
                errorCode = ErrorCodeUtil.crateErrorCode(errorTypeCode);
            }
        }
        //未知异常
        if (StringUtils.isBlank(errorCode)) {
            errorCode = ErrorCodeUtil.crateErrorCode(CommonErrorTypeCode.UNKNOWN_ERROR);
        }

        //具体原因通过code表示， 对外报的异常提醒统一
        return ResultError.error(errorCode, CommonStaticFinalConstant.OPEN_ERROR_MESSAGE);
    }

}
