package com.xiaomi.push.mpcd;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import com.xiaomi.channel.commonutils.misc.h;
import com.xiaomi.push.mpcd.job.a;
import com.xiaomi.push.mpcd.job.b;
import com.xiaomi.push.mpcd.job.c;
import com.xiaomi.push.mpcd.job.d;
import com.xiaomi.push.mpcd.job.e;
import com.xiaomi.push.mpcd.job.i;
import com.xiaomi.push.mpcd.job.j;
import com.xiaomi.push.mpcd.job.k;
import com.xiaomi.push.mpcd.job.m;
import com.xiaomi.push.service.an;

public class g {
    private static volatile g a;
    private Context b;

    private g(Context context) {
        this.b = context;
    }

    private int a(int i) {
        return Math.max(60, i);
    }

    public static g a(Context context) {
        if (a == null) {
            synchronized (g.class) {
                try {
                    if (a == null) {
                        a = new g(context);
                    }
                }
            }
        }
        return a;
    }

    /* access modifiers changed from: private */
    public void b() {
        h a2 = h.a(this.b);
        an a3 = an.a(this.b);
        SharedPreferences sharedPreferences = this.b.getSharedPreferences("mipush_extra", 0);
        long currentTimeMillis = System.currentTimeMillis();
        long j = sharedPreferences.getLong("first_try_ts", currentTimeMillis);
        if (j == currentTimeMillis) {
            sharedPreferences.edit().putLong("first_try_ts", currentTimeMillis).commit();
        }
        if (Math.abs(currentTimeMillis - j) >= 172800000) {
            boolean a4 = a3.a(com.xiaomi.xmpush.thrift.g.ScreenSizeCollectionSwitch.a(), true);
            boolean a5 = a3.a(com.xiaomi.xmpush.thrift.g.AndroidVnCollectionSwitch.a(), true);
            boolean a6 = a3.a(com.xiaomi.xmpush.thrift.g.AndroidVcCollectionSwitch.a(), true);
            boolean a7 = a3.a(com.xiaomi.xmpush.thrift.g.AndroidIdCollectionSwitch.a(), true);
            boolean a8 = a3.a(com.xiaomi.xmpush.thrift.g.OperatorSwitch.a(), true);
            if (a4 || a5 || a6 || a7 || a8) {
                int a9 = a(a3.a(com.xiaomi.xmpush.thrift.g.DeviceInfoCollectionFrequency.a(), 1209600));
                com.xiaomi.push.mpcd.job.h hVar = r6;
                com.xiaomi.push.mpcd.job.h hVar2 = new com.xiaomi.push.mpcd.job.h(this.b, a9, a4, a5, a6, a7, a8);
                a2.a(hVar, a9, 30);
            }
            boolean a10 = a3.a(com.xiaomi.xmpush.thrift.g.MacCollectionSwitch.a(), true);
            boolean a11 = a3.a(com.xiaomi.xmpush.thrift.g.IMSICollectionSwitch.a(), true);
            boolean a12 = a3.a(com.xiaomi.xmpush.thrift.g.IccidCollectionSwitch.a(), true);
            boolean a13 = a3.a(com.xiaomi.xmpush.thrift.g.DeviceIdSwitch.a(), true);
            if (a10 || a11 || a12 || a13) {
                int a14 = a(a3.a(com.xiaomi.xmpush.thrift.g.DeviceBaseInfoCollectionFrequency.a(), 1209600));
                com.xiaomi.push.mpcd.job.g gVar = new com.xiaomi.push.mpcd.job.g(this.b, a14, a10, a11, a12, a13);
                a2.a(gVar, a14, 30);
            }
            if (a3.a(com.xiaomi.xmpush.thrift.g.AppInstallListCollectionSwitch.a(), true)) {
                int a15 = a(a3.a(com.xiaomi.xmpush.thrift.g.AppInstallListCollectionFrequency.a(), 86400));
                a2.a(new c(this.b, a15), a15, 30);
            }
            if (VERSION.SDK_INT < 21 && a3.a(com.xiaomi.xmpush.thrift.g.AppActiveListCollectionSwitch.a(), true)) {
                int a16 = a(a3.a(com.xiaomi.xmpush.thrift.g.AppActiveListCollectionFrequency.a(), 900));
                a2.a(new b(this.b, a16), a16, 30);
            }
            if (a3.a(com.xiaomi.xmpush.thrift.g.BluetoothCollectionSwitch.a(), true)) {
                int a17 = a(a3.a(com.xiaomi.xmpush.thrift.g.BluetoothCollectionFrequency.a(), 10800));
                a2.a(new d(this.b, a17), a17, 30);
            }
            if (a3.a(com.xiaomi.xmpush.thrift.g.AccountCollectionSwitch.a(), true)) {
                int a18 = a(a3.a(com.xiaomi.xmpush.thrift.g.AccountCollectionFrequency.a(), 604800));
                a2.a(new a(this.b, a18), a18, 30);
            }
            if (a3.a(com.xiaomi.xmpush.thrift.g.WifiCollectionSwitch.a(), true)) {
                int a19 = a(a3.a(com.xiaomi.xmpush.thrift.g.WifiCollectionFrequency.a(), 900));
                a2.a(new k(this.b, a19), a19, 30);
            }
            if (a3.a(com.xiaomi.xmpush.thrift.g.TopAppCollectionSwitch.a(), true)) {
                int a20 = a(a3.a(com.xiaomi.xmpush.thrift.g.TopAppCollectionFrequency.a(), 300));
                a2.a(new i(this.b, a20), a20, 30);
            }
            if (a3.a(com.xiaomi.xmpush.thrift.g.BroadcastActionCollectionSwitch.a(), true)) {
                int a21 = a(a3.a(com.xiaomi.xmpush.thrift.g.BroadcastActionCollectionFrequency.a(), 900));
                a2.a(new e(this.b, a21), a21, 30);
            }
            if (a3.a(com.xiaomi.xmpush.thrift.g.WifiDevicesMacCollectionSwitch.a(), false)) {
                int a22 = a(a3.a(com.xiaomi.xmpush.thrift.g.WifiDevicesMacCollectionFrequency.a(), 900));
                a2.a(new m(this.b, a22), a22, 30);
            }
            if (a3.a(com.xiaomi.xmpush.thrift.g.ActivityTSSwitch.a(), false)) {
                c();
            }
            if (a3.a(com.xiaomi.xmpush.thrift.g.UploadSwitch.a(), true)) {
                a2.a(new j(this.b), a(a3.a(com.xiaomi.xmpush.thrift.g.UploadFrequency.a(), 86400)), 60);
            }
        }
    }

    private boolean c() {
        if (VERSION.SDK_INT >= 14) {
            try {
                ((Application) (this.b instanceof Application ? this.b : this.b.getApplicationContext())).registerActivityLifecycleCallbacks(new a(this.b, String.valueOf(System.currentTimeMillis() / 1000)));
                return true;
            } catch (Exception e) {
                com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
            }
        }
        return false;
    }

    public void a() {
        h.a(this.b).a((Runnable) new h(this), 30);
    }
}
