package com.autonavi.minimap.bl.net;

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

    void addObserver(INetworkMonitorObserver iNetworkMonitorObserver);

    int getCurrentStatus();

    void removeObserver(INetworkMonitorObserver iNetworkMonitorObserver);
}
