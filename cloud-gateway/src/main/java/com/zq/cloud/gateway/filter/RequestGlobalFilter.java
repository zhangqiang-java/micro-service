package com.zq.cloud.gateway.filter;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.zq.cloud.gateway.listener.DebugPatternInstancesChangeListener;
import com.zq.cloud.gateway.utils.CheckDebugPatternVersionUtil;
import com.zq.cloud.gateway.utils.LogHelper;
import com.zq.cloud.gateway.utils.WebFluxUtil;
import com.zq.cloud.starter.nacos.config.DebugPatternFromCustomizeContext;
import com.zq.cloud.starter.nacos.util.DebugPatternUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Component
public class RequestGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private DebugPatternInstancesChangeListener debugPatternInstancesChangeListener;


    @Autowired
    private ModifyRequestBodyGatewayFilterFactory modifyRequestBodyGatewayFilterFactory;

    private ModifyRequestBodyGatewayFilterFactory.Config config = new ModifyRequestBodyGatewayFilterFactory.Config();

    private GatewayFilter filter = null;

    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }

    @PostConstruct
    public void init() {
        config.setRewriteFunction(byte[].class, byte[].class, (exchange, requestBody) -> {
            ServerHttpRequest request = exchange.getRequest();
            String printStr = LogHelper.covertStr(request.getHeaders().getContentType(), requestBody);
            //打印日志
            log.info("【请求】:{} {}{} {} origin={} contentType={} requestBody={}",
                    request.getMethodValue().toUpperCase(),
                    request.getURI().getHost(),
                    request.getPath().value(),
                    request.getURI().getQuery() == null ? "" : request.getURI().getQuery(),
                    WebFluxUtil.getIpAddress(request),
                    request.getHeaders().getContentType(),
                    org.apache.commons.lang.StringUtils.abbreviate(printStr, 1024)
            );
            return Mono.just(requestBody == null ? new byte[0] : requestBody);
        });
        filter = modifyRequestBodyGatewayFilterFactory.apply(config);
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        settingDebugPatternVersion(exchange);
        return filter.filter(exchange, chain);
    }

    /**
     * 设置有调试模式版本号的标记 （获取路径 cook header parm）
     *
     * @param exchange
     */
    private void settingDebugPatternVersion(ServerWebExchange exchange) {
        //调试版本号
        String debugPatternVersion = null;
        ServerHttpRequest request = exchange.getRequest();

        //从cookie中获取
        List<HttpCookie> httpCookies = request.getCookies().get(DebugPatternUtil.getDebugPatternKey());
        if (CollectionUtils.isNotEmpty(httpCookies)) {
            debugPatternVersion = httpCookies.stream().map(HttpCookie::getValue).findFirst().orElse(null);
        }

        //cookie中没有 从header中获取
        if (StringUtils.isEmpty(debugPatternVersion)) {
            debugPatternVersion = request.getHeaders().getFirst(DebugPatternUtil.getDebugPatternKey());
        }

        // header中没有 从请求参数中获取
        if (StringUtils.isEmpty(debugPatternVersion)) {
            debugPatternVersion = request.getQueryParams().getFirst(DebugPatternUtil.getDebugPatternKey());
        }

        // 存在就放在上下问中
        if (StringUtils.hasText(debugPatternVersion)) {
            Route route = (Route) exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            boolean existDebugPatternVersion =
                    debugPatternInstancesChangeListener.checkDebugPatternVersionMap(debugPatternVersion,route.getId());
            if (existDebugPatternVersion) {
                DebugPatternFromCustomizeContext.setDebugPatternVersion(debugPatternVersion);
            } else {
                log.info("调试版本【{}】的对应调试服务不存在,将走正常路由逻辑", debugPatternVersion);
                DebugPatternFromCustomizeContext.clear();
            }
        } else {
            DebugPatternFromCustomizeContext.clear();
        }
    }

}
