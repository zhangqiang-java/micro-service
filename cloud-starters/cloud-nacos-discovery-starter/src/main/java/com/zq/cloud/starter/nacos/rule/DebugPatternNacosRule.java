package com.zq.cloud.starter.nacos.rule;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.Server;
import com.zq.cloud.starter.nacos.util.DebugPatternUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * 带了调试模式的标识  选择对应调试的服务  没有对应key就当正常模式处理
 * 没有带调试模式 从正常中的服务中获取
 */
@Slf4j
public class DebugPatternNacosRule extends RoundRobinRule {

    @Override
    public Server choose(Object key) {
        ILoadBalancer loadBalancer = this.getLoadBalancer();
        //校验是否调试模式请求
        String debugPatternVersion = DebugPatternUtil.getDebugPatternVersion();
        if (StringUtils.hasText(debugPatternVersion)) {
            //选取指定的服务
            NacosServer debugServer = findDebugServer(loadBalancer.getReachableServers(), debugPatternVersion);
            if (Objects.nonNull(debugServer)) {
                log.info("找到：{}对应的调试服务：{},服务节点：{}", debugPatternVersion, debugServer.getInstance().getServiceName(), debugServer.getHostPort());
                return debugServer;
            }
        }

        //没有指定服务 或者正常请求走正常逻辑
        final Server choose = super.choose(new DebugPatternILoadBalancer(loadBalancer), key);
        if (StringUtils.hasText(debugPatternVersion)) {
            log.info("未找到：{}对应的调试服务：{},正常负载到服务节点：{}", debugPatternVersion, choose.getMetaInfo().getAppName(), choose.getHostPort());
        }
        return choose;
    }


    /**
     * 查询对应调试版本的服务
     *
     * @param serverList
     * @param debugPatternVersion
     * @return
     */
    private NacosServer findDebugServer(List<Server> serverList, String debugPatternVersion) {
        for (Server debugPatternServer : serverList) {
            if (debugPatternServer instanceof NacosServer) {
                String serverMetadataDebugVersion = ((NacosServer) debugPatternServer).getMetadata().get(DebugPatternUtil.getDebugPatternKey());
                if (StringUtils.trimWhitespace(debugPatternVersion).equalsIgnoreCase(serverMetadataDebugVersion)) {
                    return (NacosServer) debugPatternServer;
                }
            }
        }
        return null;
    }
}
