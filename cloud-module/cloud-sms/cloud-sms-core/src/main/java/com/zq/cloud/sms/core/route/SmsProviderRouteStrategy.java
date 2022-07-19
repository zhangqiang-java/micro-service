package com.zq.cloud.sms.core.route;

import com.zq.cloud.sms.core.enums.RouteStrategyEnum;
import com.zq.cloud.sms.core.enums.SmsProviderEnum;
import com.zq.cloud.sms.core.provider.SmsProvider;
import com.zq.cloud.utils.BusinessAssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 短信服务路由策略
 */
@Component
public class SmsProviderRouteStrategy implements ApplicationContextAware {

    @Value("${zq.sms.provider.routeStrategy:0}")
    private Integer routeStrategy;

    @Value("${zq.sms.provider.designated:}")
    private String designated;

    private AtomicInteger index = new AtomicInteger(0);

    private ApplicationContext applicationContext;

    private static Map<String, SmsProvider> PROVIDER_MAP = new HashMap<>();

    private static List<SmsProvider> PROVIDER_LIST = new ArrayList<>();

    @PostConstruct
    public void init() {
        PROVIDER_MAP = applicationContext.getBeansOfType(SmsProvider.class);
        BusinessAssertUtils.isTrue(PROVIDER_MAP.size() > 0, "没有配置短信服务方");
        for (Map.Entry<String, SmsProvider> entry : PROVIDER_MAP.entrySet()) {
            PROVIDER_LIST.add(entry.getValue());
        }
    }


    /**
     * 根据配置策略 选择服务提供方
     *
     * @return
     */
    public SmsProvider chooseProvider() {
        //随机
        if (RouteStrategyEnum.RANDOM.code().equals(routeStrategy)) {
            Random random = new Random();
            return PROVIDER_LIST.get(random.nextInt(PROVIDER_LIST.size()));
        }

        //轮询
        if (RouteStrategyEnum.POLLING.code().equals(routeStrategy)) {
            SmsProvider smsProvider = PROVIDER_LIST.get(Math.abs(index.get() % PROVIDER_LIST.size()));
            index.compareAndSet(index.get(), index.get() + 1);
            return smsProvider;
        }


        //指定
        if (RouteStrategyEnum.DESIGNATED.code().equals(routeStrategy)) {
            BusinessAssertUtils.isTrue(StringUtils.isNotBlank(designated), "没有选择指定服务");
            StringBuilder builder = new StringBuilder("请选择正确的服务提供方，可供选择的服务：");
            for (SmsProviderEnum smsProviderEnum : SmsProviderEnum.values()) {
                builder.append(smsProviderEnum.message()).append(":").append(smsProviderEnum.code()).append(",");
                if (smsProviderEnum.code().equalsIgnoreCase(designated)) {
                    return PROVIDER_MAP.get(smsProviderEnum.serviceCode());
                }
            }
            BusinessAssertUtils.fail(builder.toString(), routeStrategy, designated);
        }

        BusinessAssertUtils.fail("请选择正确的服务提供策略", routeStrategy);
        return null;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
