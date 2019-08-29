package com.autonavi.jni.eyrie.amap.tbt;

public abstract class NaviEventReceiver {
    private long mShadow = 0;

    public abstract void onNaviEvent(String str);
}
