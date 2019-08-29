package com.amap.location.g.a.a.a;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import com.amap.location.common.d.a;
import java.util.List;

/* compiled from: SystemTelephonyProvider */
public class b implements a {
    private TelephonyManager a;

    public b(Context context) {
        this.a = (TelephonyManager) context.getSystemService("phone");
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public CellLocation a() {
        try {
            if (this.a == null) {
                return null;
            }
            return this.a.getCellLocation();
        } catch (SecurityException e) {
            a.a("systel", "", e);
            return null;
        } catch (Exception e2) {
            a.a("systel", "", e2);
            return null;
        }
    }

    @RequiresPermission("android.permission.ACCESS_COARSE_LOCATION")
    public List<CellInfo> b() {
        if (VERSION.SDK_INT < 17) {
            return null;
        }
        try {
            if (this.a == null) {
                return null;
            }
            return this.a.getAllCellInfo();
        } catch (SecurityException e) {
            a.a("systel", "", e);
            return null;
        }
    }

    public int c() {
        try {
            if (this.a == null) {
                return 0;
            }
            return this.a.getNetworkType();
        } catch (Exception e) {
            a.a("systel", "", e);
            return 0;
        }
    }

    public int d() {
        if (this.a == null) {
            return 0;
        }
        return this.a.getPhoneType();
    }

    public String e() {
        if (this.a == null) {
            return null;
        }
        return this.a.getNetworkOperator();
    }

    @Nullable
    @RequiresPermission("android.permission.ACCESS_COARSE_LOCATION")
    @Deprecated
    public List<NeighboringCellInfo> f() {
        try {
            if (this.a == null) {
                return null;
            }
            return this.a.getNeighboringCellInfo();
        } catch (SecurityException e) {
            a.a("systel", "", e);
            return null;
        }
    }

    public void a(PhoneStateListener phoneStateListener, int i) {
        try {
            if (this.a != null) {
                this.a.listen(phoneStateListener, i);
            }
        } catch (SecurityException e) {
            a.a("systel", "", e);
        }
    }
}
