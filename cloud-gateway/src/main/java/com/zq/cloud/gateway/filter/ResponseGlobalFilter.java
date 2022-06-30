package com.zq.cloud.gateway.filter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zq.cloud.constant.StaticFinalConstant;
import com.zq.cloud.gateway.utils.LogHelper;
import com.zq.cloud.starter.nacos.config.DebugPatternFromCustomizeContext;
import com.zq.cloud.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Objects;

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
            String responseBodyStr = LogHelper.covertStr(response.getHeaders().getContentType(), responseBody);

            if (StringUtils.isNotBlank(responseBodyStr)) {
                // 异常返回时 添加ServiceCode
                responseBodyStr = addErrorResultServiceCode(exchange, responseBodyStr);
            }

            responseBodyStr = StringUtils.isBlank(responseBodyStr) ? "null" : responseBodyStr;
            //打印日志
            log.info("【响应】:{} {}{} {} statusCode={} contentType={} responseBody={}",
                    request.getMethodValue().toUpperCase(),
                    request.getURI().getHost(),
                    request.getPath().value(),
                    request.getURI().getQuery() == null ? "" : request.getURI().getQuery(),
                    response.getStatusCode(),
                    response.getHeaders().getContentType(),
                    StringUtils.abbreviate(responseBodyStr, 1024)
            );
            return Mono.just(responseBody == null ? new byte[0] : responseBodyStr.getBytes(LogHelper.getMediaTypeCharset(response.getHeaders().getContentType())));
        });
        filter = modifyResponseBodyGatewayFilterFactory.apply(config);
    }

    /**
     * 异常返回时 添加ServiceCode
     *
     * @param exchange
     * @param responseBody
     * @return
     */
    private String addErrorResultServiceCode(ServerWebExchange exchange, String responseBody) {
        try {
            ObjectNode root = (ObjectNode) JacksonUtils.getObjectMapper().readTree(responseBody);
            JsonNode errorCodeNode = root.findValue(StaticFinalConstant.ERROR_CODE_KEY);
            if (errorCodeNode != null && errorCodeNode.isTextual()) {
                String errorCode = errorCodeNode.asText();
                // 错误码不时空 并且没有添加过serviceCode
                if (StringUtils.isNotBlank(errorCode)
                        && !StaticFinalConstant.SUCCESS_CODE.equals(errorCode)
                        && !errorCode.startsWith(StaticFinalConstant.SERVICE_PREFIX)) {
                    Route route = (Route) exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
                    Object serviceCodeObj = route.getMetadata().get(StaticFinalConstant.SERVICE_CODE_KEY);
                    String serviceCodeStr = Objects.nonNull(serviceCodeObj) ? serviceCodeObj.toString() : "";
                    errorCode = StaticFinalConstant.ERROR_TYPE_PREFIX + serviceCodeStr + errorCode;
                    root.put(StaticFinalConstant.ERROR_CODE_KEY, errorCode);
                    return JacksonUtils.getObjectMapper().writeValueAsString(root);
                }
            }
        } catch (Exception e) {
            log.error("异常返回时 添加ServiceCode", e);
        }
        return responseBody;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        DebugPatternFromCustomizeContext.clear();
        return filter.filter(exchange, chain);
    }
}
