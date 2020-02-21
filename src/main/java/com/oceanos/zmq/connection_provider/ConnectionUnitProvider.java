package com.oceanos.zmq.connection_provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ConnectionUnitProvider {

   private Map<String, ConnectionUnit> pubs = new HashMap<>();
   private Map<String, ConnectionUnit> subs = new HashMap<>();

    void addPub(String topic, ConnectionUnit unit){
        pubs.put(topic, unit);
    }

    void addSub(String topic, ConnectionUnit unit){
        subs.put(topic, unit);
    }

    Optional<ConnectionUnit> getPub(String name){
        return Optional.ofNullable(pubs.get(name));
    }

    Optional<ConnectionUnit> getSub(String name){
        return Optional.ofNullable(subs.get(name));
    }

}
