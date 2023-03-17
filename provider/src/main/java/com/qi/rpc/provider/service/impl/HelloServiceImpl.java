package com.qi.rpc.provider.service.impl;

import com.qi.rpc.api.service.HelloService;
import com.qi.rpc.server.annotation.RpcService;

@RpcService(interfaceClass = HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
