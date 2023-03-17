package com.qi.rpc.server.transport.netty;

import com.qi.rpc.server.transport.RpcServer;

/**
 * @author CreateQi
 * @version 1.0
 * @ClassName TestNettyServer
 * @Date 2023/1/7 19:59
 */
public class TestNettyServer {

    public static void main(String[] args) {
        RpcServer rpcServer = new NettyRpcServer();
        rpcServer.start(8880);
    }

}
