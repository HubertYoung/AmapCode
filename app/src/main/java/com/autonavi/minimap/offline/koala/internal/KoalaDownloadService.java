package com.autonavi.minimap.offline.koala.internal;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class KoalaDownloadService extends Service {
    private KoalaDownloadBinder mBinder;

    public void onCreate() {
        super.onCreate();
        this.mBinder = new KoalaDownloadBinder();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        return 2;
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public void onDestroy() {
        this.mBinder.onDestroy();
        super.onDestroy();
    }
}
