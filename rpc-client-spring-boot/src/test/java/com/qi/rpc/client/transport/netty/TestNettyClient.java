package com.qi.rpc.client.transport.netty;

import com.qi.rpc.client.common.RequestMetadata;
import com.qi.rpc.client.transport.RpcClient;
import com.qi.rpc.core.common.RpcRequest;
import com.qi.rpc.core.enums.MessageType;
import com.qi.rpc.core.enums.SerializationType;
import com.qi.rpc.core.protocol.MessageHeader;
import com.qi.rpc.core.protocol.RpcMessage;

/**
 * @author CreateQi
 * @version 1.0
 * @ClassName TestNettyClient
 * @Date 2023/1/7 19:57
 */
public class TestNettyClient {

    public static void main(String[] args) {
        RpcClient rpcClient = new NettyRpcClient();
        RpcMessage rpcMessage = new RpcMessage();
        MessageHeader header = MessageHeader.build(SerializationType.KRYO.name());
        header.setMessageType(MessageType.REQUEST.getType());
        rpcMessage.setHeader(header);
        RpcRequest request = new RpcRequest();
        rpcMessage.setBody(request);
        RequestMetadata metadata = RequestMetadata.builder()
                .rpcMessage(rpcMessage)
                .serverAddr("192.168.0.5")
                .port(8880).build();
        rpcClient.sendRpcRequest(metadata);
    }

}
