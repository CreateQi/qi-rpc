package com.qi.rpc.client.proxy;

import com.qi.rpc.client.common.RequestMetadata;
import com.qi.rpc.client.config.RpcClientProperties;
import com.qi.rpc.client.transport.RpcClient;
import com.qi.rpc.core.common.RpcRequest;
import com.qi.rpc.core.common.RpcResponse;
import com.qi.rpc.core.common.ServiceInfo;
import com.qi.rpc.core.discovery.ServiceDiscovery;
import com.qi.rpc.core.exception.RpcException;
import com.qi.rpc.core.protocol.MessageHeader;
import com.qi.rpc.core.protocol.RpcMessage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 客户端方法调用处理器类
 *
 * @author CreateQi
 * @version 1.0
 * @ClassName ClientStubInvocationHandler
 * @Date 2023/1/7 14:03
 */
public class ClientStubInvocationHandler implements InvocationHandler {

    private final ServiceDiscovery serviceDiscovery;

    private final RpcClient rpcClient;

    private final RpcClientProperties properties;

    private final String serviceName;

    public ClientStubInvocationHandler(ServiceDiscovery serviceDiscovery, RpcClient rpcClient, RpcClientProperties properties, String serviceName) {
        this.serviceDiscovery = serviceDiscovery;
        this.rpcClient = rpcClient;
        this.properties = properties;
        this.serviceName = serviceName;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构建请求头
        MessageHeader header = MessageHeader.build(properties.getSerialization());
        // 构建请求体
        RpcRequest request = new RpcRequest();
        request.setServiceName(serviceName);
        request.setMethod(method.getName());
        request.setParameterTypes(method.getParameterTypes());
        request.setParameterValues(args);

        // 进行服务发现
        ServiceInfo serviceInfo = serviceDiscovery.discover(request);
        if (serviceInfo == null) {
            throw new RpcException(String.format("The service [%s] was not found in the remote registry center.",
                    serviceName));
        }

        // 构建通信协议信息
        RpcMessage rpcMessage = new RpcMessage();
        rpcMessage.setHeader(header);
        rpcMessage.setBody(request);

        // 构建请求元数据
        RequestMetadata metadata = RequestMetadata.builder()
                .rpcMessage(rpcMessage)
                .serverAddr(serviceInfo.getAddress())
                .port(serviceInfo.getPort())
                .timeout(properties.getTimeout()).build();

        // todo: 是否每次 invoke 方法一次都创建一个新的 RpcClient ？？？？

        //  获得 RpcClient 实现类
//        RpcClient rpcClient = RpcClientFactory.getRpcClient(properties.getTransport());

        // 发送网络请求，获取结果
        RpcMessage responseRpcMessage = rpcClient.sendRpcRequest(metadata);

        if (responseRpcMessage == null) {
            throw new RpcException("Remote procedure call timeout.");
        }

        // 获取响应结果
        RpcResponse response = (RpcResponse) responseRpcMessage.getBody();

        // 如果 远程调用 发生错误
        if (response.getExceptionValue() != null) {
            throw new RpcException(response.getExceptionValue());
        }
        // 返回响应结果
        return response.getReturnValue();
    }
}
