package com.autonavi.bundle.amaphome.compat.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.autonavi.minimap.ajx3.util.RomUtil;

public class NotifyServiceImpl extends Service {
    public static int a = 10000;
    public NotificationManager b;
    public int c;
    public String d;
    public String e;
    private MyBinder f = new MyBinder();

    public class MyBinder extends Binder {
        public MyBinder() {
        }

        public NotifyServiceImpl getService() {
            return NotifyServiceImpl.this;
        }
    }

    public void onCreate() {
        super.onCreate();
        this.b = (NotificationManager) getSystemService("notification");
    }

    @Nullable
    public IBinder onBind(Intent intent) {
        return this.f;
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        super.onStartCommand(intent, i, i2);
        return 1;
    }

    public void onDestroy() {
        super.onDestroy();
        a();
        this.c = 0;
        this.d = "";
        this.e = "";
    }

    public final void a() {
        try {
            if (b()) {
                stopForeground(true);
                this.b.cancel(a);
            } else {
                stopForeground(true);
            }
        } catch (Throwable unused) {
        }
        a = 10000;
    }

    public static boolean b() {
        return RomUtil.ROM_SMARTISAN.equalsIgnoreCase(Build.BRAND) && "OS105".equalsIgnoreCase(Build.MODEL) && (VERSION.SDK_INT == 24 || VERSION.SDK_INT == 25);
    }
}
