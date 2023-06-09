package com.qi.rpc.server.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 服务端配置属性类（必须提供 getter、setter 方法，否则无法注入属性值）
 *
 * @author CreateQi
 * @version 1.0
 * @ClassName RpcServerProperties
 * @Date 2023/1/6 23:33
 */
@Data
@ConfigurationProperties(prefix = "rpc.server")
public class RpcServerProperties {

    /**
     * The service startup port is 8080 by default
     */
    private Integer port;

    /**
     * Application name, which defaults to provider-1
     */
    private String appName;

    /**
     * Registration center, such as zookeeper and nacos, defaults to zookeeper
     */
    private String registry;

    /**
     * Transmission protocols, such as netty and http, are netty by default
     */
    private String transport;

    /**
     * The address of the registry is 127.0.0.1:2181 by default
     */
    private String registryAddr;

    /**
     * 进行默认初始化值
     */
    public RpcServerProperties() {
        this.port = 8080;
        this.appName = "provider-1";
        this.registry = "zookeeper";
        this.transport = "netty";
        this.registryAddr = "127.0.0.1:2181";
    }
}
