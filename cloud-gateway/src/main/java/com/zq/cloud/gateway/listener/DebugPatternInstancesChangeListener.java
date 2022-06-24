package com.zq.cloud.gateway.listener;

import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.naming.utils.NamingUtils;
import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;
import com.alibaba.nacos.common.notify.Event;
import com.alibaba.nacos.common.notify.NotifyCenter;
import com.alibaba.nacos.common.notify.listener.Subscriber;
import com.alibaba.nacos.common.utils.CollectionUtils;
import com.zq.cloud.gateway.utils.CheckDebugPatternVersionUtil;
import com.zq.cloud.starter.nacos.util.DebugPatternUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 监听调试实例变化
 */
@Slf4j
@Component
public class DebugPatternInstancesChangeListener extends Subscriber<InstancesChangeEvent> implements ApplicationRunner {
    @Value("${spring.application.name}")
    private String gatWayServiceName;


    @Override
    public void onEvent(InstancesChangeEvent event) {
        //排除gatWay本身
        if (NamingUtils.getGroupedName(gatWayServiceName, Constants.DEFAULT_GROUP).equals(event.getServiceName())) {
            return;
        }

        List<String> debugPatternInstancesList = event.getHosts().stream().filter(instance ->
                StringUtils.isNotBlank(instance.getMetadata().get(DebugPatternUtil.getDebugPatternKey())))
                .map(instance -> instance.getMetadata().get(DebugPatternUtil.getDebugPatternKey()))
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(debugPatternInstancesList)) {
            CheckDebugPatternVersionUtil.DEBUG_PATTERN_VERSION_MAP.put(event.getServiceName(), debugPatternInstancesList);
        }
    }

    @Override
    public Class<? extends Event> subscribeType() {
        return InstancesChangeEvent.class;
    }

    @Override

    public void run(ApplicationArguments args) throws Exception {
        NotifyCenter.registerSubscriber(this);
    }
}
