package com.zq.cloud.gateway.filter;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.zq.cloud.gateway.listener.DebugPatternInstancesChangeListener;
import com.zq.cloud.gateway.utils.LogHelper;
import com.zq.cloud.gateway.utils.WebFluxUtil;
import com.zq.cloud.starter.nacos.discovery.config.DebugPatternFromCustomizeContext;
import com.zq.cloud.starter.nacos.discovery.util.DebugPatternUtil;
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
            //????????????
            log.info("????????????:{} {}{} {} origin={} contentType={} requestBody={}",
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
     * ??????????????????????????????????????? ??????????????? cook header parm???
     *
     * @param exchange
     */
    private void settingDebugPatternVersion(ServerWebExchange exchange) {
        //???????????????
        String debugPatternVersion = null;
        ServerHttpRequest request = exchange.getRequest();

        //???cookie?????????
        List<HttpCookie> httpCookies = request.getCookies().get(DebugPatternUtil.getDebugPatternKey());
        if (CollectionUtils.isNotEmpty(httpCookies)) {
            debugPatternVersion = httpCookies.stream().map(HttpCookie::getValue).findFirst().orElse(null);
        }

        //cookie????????? ???header?????????
        if (StringUtils.isEmpty(debugPatternVersion)) {
            debugPatternVersion = request.getHeaders().getFirst(DebugPatternUtil.getDebugPatternKey());
        }

        // header????????? ????????????????????????
        if (StringUtils.isEmpty(debugPatternVersion)) {
            debugPatternVersion = request.getQueryParams().getFirst(DebugPatternUtil.getDebugPatternKey());
        }

        // ???????????????????????????
        if (StringUtils.hasText(debugPatternVersion)) {
            Route route = (Route) exchange.getAttributes().get(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            boolean existDebugPatternVersion =
                    debugPatternInstancesChangeListener.checkDebugPatternVersionMap(debugPatternVersion,route.getId());
            if (existDebugPatternVersion) {
                DebugPatternFromCustomizeContext.setDebugPatternVersion(debugPatternVersion);
            } else {
                log.info("???????????????{}?????????????????????????????????,????????????????????????", debugPatternVersion);
                DebugPatternFromCustomizeContext.clear();
            }
        } else {
            DebugPatternFromCustomizeContext.clear();
        }
    }

}
