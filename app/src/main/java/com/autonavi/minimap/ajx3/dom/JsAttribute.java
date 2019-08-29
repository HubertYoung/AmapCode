package com.autonavi.minimap.ajx3.dom;

public class JsAttribute {
    public final String key;
    public final String value;

    private native String nativeGetKey(long j);

    private native String nativeGetValue(long j);

    JsAttribute(long j) {
        this.key = nativeGetKey(j);
        this.value = nativeGetValue(j);
    }
}
