package com.autonavi.minimap.dexfilter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class DexServiceProxy extends Service {
    static {
        drx.a("--DexServiceProxy.static initializer :");
    }

    public DexServiceProxy() {
        drx.a("--DexServiceProxy.DexServiceProxy :");
    }

    public void onCreate() {
        super.onCreate();
        drx.a("--DexServiceProxy.onCreate :");
        stopSelf();
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        stopSelf();
        return 2;
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        drx.a("--DexServiceProxy.onBind :");
        return null;
    }

    public boolean onUnbind(Intent intent) {
        drx.a("--DexServiceProxy.onUnbind :");
        return super.onUnbind(intent);
    }

    public void onDestroy() {
        super.onDestroy();
        drx.a("--DexServiceProxy.onDestroy :");
    }
}
