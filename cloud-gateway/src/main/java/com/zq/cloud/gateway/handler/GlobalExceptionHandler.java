package com.zq.cloud.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zq.cloud.constant.StaticFinalConstant;
import com.zq.cloud.dto.exception.BusinessException;
import com.zq.cloud.dto.exception.NotLoginException;
import com.zq.cloud.dto.result.ResultBase;
import com.zq.cloud.enums.CommonErrorTypeCode;
import com.zq.cloud.utils.ErrorCodeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 网关异常通用处理器，只作用在webflux环境下,优先级低于 {@link ResponseStatusExceptionHandler} 执行
 */
@Slf4j
@Order(-1)
@RequiredArgsConstructor
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);

        Route route = (Route) exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        Object serviceCodeObj = route.getMetadata().get(StaticFinalConstant.SERVICE_CODE_KEY);
        String serviceCodeStr = Objects.nonNull(serviceCodeObj) ? serviceCodeObj.toString() : "";
        ResultBase<Void> errorResult = this.createErrorResult(request, response, ex, serviceCodeStr);
        return response
                .writeWith(Mono.fromSupplier(() -> {
                    DataBufferFactory bufferFactory = response.bufferFactory();
                    try {
                        return bufferFactory.wrap(objectMapper.writeValueAsBytes(errorResult));
                    } catch (JsonProcessingException e) {
                        log.warn("Error writing response", ex);
                        return bufferFactory.wrap(new byte[0]);
                    }
                }));
    }


    /**
     * 异常返回
     *
     * @param request
     * @param response
     * @param ex
     * @param serviceCode
     * @return
     */
    private ResultBase<Void> createErrorResult(ServerHttpRequest request, ServerHttpResponse response, Throwable ex, String serviceCode) {
        ResultBase<Void> resultBase = new ResultBase<>();
        resultBase.setSuccess(false);
        resultBase.setMessage(ex.getMessage());
        //未登录异常
        if (ex instanceof NotLoginException) {
            resultBase.setErrorCode(((NotLoginException) ex).getCode());
            resultBase.setMessage(ex.getMessage());
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return resultBase;
        }

        //业务异常
        if (ex instanceof BusinessException) {
            resultBase.setErrorCode(((BusinessException) ex).getCode());
            resultBase.setMessage(ex.getMessage());
            return resultBase;
        }

        log.error("【非业务异常】:{} {}{} {}", request.getMethodValue().toUpperCase(),
                request.getURI().getHost(), request.getPath().value(),
                request.getURI().getQuery() == null ? "" : request.getURI().getQuery(), ex);


        //设置异常code
        CommonErrorTypeCode[] values = CommonErrorTypeCode.values();
        for (CommonErrorTypeCode errorTypeCode : values) {
            if (StringUtils.equalsAny(ex.getClass().getName(), errorTypeCode.getErrorClassArray())) {
                resultBase.setErrorCode(ErrorCodeUtil.crateErrorCode(serviceCode, errorTypeCode));
                break;
            }
        }
        //未知异常
        if (StringUtils.isBlank(resultBase.getErrorCode())) {
            resultBase.setErrorCode(ErrorCodeUtil.crateErrorCode(serviceCode, CommonErrorTypeCode.UNKNOWN_ERROR));
        }
        resultBase.setMessage(StaticFinalConstant.OPEN_ERROR_MESSAGE);
        return resultBase;
    }
}
