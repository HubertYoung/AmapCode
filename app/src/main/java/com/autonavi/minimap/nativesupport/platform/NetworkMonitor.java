package com.autonavi.minimap.nativesupport.platform;

import android.app.Application;
import android.net.NetworkInfo;
import com.amap.bundle.logs.AMapLog;
import com.autonavi.amap.app.AMapAppGlobal;
import com.autonavi.minimap.ackor.ackorplatform.INetworkMonitor;
import com.autonavi.minimap.ackor.ackorplatform.INetworkMonitor.NetworkMonitorObserver;
import com.iflytek.tts.TtsService.alc.ALCTtsConstant;
import java.util.ArrayList;
import java.util.List;

public final class NetworkMonitor implements INetworkMonitor {
    private volatile NetworkInfo mLastNetworkInfo = null;
    private List<NetworkMonitorObserver> mObserverList = new ArrayList();

    public NetworkMonitor() {
        AMapLog.logNormalNative(AMapLog.GROUP_COMMON, "P0003", ALCTtsConstant.EVENT_ID_TTS_PLAY_ERROR, "ackor network monitor create!");
    }

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
        Application application = AMapAppGlobal.getApplication();
        if (application != null) {
            NetworkInfo e = aaw.e(application);
            if (e != null) {
                return getNetworkStatus(e);
            }
        }
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
