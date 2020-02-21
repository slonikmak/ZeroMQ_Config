package com.oceanos.zmq.connection_provider;

import groovy.lang.GroovyObjectSupport;

public class ConnectionUnit extends GroovyObjectSupport {
    private ConnectionAddress connectionAddress;
    private String topic;

    ConnectionUnit(String topic, ConnectionAddress connectionAddress) {
        this.connectionAddress = connectionAddress;
        this.topic = topic;
    }

    ConnectionAddress getConnectionAddress() {
        return connectionAddress;
    }

    String getTopic() {
        return topic;
    }
}
