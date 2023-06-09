package com.qi.rpc.client.proxy;

import com.qi.rpc.client.config.RpcClientProperties;
import com.qi.rpc.client.transport.RpcClient;
import com.qi.rpc.core.discovery.ServiceDiscovery;
import com.qi.rpc.core.util.ServiceUtil;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端代理工厂类，返回服务代理类
 *
 * @author CreateQi
 * @version 1.0
 * @ClassName ClientStubProxyFactory
 * @Date 2023/1/7 14:54
 */
public class ClientStubProxyFactory {

    /**
     * 服务发现中心实现类
     */
    private final ServiceDiscovery discovery;

    /**
     * RpcClient 传输实现类
     */
    private final RpcClient rpcClient;

    /**
     * 客户端配置属性
     */
    private final RpcClientProperties properties;

    public ClientStubProxyFactory(ServiceDiscovery discovery, RpcClient rpcClient, RpcClientProperties properties) {
        this.discovery = discovery;
        this.rpcClient = rpcClient;
        this.properties = properties;
    }

    /**
     * 代理对象缓存
     */
    private static final Map<String, Object> proxyMap = new ConcurrentHashMap<>();

    /**
     * 获取代理对象
     *
     * @param clazz   服务接口类型
     * @param version 版本号
     * @param <T>     代理对象的参数类型
     * @return 对应版本的代理对象
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz, String version) {
        return (T) proxyMap.computeIfAbsent(ServiceUtil.serviceKey(clazz.getName(), version),
                serviceName -> Proxy.newProxyInstance(
                        clazz.getClassLoader(),
                        new Class[]{clazz}, // 注意，这里的接口是 clazz 本身（即，要代理的实现类所实现的接口）
                        new ClientStubInvocationHandler(discovery, rpcClient, properties, serviceName)));
    }

}
