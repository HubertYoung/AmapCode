package com.amap.location.g.b.a.c;

import android.location.GnssStatus.Callback;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.GpsStatus.NmeaListener;
import android.location.LocationListener;
import android.location.OnNmeaMessageListener;
import android.os.Looper;
import android.support.annotation.RequiresPermission;
import java.util.List;

/* compiled from: IGpsProvider */
public interface a {
    GpsStatus a(GpsStatus gpsStatus);

    List<String> a();

    @Deprecated
    void a(NmeaListener nmeaListener);

    void a(LocationListener locationListener);

    void a(OnNmeaMessageListener onNmeaMessageListener);

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    void a(String str, long j, float f, LocationListener locationListener, Looper looper);

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    boolean a(Callback callback);

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    @Deprecated
    boolean a(Listener listener);

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    @Deprecated
    boolean a(NmeaListener nmeaListener, Looper looper);

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    boolean a(OnNmeaMessageListener onNmeaMessageListener, Looper looper);

    boolean a(String str);

    void b(Callback callback);

    @Deprecated
    void b(Listener listener);
}
