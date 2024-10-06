package com.hllolluni.common_module;

public enum ReaderStatus {
    AVAILABLE("available"), READING("reading"), UNAVAILABLE("unavailable");

    public String state;
    private ReaderStatus(String state){
        this.state = state;
    }
}
