package com.oceanos.zmq.connection_provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.DelegatingScript;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.zeromq.ZContext;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ZMQConnectionProvider {
    final private ConnectionUnitProvider unitProvider;
    final private ZContext context;
    final private ObjectMapper objectMapper;

    public ZMQConnectionProvider(File file, ConfigType configType) throws IOException {
        CompilerConfiguration cc = new CompilerConfiguration();
        cc.setScriptBaseClass(DelegatingScript.class.getName());

        GroovyShell sh = new GroovyShell(ZMQConnectionProvider.class.getClassLoader(), new Binding(), cc);
        DelegatingScript script = (DelegatingScript)sh.parse(file);
        ConnectionProviderBuilder builder = new ConnectionProviderBuilder(configType.toString());
        script.setDelegate(builder);
        script.run();
        unitProvider = builder.getProvider();

        context = new ZContext();
        objectMapper = new ObjectMapper();
    }

    public <T> ZMQPub<T> createZMQPub(String topic) throws IllegalArgumentException{
        Optional<ConnectionUnit> unit = unitProvider.getPub(topic);
        if (unit.isPresent()){
            ZMQPubImpl<T> pub = new ZMQPubImpl<T>(context, unit.get(), objectMapper);
            return pub;
        } else throw new IllegalArgumentException("Can't find connection for topic: "+topic);
    }

    public <T> ZMQSub<T> createZMQSub(String topic, Class<T> type) throws IllegalArgumentException{
        Optional<ConnectionUnit> unit = unitProvider.getSub(topic);
        if (unit.isPresent()){
            ZMQSubImpl<T> sub = new ZMQSubImpl<T>(context, unit.get(), objectMapper, type);
            return sub;
        } else throw new IllegalArgumentException("Can't find connection for topic: "+topic);
    }

    public void close(){
        context.close();
    }


}
