package com.oceanos.zmq.connection_provider;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.io.IOException;

public class ZMQSubImpl<T> extends ZMQConnection implements ZMQSub<T>{
    static Logger logger = LoggerFactory.getLogger(ZMQSubImpl.class);

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
    public T receive() {
        String msg = socket.recvStr();
        try {
            return objectMapper.readValue(msg.replace(connectionUnit.getTopic(),""), type);
        } catch (IOException e) {
            //e.printStackTrace();
            logger.error("Massage parsing error!", e);
        }
        return null;
    }
}
