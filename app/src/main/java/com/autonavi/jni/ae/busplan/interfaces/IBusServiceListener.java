package com.autonavi.jni.ae.busplan.interfaces;

public interface IBusServiceListener {
    void onError(int i, int i2);

    void onResult(int i, String str);
}
