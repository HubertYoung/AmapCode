package com.autonavi.minimap.ajx3.platform.ackor;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface INetworkMonitor {
    public static final int NETWORK_STATUS_2G = 3;
    public static final int NETWORK_STATUS_3G = 4;
    public static final int NETWORK_STATUS_4G = 5;
    public static final int NETWORK_STATUS_NOT_REACHABLE = 1;
    public static final int NETWORK_STATUS_UNKNOWN = 0;
    public static final int NETWORK_STATUS_WIFI = 2;

    @KeepClassMembers
    @Keep
    public static class NetworkMonitorObserver {
        private long mShadow;

        private native void nativeNetworkStatusChanged(int i, int i2, long j);

        public void onConnectionChanged(int i, int i2) {
            nativeNetworkStatusChanged(i, i2, this.mShadow);
        }
    }

    void addObserver(NetworkMonitorObserver networkMonitorObserver);

    int getCurrentStatus();

    void removeObserver(NetworkMonitorObserver networkMonitorObserver);
}
