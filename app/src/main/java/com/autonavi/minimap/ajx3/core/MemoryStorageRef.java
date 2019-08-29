package com.autonavi.minimap.ajx3.core;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class MemoryStorageRef {
    private final long mShadow;

    private native void nativeClear(long j);

    private native Object nativeGetItem(long j, String str);

    private native void nativeRemoveItem(long j, String str);

    private native void nativeSetItem(long j, String str, Object obj);

    public MemoryStorageRef(long j) {
        this.mShadow = j;
    }

    public void setItem(String str, Object obj) {
        nativeSetItem(this.mShadow, str, obj);
    }

    public Object getItem(String str) {
        return nativeGetItem(this.mShadow, str);
    }

    public void removeItem(String str) {
        nativeRemoveItem(this.mShadow, str);
    }

    public void clear() {
        nativeClear(this.mShadow);
    }

    public long getShadow() {
        return this.mShadow;
    }
}
