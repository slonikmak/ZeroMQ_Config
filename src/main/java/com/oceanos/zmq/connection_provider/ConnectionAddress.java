package com.oceanos.zmq.connection_provider;

public class ConnectionAddress {
    private String host;
    private String protocol;
    private int port;

    ConnectionAddress(String protocol, String host, int port) {
        this.host = host;
        this.protocol = protocol;
        this.port = port;
    }

    String getAddress() {
        return protocol+"://"+host+":"+port;
    }

}
