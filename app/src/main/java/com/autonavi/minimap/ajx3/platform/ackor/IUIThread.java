package com.autonavi.minimap.ajx3.platform.ackor;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public abstract class IUIThread {
    public native void nativePost(long j);

    /* access modifiers changed from: protected */
    public abstract void post(long j, long j2);
}
