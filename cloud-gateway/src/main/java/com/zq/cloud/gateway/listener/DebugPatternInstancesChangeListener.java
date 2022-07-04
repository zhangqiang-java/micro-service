package com.zq.cloud.gateway.listener;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.nacos.api.common.Constants;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.api.naming.utils.NamingUtils;
import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;
import com.alibaba.nacos.common.notify.Event;
import com.alibaba.nacos.common.notify.NotifyCenter;
import com.alibaba.nacos.common.notify.listener.Subscriber;
import com.zq.cloud.constant.StaticFinalConstant;
import com.zq.cloud.dto.exception.BusinessException;
import com.zq.cloud.gateway.config.GateWayStaticFinalConstant;
import com.zq.cloud.starter.nacos.discovery.util.DebugPatternUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 监听调试实例变化
 */
@Slf4j
@Component
public class DebugPatternInstancesChangeListener extends Subscriber<InstancesChangeEvent> implements ApplicationRunner {
    @Value("${spring.application.name}")
    private String gatWayServiceName;

    @Autowired
    private NacosServiceManager nacosServiceManager;

    @Autowired
    private NacosDiscoveryProperties discoveryProperties;

    /**
     * 注册进来的调试服务
     */
    public final static ConcurrentHashMap<String, List<String>> DEBUG_PATTERN_VERSION_MAP = new ConcurrentHashMap<>();


    /**
     * 检查是否存在对应的调试版本
     *
     * @param debugPatternVersion
     * @return
     */
    public boolean checkDebugPatternVersionMap(String debugPatternVersion, String serverId) {
        if (DEBUG_PATTERN_VERSION_MAP.values().stream().flatMap(Collection::stream).collect(Collectors.toList()).contains(debugPatternVersion)) {
            return true;
        }

        String serviceName = NamingUtils.getGroupedName(serverId, Constants.DEFAULT_GROUP);
        //没有订阅过该服务
        if (!DEBUG_PATTERN_VERSION_MAP.containsKey(serviceName)) {
            NamingService namingService =
                    nacosServiceManager.getNamingService(discoveryProperties.getNacosProperties());
            try {
                //查询并订阅
                List<Instance> instanceList = namingService.getAllInstances(serverId, true);
                List<String> debugPatternInstancesList =
                        instanceList.stream().map(instance -> instance.getMetadata().get(DebugPatternUtil.getDebugPatternKey()))
                                .collect(Collectors.toList());
                DEBUG_PATTERN_VERSION_MAP.put(serviceName, debugPatternInstancesList);
            } catch (NacosException e) {
                //网关code码写死为000  001
                throw new BusinessException(GateWayStaticFinalConstant.SERVER_CODE,
                        StaticFinalConstant.OPEN_ERROR_MESSAGE, GateWayStaticFinalConstant.NACOS_EXCEPTION_CODE);
            }

        }
        return DEBUG_PATTERN_VERSION_MAP.values().stream().flatMap(Collection::stream).collect(Collectors.toList()).contains(debugPatternVersion);
    }


    @Override
    public void onEvent(InstancesChangeEvent event) {
        //排除gatWay本身
        if (NamingUtils.getGroupedName(gatWayServiceName, Constants.DEFAULT_GROUP).equals(event.getServiceName())) {
            return;
        }

        List<String> debugPatternInstancesList = event.getHosts().stream()
                .map(instance -> instance.getMetadata().get(DebugPatternUtil.getDebugPatternKey()))
                .collect(Collectors.toList());
        DEBUG_PATTERN_VERSION_MAP.put(event.getServiceName(), debugPatternInstancesList);

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
