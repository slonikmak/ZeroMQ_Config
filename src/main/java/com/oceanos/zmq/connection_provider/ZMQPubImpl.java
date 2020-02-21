package com.oceanos.zmq.connection_provider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public class ZMQPubImpl<T> extends ZMQConnection implements ZMQPub<T>{


    ZMQPubImpl(ZContext context, ConnectionUnit connectionUnit, ObjectMapper objectMapper) {
        super(context, connectionUnit, objectMapper);
    }

    @Override
    ZMQ.Socket createSocket(ZContext context) {
        return context.createSocket(SocketType.PUB);
    }

    public void send(T msg) throws JsonProcessingException {
        String msgString = objectMapper.writeValueAsString(msg);
        socket.send(connectionUnit.getTopic()+msgString);
    }

}
