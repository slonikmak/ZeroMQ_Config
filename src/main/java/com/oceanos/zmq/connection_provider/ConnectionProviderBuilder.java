package com.oceanos.zmq.connection_provider;

import groovy.lang.Closure;
import groovy.lang.GroovyObjectSupport;

public class ConnectionProviderBuilder extends GroovyObjectSupport {

    String type;

    ConnectionProviderBuilder(String type){
        this.type = type;
    }

    private ConnectionUnitProvider provider = new ConnectionUnitProvider();


    private void pub(String topic, ConnectionAddress address){
        ConnectionUnit unit = new ConnectionUnit(topic, address);
        provider.addPub(topic, unit);
    }

    private void sub(String topic, ConnectionAddress address){
        ConnectionUnit unit = new ConnectionUnit(topic, address);
        provider.addSub(topic, unit);
    }

    private ConnectionAddress address(Closure closure){
        ConnectionAddressBuilder builder = new ConnectionAddressBuilder();
        closure.setDelegate(builder);
        closure.setResolveStrategy(Closure.DELEGATE_FIRST);
        closure.run();
        ConnectionAddress address =  builder.get(type);
        return address;
    }


    ConnectionUnitProvider getProvider() {
        return provider;
    }
}
