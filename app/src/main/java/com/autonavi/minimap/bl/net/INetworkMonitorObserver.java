package com.autonavi.minimap.bl.net;

import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public interface INetworkMonitorObserver {
    void onNetworkStatusChanged(int i, int i2);
}
