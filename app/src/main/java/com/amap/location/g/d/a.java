package com.amap.location.g.d;

import android.content.ContentResolver;
import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.Build.VERSION;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import com.amap.location.g.d.a.b;
import java.util.List;

/* compiled from: AmapWifiManager */
public class a {
    private static volatile a c;
    private com.amap.location.g.d.a.a.a a;
    private b b;
    private long d;

    /* renamed from: com.amap.location.g.d.a$a reason: collision with other inner class name */
    /* compiled from: AmapWifiManager */
    public interface C0029a {
        void a();
    }

    public static a a(@NonNull Context context) {
        if (c == null) {
            synchronized (a.class) {
                try {
                    if (c == null) {
                        c = new a(context.getApplicationContext());
                    }
                }
            }
        }
        return c;
    }

    private a(Context context) {
        this.a = com.amap.location.g.d.a.a.a(context);
        this.b = new b(context, this.a);
    }

    public boolean a() {
        boolean z;
        if (com.amap.location.g.a.a > 0 && SystemClock.elapsedRealtime() - this.d < com.amap.location.g.a.a) {
            return false;
        }
        try {
            z = "true".equals(String.valueOf(com.amap.location.g.c.a.a(this.a, "startScanActive", new Object[0])));
        } catch (Exception unused) {
            z = false;
        }
        if (!z) {
            z = this.a.b();
        }
        this.d = SystemClock.elapsedRealtime();
        return z;
    }

    public boolean a(C0029a aVar, long j) {
        if (aVar == null || SystemClock.elapsedRealtime() - this.d > j) {
            return a();
        }
        this.b.a(aVar);
        return false;
    }

    public void a(C0029a aVar, Looper looper) {
        if (aVar != null) {
            this.b.a(aVar, looper);
        }
    }

    public void a(C0029a aVar) {
        if (aVar != null) {
            this.b.b(aVar);
        }
    }

    public boolean b(Context context) {
        if (context == null || VERSION.SDK_INT <= 17) {
            return false;
        }
        if (!(context.getPackageManager().checkPermission("android.permission.WRITE_SETTINGS", context.getPackageName()) == 0)) {
            return false;
        }
        ContentResolver contentResolver = context.getContentResolver();
        try {
            if (((Integer) com.amap.location.g.c.a.a("android.provider.Settings$Global", "getInt", new Object[]{contentResolver, "wifi_scan_always_enabled"}, new Class[]{ContentResolver.class, String.class})).intValue() == 0) {
                if (((Boolean) com.amap.location.g.c.a.a("android.provider.Settings$Global", "putInt", new Object[]{contentResolver, "wifi_scan_always_enabled", Integer.valueOf(1)}, new Class[]{ContentResolver.class, String.class, Integer.TYPE})).booleanValue()) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    @Nullable
    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public List<ScanResult> b() {
        return this.a.a();
    }

    @Nullable
    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public WifiInfo c() {
        return this.a.c();
    }

    @RequiresApi(api = 18)
    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public boolean d() {
        if (VERSION.SDK_INT >= 18) {
            return this.a.d();
        }
        return false;
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public int e() {
        return this.a.e();
    }

    @Nullable
    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public DhcpInfo f() {
        return this.a.f();
    }

    public boolean g() {
        return this.a.g();
    }
}
