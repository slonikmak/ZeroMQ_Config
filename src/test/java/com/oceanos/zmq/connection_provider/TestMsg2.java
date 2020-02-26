package com.oceanos.zmq.connection_provider;

public class TestMsg2 {
    String str;
    int value;

    public TestMsg2(String str, int value) {
        this.str = str;
        this.value = value;
    }

    public TestMsg2() {
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
