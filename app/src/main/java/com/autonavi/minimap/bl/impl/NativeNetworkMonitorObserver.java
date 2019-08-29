package com.autonavi.minimap.bl.impl;

import com.autonavi.minimap.bl.net.INetworkMonitorObserver;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
class NativeNetworkMonitorObserver implements INetworkMonitorObserver {
    private long mShadow;

    private native void nativeNetworkStatusChanged(int i, int i2, long j);

    NativeNetworkMonitorObserver() {
    }

    public void onNetworkStatusChanged(int i, int i2) {
        nativeNetworkStatusChanged(i, i2, this.mShadow);
    }
}
