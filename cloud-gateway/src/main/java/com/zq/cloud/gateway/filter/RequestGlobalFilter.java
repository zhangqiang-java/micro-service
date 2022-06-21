package com.zq.cloud.gateway.filter;

import com.zq.cloud.gateway.utils.LogHelper;
import com.zq.cloud.gateway.utils.WebFluxUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyRequestBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR;

@Slf4j
@Component
public class RequestGlobalFilter implements GlobalFilter, Ordered {


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
                    StringUtils.abbreviate(printStr, 1024)
            );
            return Mono.just(requestBody == null ? new byte[0] : requestBody);
        });
        filter = modifyRequestBodyGatewayFilterFactory.apply(config);
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //设置有灰色版本号的标记
        //settingGreyVersion(exchange);
        return filter.filter(exchange, chain);
    }

//    /**
//     * 设置有灰色版本号的标记
//     *
//     * @param exchange
//     */
//    private void settingGreyVersion(ServerWebExchange exchange) {
//        ServerHttpRequest request = exchange.getRequest();
//        List<HttpCookie> httpCookies = request.getCookies().get(GreyVersionGetUtils.getGreyVersionKey());
//        String greyVersion = httpCookies == null ? null : httpCookies.stream().filter(httpCookie -> httpCookie.getName()
//                .equals(GreyVersionGetUtils.getGreyVersionKey()))
//                .map(HttpCookie::getValue).findFirst().orElse(null);
//        if (StringUtils.isBlank(greyVersion)) {
//            greyVersion = request.getHeaders().getFirst(GreyVersionGetUtils.getGreyVersionKey());
//        }
//        if (StringUtils.isBlank(greyVersion)) {
//            greyVersion = request.getQueryParams().getFirst(GreyVersionGetUtils.getGreyVersionKey());
//        }
//        if (StringUtils.isBlank(greyVersion)) {
//            GreyVersionFromCustomizeContext.clear();
//        } else {
//            Route route = exchange.getAttribute(GATEWAY_ROUTE_ATTR);
//            if (route == null || route.getUri() == null) {
//                return;
//            }
//            if (route.getUri().getScheme().equals("lb")) {
//                // 是访问的lb服务，就在nacos中检查灰度版本是否存在。
//                boolean existGreyVersion = greyVersionChangeListener.existGreyVersion(greyVersion);
//                BizAssertUtils.isTrue(existGreyVersion, "灰度版本号已经不存在对应的服务,请清除携带的灰度版本号信息", greyVersion, request.getPath());
//            } else if (route.getUri().getScheme().equals("md")) {
//                // 是访问的Metadata配置的服务，就在路由中检查灰度版本是否存在。
//                String uris = (String) route.getMetadata().getOrDefault(route.getUri().getAuthority(), "null");
//                String greyVersionSuffix = MetadataUriListGatewayFilterFactory.GREY_DELIMITER + greyVersion;
//                boolean existGreyVersion = Arrays.stream(uris.split(MetadataUriListGatewayFilterFactory.URIS_DELIMITER)).anyMatch(uri -> uri.endsWith(greyVersionSuffix));
//                BizAssertUtils.isTrue(existGreyVersion, "灰度版本号已经不存在对应的路由配置,请清除携带的灰度版本号信息", greyVersion, request.getPath());
//            }
//            GreyVersionFromCustomizeContext.setGreyVersion(greyVersion);
//            //这里设置在Attributes方便响应后的线程池使用
//            exchange.getAttributes().put(GreyVersionGetUtils.getGreyVersionKey(), greyVersion);
//        }
//    }

}
