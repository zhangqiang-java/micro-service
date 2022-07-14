package com.zq.cloud.gateway.handler;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.zq.cloud.constant.CommonStaticFinalConstant;
import com.zq.cloud.dto.result.ResultBase;
import com.zq.cloud.enums.CommonErrorTypeCode;
import com.zq.cloud.utils.ErrorCodeUtil;
import com.zq.cloud.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 自定义限流处理
 */
@Slf4j
@Component
public class SentinelBlockRequestHandler implements BlockRequestHandler {

    @Override
    public Mono<ServerResponse> handleRequest(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        response.setStatusCode(HttpStatus.OK);
        ResultBase<Void> resultBase = new ResultBase<>();
        resultBase.setSuccess(false);
        resultBase.setMessage(ex.getMessage());

        Route route = (Route) exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        Object serviceCodeObj = route.getMetadata().get(CommonStaticFinalConstant.SERVICE_CODE_KEY);
        String serviceCodeStr = Objects.nonNull(serviceCodeObj) ? serviceCodeObj.toString() : "";
        resultBase.setErrorCode(ErrorCodeUtil.crateErrorCode(serviceCodeStr, CommonErrorTypeCode.BLOCK_EXCEPTION));
        resultBase.setMessage(CommonStaticFinalConstant.OPEN_ERROR_MESSAGE);
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Objects.requireNonNull(JacksonUtils.toJsonString(resultBase)));
    }
}
