package com.zq.cloud.gateway.filter;

import com.zq.cloud.gateway.utils.LogHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class ResponseGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory;


    private GatewayFilter filter = null;

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 2;
    }


    @PostConstruct
    public void init() {
        ModifyResponseBodyGatewayFilterFactory.Config config = new ModifyResponseBodyGatewayFilterFactory.Config();
        config.setRewriteFunction(byte[].class, byte[].class, (exchange, responseBody) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();
            String printStr = LogHelper.covertStr(response.getHeaders().getContentType(), responseBody);
            printStr = StringUtils.isBlank(printStr) ? "null" : printStr;
            //打印日志
            log.info("【响应】:{} {}{} {} statusCode={} contentType={} responseBody={}",
                    request.getMethodValue().toUpperCase(),
                    request.getURI().getHost(),
                    request.getPath().value(),
                    request.getURI().getQuery() == null ? "" : request.getURI().getQuery(),
                    response.getStatusCode(),
                    response.getHeaders().getContentType(),
                    StringUtils.abbreviate(printStr, 1024)
            );
            return Mono.just(responseBody == null ? new byte[0] : responseBody);
        });
        filter = modifyResponseBodyGatewayFilterFactory.apply(config);
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return filter.filter(exchange, chain);
    }
}
