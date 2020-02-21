package com.oceanos.zmq.connection_provider;

import java.util.HashMap;
import java.util.Map;

public class ConnectionAddressBuilder {

    Map<String, ConnectionAddress> addresses = new HashMap<>();

    void debug(String host, int port, String protocol){
        put("DEBUG", host, port, protocol);
    }

    void prod(String host, int port, String protocol){
        put("PROD", host, port, protocol);
    }

    void test(String host, int port, String protocol){
        put("TEST", host, port, protocol);
    }

    private void put(String type, String host, int port, String protocol){
        addresses.put(type, new ConnectionAddress(protocol, host, port));
    }

    ConnectionAddress get(String type){
        return addresses.get(type);
    }
}
