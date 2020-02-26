package com.oceanos.zmq.connection_provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

public abstract class ZMQConnection implements AutoCloseable{

    final  ZMQ.Socket socket;
    final  ConnectionUnit connectionUnit;
    final  ObjectMapper objectMapper;

    public ZMQConnection(ZContext context, ConnectionUnit connectionUnit, ObjectMapper objectMapper){
        socket = createSocket(context);
        this.connectionUnit = connectionUnit;
        this.objectMapper = objectMapper;
    }

    abstract ZMQ.Socket createSocket(ZContext context);

    public void bind(){
        socket.bind(connectionUnit.getConnectionAddress().getAddress());
    }

    public void connect(){
        socket.connect(connectionUnit.getConnectionAddress().getAddress());
    }

    @Override
    public void close(){
        this.socket.close();
    }

}
