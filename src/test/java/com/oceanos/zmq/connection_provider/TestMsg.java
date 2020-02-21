package com.oceanos.zmq.connection_provider;

public class TestMsg {
    String str;
    int number;

    public TestMsg(String str, int number) {
        this.str = str;
        this.number = number;
    }

    public TestMsg() {
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
