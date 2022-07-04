package com.zq.cloud.starter.nacos.discovery.rule;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.zq.cloud.starter.nacos.discovery.util.DebugPatternUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 调试模式的负载均衡器
 */
public class DebugPatternILoadBalancer implements ILoadBalancer {

    public ILoadBalancer iLoadBalancer;

    public DebugPatternILoadBalancer(ILoadBalancer iLoadBalancer) {
        this.iLoadBalancer = iLoadBalancer;
    }


    @Override
    public void addServers(List<Server> newServers) {
        iLoadBalancer.addServers(newServers);
    }

    @Override
    public Server chooseServer(Object key) {
        return iLoadBalancer.chooseServer(key);
    }

    @Override
    public void markServerDown(Server server) {
        iLoadBalancer.markServerDown(server);
    }

    @Override
    public List<Server> getServerList(boolean availableOnly) {
        return (availableOnly ? getReachableServers() : getAllServers());
    }

    /**
     * 排除调试的服务
     *
     * @return
     */
    @Override
    public List<Server> getReachableServers() {
        List<Server> reachableServers = iLoadBalancer.getReachableServers();
        return excludeDebugServers(reachableServers);
    }

    /**
     * 排除调试服务 只存在调试的服务，没有正常服务 就直接返回所有正常的服务 不做排除
     *
     * @return
     */
    @Override
    public List<Server> getAllServers() {
        List<Server> allServers = iLoadBalancer.getAllServers();
        return excludeDebugServers(allServers);
    }


    /**
     * 排除调试服务 只存在调试的服务，没有正常服务 就直接返回所有正常的服务 不做排除
     *
     * @param servers
     * @return
     */
    private List<Server> excludeDebugServers(List<Server> servers) {
        List<Server> excludeDebugServers = new ArrayList<>();
        for (Server server : servers) {
            if (server instanceof NacosServer) {
                String debugPatternVersion = ((NacosServer) server).getMetadata().get(DebugPatternUtil.getDebugPatternKey());
                if (!StringUtils.hasText(debugPatternVersion)) {
                    excludeDebugServers.add(server);
                }
            }
        }
        return Collections.unmodifiableList(CollectionUtils.isEmpty(excludeDebugServers)?servers:excludeDebugServers);
    }
}
