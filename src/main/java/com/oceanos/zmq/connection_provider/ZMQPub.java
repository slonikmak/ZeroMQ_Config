package com.oceanos.zmq.connection_provider;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface ZMQPub<T> extends AutoCloseable{
    void send(T msg) throws JsonProcessingException;
    void bind();
    void connect();
}
