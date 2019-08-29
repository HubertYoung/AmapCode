package com.amap.location.g.d.a.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import com.amap.location.common.d.a;
import com.amap.location.g.d.a.C0029a;
import java.util.List;

/* compiled from: SystemWifiProvider */
public class b implements a {
    private WifiManager a;

    public b(Context context) {
        this.a = (WifiManager) context.getSystemService("wifi");
    }

    public void a(Context context, final C0029a aVar) {
        try {
            context.getApplicationContext().registerReceiver(new BroadcastReceiver() {
                C0029a a = aVar;

                public void onReceive(Context context, Intent intent) {
                    if (!(intent == null || !"android.net.wifi.SCAN_RESULTS".equals(intent.getAction()) || this.a == null)) {
                        this.a.a();
                    }
                }
            }, new IntentFilter("android.net.wifi.SCAN_RESULTS"));
        } catch (Throwable unused) {
        }
    }

    @Nullable
    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public List<ScanResult> a() {
        try {
            if (this.a == null) {
                return null;
            }
            return this.a.getScanResults();
        } catch (SecurityException e) {
            a.a("syswifi", "", e);
            return null;
        }
    }

    @RequiresPermission("android.permission.CHANGE_WIFI_STATE")
    public boolean b() {
        boolean z = false;
        try {
            if (this.a != null && this.a.startScan()) {
                z = true;
            }
            return z;
        } catch (SecurityException e) {
            a.a("syswifi", "", e);
            return false;
        } catch (Exception e2) {
            a.a("syswifi", "", e2);
            return false;
        }
    }

    @Nullable
    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public WifiInfo c() {
        try {
            if (this.a == null) {
                return null;
            }
            return this.a.getConnectionInfo();
        } catch (Exception e) {
            a.a("syswifi", "", e);
            return null;
        }
    }

    @RequiresApi(api = 18)
    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public boolean d() {
        try {
            if (this.a == null) {
                return false;
            }
            return this.a.isScanAlwaysAvailable();
        } catch (Exception e) {
            a.a("syswifi", "", e);
            return false;
        }
    }

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public int e() {
        try {
            if (this.a == null) {
                return 4;
            }
            return this.a.getWifiState();
        } catch (SecurityException e) {
            a.a("syswifi", "", e);
            return 4;
        }
    }

    @Nullable
    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    public DhcpInfo f() {
        try {
            if (this.a == null) {
                return null;
            }
            return this.a.getDhcpInfo();
        } catch (Exception e) {
            a.a("syswifi", "", e);
            return null;
        }
    }

    public boolean g() {
        try {
            if (this.a == null) {
                return false;
            }
            return this.a.isWifiEnabled();
        } catch (Exception e) {
            a.a("syswifi", "", e);
            return false;
        }
    }
}
