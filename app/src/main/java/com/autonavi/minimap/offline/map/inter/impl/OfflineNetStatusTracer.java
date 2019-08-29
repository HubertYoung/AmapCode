package com.autonavi.minimap.offline.map.inter.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.autonavi.amap.app.AMapAppGlobal;
import java.util.ArrayList;
import java.util.List;

public class OfflineNetStatusTracer {
    public static final int STATUS_DISCONNECTION = 0;
    public static final int STATUS_MOBILE = 1;
    public static final int STATUS_WIFI = 2;
    private boolean mIsRegister;
    /* access modifiers changed from: private */
    public List<Listener> mListeners;
    private final BroadcastReceiver mNetReceiver;

    public interface Listener {
        void onNetChanged(int i);
    }

    static final class a {
        /* access modifiers changed from: private */
        public static OfflineNetStatusTracer a = new OfflineNetStatusTracer();
    }

    private OfflineNetStatusTracer() {
        this.mIsRegister = false;
        this.mListeners = new ArrayList();
        this.mNetReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction())) {
                    int netStatus = OfflineNetStatusTracer.this.getNetStatus();
                    for (Listener onNetChanged : OfflineNetStatusTracer.this.mListeners) {
                        onNetChanged.onNetChanged(netStatus);
                    }
                }
            }
        };
    }

    public static OfflineNetStatusTracer get() {
        return a.a;
    }

    public void addListener(Listener listener) {
        if (!this.mListeners.contains(listener)) {
            this.mListeners.add(listener);
        }
    }

    public void removeListener(Listener listener) {
        if (this.mListeners.contains(listener)) {
            this.mListeners.remove(listener);
        }
    }

    public int getNetStatus() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) AMapAppGlobal.getApplication().getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            return 0;
        }
        return activeNetworkInfo.getType() == 1 ? 2 : 1;
    }

    public void begin() {
        if (!this.mIsRegister) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            AMapAppGlobal.getApplication().registerReceiver(this.mNetReceiver, intentFilter);
            this.mIsRegister = true;
        }
    }

    public void end() {
        if (this.mIsRegister) {
            AMapAppGlobal.getApplication().unregisterReceiver(this.mNetReceiver);
            this.mIsRegister = false;
        }
    }
}
