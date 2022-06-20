package com.zq.cloud.gateway.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import reactor.core.publisher.Mono;

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
        ServerHttpResponse response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);

        Route route = (Route) exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

        ResultBase<Void> errorResult = this.createErrorResult(response, ex, route.getId());
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


    private ResultBase<Void> createErrorResult(ServerHttpResponse response, Throwable ex, String routeId) {
        ResultBase<Void> resultBase = new ResultBase<>();
        resultBase.setSuccess(false);
        resultBase.setMessage(ex.getMessage());
        //未登录异常
        if (ex instanceof NotLoginException) {
            resultBase.setCode(((NotLoginException) ex).getCode());
            resultBase.setMessage(ex.getMessage());
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return resultBase;
        }

        //业务异常
        if (ex instanceof BusinessException) {
            resultBase.setCode(((BusinessException) ex).getCode());
            resultBase.setMessage(ex.getMessage());
            return resultBase;
        }

        // 数据库错误
        if (StringUtils.equalsAny(ex.getClass().getName(),
                "org.springframework.jdbc.BadSqlGrammarException", "org.mybatis.spring.MyBatisSystemException")) {
            resultBase.setCode(ErrorCodeUtil.crateErrorCode(routeId, CommonErrorTypeCode.DB_ERROR));
            resultBase.setMessage(ex.getMessage());
        } else {
            //位置异常
            resultBase.setCode(ErrorCodeUtil.crateErrorCode(routeId, CommonErrorTypeCode.UNKNOWN_ERROR));
            resultBase.setMessage(ex.getMessage());
        }


        return resultBase;
    }
}
