package com.autonavi.jni.alc.inter;

public interface IALCRecordAppender {
    int getType();

    void write(String str, int i);
}
