package com.oceanos.zmq.connection_provider;

import java.io.IOException;

public interface ZMQSub<T> extends AutoCloseable{
    T receive() throws IOException;
    void bind();
    void connect();
}
