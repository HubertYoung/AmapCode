package com.autonavi.jni.ae.route.observer;

public interface HttpInterface {
    boolean requestHttpGet(int i, int i2, String str);

    boolean requestHttpPost(int i, int i2, String str, byte[] bArr);
}
