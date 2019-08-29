package com.autonavi.minimap.ackor.ackorplatform;

public abstract class IUIThread {
    public native void nativePost(long j);

    /* access modifiers changed from: protected */
    public abstract void post(long j, long j2);
}
