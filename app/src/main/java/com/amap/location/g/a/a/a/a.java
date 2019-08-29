package com.amap.location.g.a.a.a;

import android.annotation.TargetApi;
import android.support.annotation.RequiresPermission;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import java.util.List;

/* compiled from: ITelephonyProvider */
public interface a {
    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    CellLocation a();

    void a(PhoneStateListener phoneStateListener, int i);

    @TargetApi(17)
    @RequiresPermission("android.permission.ACCESS_COARSE_LOCATION")
    List<CellInfo> b();

    int c();

    int d();

    String e();

    @RequiresPermission("android.permission.ACCESS_COARSE_LOCATION")
    @Deprecated
    List<NeighboringCellInfo> f();
}
