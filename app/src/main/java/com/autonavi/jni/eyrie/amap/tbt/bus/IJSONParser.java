package com.autonavi.jni.eyrie.amap.tbt.bus;

public interface IJSONParser {
    <T> T parseObject(String str, Class<T> cls);

    String toJSONString(Object obj);
}
