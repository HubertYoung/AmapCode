package com.amap.location.g.d.a.a;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import com.amap.location.g.d.a.C0029a;
import java.util.List;

/* compiled from: IWifiProvider */
public interface a {
    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    List<ScanResult> a();

    void a(@NonNull Context context, @NonNull C0029a aVar);

    @RequiresPermission("android.permission.CHANGE_WIFI_STATE")
    boolean b();

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    WifiInfo c();

    @RequiresApi(api = 18)
    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    boolean d();

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    int e();

    @RequiresPermission("android.permission.ACCESS_WIFI_STATE")
    DhcpInfo f();

    boolean g();
}
