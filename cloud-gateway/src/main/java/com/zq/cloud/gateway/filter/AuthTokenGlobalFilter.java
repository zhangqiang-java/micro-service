package com.zq.cloud.gateway.filter;

import com.zq.cloud.authuser.facade.UserClient;
import com.zq.cloud.authuser.facade.dto.UserPermissionCheckDto;
import com.zq.cloud.constant.StaticFinalConstant;
import com.zq.cloud.dto.exception.NotLoginException;
import com.zq.cloud.dto.result.ResultBase;
import com.zq.cloud.gateway.config.GateWayStaticFinalConstant;
import com.zq.cloud.gateway.utils.WebFluxUtil;
import com.zq.cloud.utils.BusinessAssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * 统一鉴权
 */
@Slf4j
@Component
public class AuthTokenGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private UserClient userClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        //以anon 开头的认为是不需要鉴权的
        if (path.startsWith("/anon/")) {
            return chain.filter(exchange);
        }

        //从header中获取
        String token = exchange.getRequest().getHeaders().getFirst(GateWayStaticFinalConstant.AUTH);
        if (StringUtils.isBlank(token)) {
            throw new NotLoginException();
        }

        UserPermissionCheckDto checkDto = new UserPermissionCheckDto();
        checkDto.setToken(token);
        checkDto.setPath(request.getPath().value());
        ResultBase<String> checkResult = userClient.checkUserPermission(checkDto);
        if (!checkResult.isSuccess() || Objects.isNull(checkResult.getData())) {
            //未登录异常
            if (StaticFinalConstant.NOT_LOGIN_CODE.equals(checkResult.getErrorCode())) {
                throw new NotLoginException();
            }
            BusinessAssertUtils.fail(checkResult.getMessage());
        }

        // 将userInfo信息放到Header中
        ServerHttpRequest.Builder requestBuilder = exchange.getRequest().mutate();
        WebFluxUtil.putHeader(requestBuilder, StaticFinalConstant.USER_INFO_HEADER_NAME, checkResult.getData());
        return chain.filter(exchange.mutate().request(requestBuilder.build()).build());
    }

    @Override
    public int getOrder() {
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 3;
    }
}
