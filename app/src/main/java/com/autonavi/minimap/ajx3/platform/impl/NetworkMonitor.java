package com.autonavi.minimap.ajx3.platform.impl;

import android.net.NetworkInfo;
import com.autonavi.minimap.ajx3.platform.ackor.INetworkMonitor;
import com.autonavi.minimap.ajx3.platform.ackor.INetworkMonitor.NetworkMonitorObserver;
import java.util.ArrayList;
import java.util.List;

public final class NetworkMonitor implements INetworkMonitor {
    private NetworkInfo mLastNetworkInfo = null;
    private List<NetworkMonitorObserver> mObserverList = new ArrayList();

    public final void setNetworkStatus(NetworkInfo networkInfo) {
        this.mLastNetworkInfo = networkInfo;
    }

    public final void onConnectionChanged(NetworkInfo networkInfo) {
        if (this.mObserverList != null) {
            for (NetworkMonitorObserver onConnectionChanged : this.mObserverList) {
                onConnectionChanged.onConnectionChanged(getNetworkStatus(this.mLastNetworkInfo), getNetworkStatus(networkInfo));
            }
        }
        this.mLastNetworkInfo = networkInfo;
    }

    public final void addObserver(NetworkMonitorObserver networkMonitorObserver) {
        this.mObserverList.add(networkMonitorObserver);
    }

    public final void removeObserver(NetworkMonitorObserver networkMonitorObserver) {
        this.mObserverList.remove(networkMonitorObserver);
    }

    public final int getCurrentStatus() {
        return getNetworkStatus(this.mLastNetworkInfo);
    }

    static int getNetworkStatus(NetworkInfo networkInfo) {
        if (networkInfo == null) {
            return 1;
        }
        switch (networkInfo.getType()) {
            case 0:
                return 5;
            case 1:
                return 2;
            default:
                return 1;
        }
    }
}
