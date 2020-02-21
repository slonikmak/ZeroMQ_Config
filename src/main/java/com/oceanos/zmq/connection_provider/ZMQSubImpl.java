package com.oceanos.zmq.connection_provider;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.IOException;

public class ZMQSubImpl<T> extends ZMQConnection implements ZMQSub<T>{

    final private JavaType type;

    ZMQSubImpl(ZContext context, ConnectionUnit connectionUnit, ObjectMapper objectMapper, Class<T> type) {
        super(context, connectionUnit, objectMapper);
        socket.subscribe(connectionUnit.getTopic());
        this.type = objectMapper.getTypeFactory().constructType(type);
    }

    @Override
    ZMQ.Socket createSocket(ZContext context) {
        return context.createSocket(SocketType.SUB);
    }


    @Override
    public T receive() throws IOException {
        String msg = socket.recvStr();
        return objectMapper.readValue(msg.replace(connectionUnit.getTopic(),""), type);
    }
}
