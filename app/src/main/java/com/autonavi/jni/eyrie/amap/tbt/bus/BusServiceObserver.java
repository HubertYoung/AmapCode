package com.autonavi.jni.eyrie.amap.tbt.bus;

public abstract class BusServiceObserver {
    private long mShadow;

    public abstract void onError(int i, int i2, int i3);

    public abstract void onResult(int i, int i2, String str);
}
