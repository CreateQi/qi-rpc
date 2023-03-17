package com.qi.rpc.core.discovery.nacos;

import com.qi.rpc.core.common.RpcRequest;
import com.qi.rpc.core.common.ServiceInfo;
import com.qi.rpc.core.discovery.ServiceDiscovery;
import com.qi.rpc.core.loadbalance.LoadBalance;

import java.io.IOException;

/**
 * @author CreateQi
 * @version 1.0
 * @ClassName NacosServiceDiscovery
 * @Date 2023/1/8 16:19
 */
public class NacosServiceDiscovery implements ServiceDiscovery {

    private final String registryAddr;

    private final LoadBalance loadBalance;

    public NacosServiceDiscovery(String registryAddr, LoadBalance loadBalance) {
        this.registryAddr = registryAddr;
        this.loadBalance = loadBalance;
    }

    @Override
    public ServiceInfo discover(RpcRequest request) {
        return null;
    }

    @Override
    public void destroy() throws IOException {

    }
}
