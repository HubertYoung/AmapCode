package com.alibaba.analytics;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class AnalyticsService extends Service {
    IAnalytics monitor = null;

    public IBinder onBind(Intent intent) {
        if (this.monitor == null) {
            this.monitor = new AnalyticsImp(getApplication());
        }
        return (IBinder) this.monitor;
    }

    public void onDestroy() {
        if (this.monitor != null) {
            try {
                this.monitor.triggerUpload();
            } catch (RemoteException unused) {
            }
        }
        super.onDestroy();
    }

    public void onLowMemory() {
        if (this.monitor != null) {
            try {
                this.monitor.triggerUpload();
            } catch (RemoteException unused) {
            }
        }
        super.onLowMemory();
    }
}
