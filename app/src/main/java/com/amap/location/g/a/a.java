package com.amap.location.g.a;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresPermission;
import android.telephony.CellInfo;
import android.telephony.CellLocation;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneStateListener;
import com.amap.location.g.a.a.c;
import java.util.List;

/* compiled from: AmapTelephonyManager */
public class a {
    private static volatile a c;
    /* access modifiers changed from: private */
    public com.amap.location.g.a.a.a a;
    /* access modifiers changed from: private */
    public com.amap.location.g.a.a.a.a b;
    private Handler d = new Handler(Looper.getMainLooper());

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
        this.b = c.a(context);
        this.d.post(new Runnable() {
            public void run() {
                a.this.a = new com.amap.location.g.a.a.a(a.this.b);
            }
        });
    }

    public void a(PhoneStateListener phoneStateListener, int i, long j, Looper looper) {
        if (phoneStateListener != null) {
            Handler handler = this.d;
            final PhoneStateListener phoneStateListener2 = phoneStateListener;
            final int i2 = i;
            final long j2 = j;
            final Looper looper2 = looper;
            AnonymousClass2 r1 = new Runnable() {
                public void run() {
                    a.this.a.a(phoneStateListener2, i2, j2, looper2);
                }
            };
            handler.post(r1);
        }
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public CellLocation a() {
        if (this.b == null) {
            return null;
        }
        return this.b.a();
    }

    @TargetApi(17)
    @RequiresPermission("android.permission.ACCESS_COARSE_LOCATION")
    public List<CellInfo> b() {
        if (this.b == null) {
            return null;
        }
        return this.b.b();
    }

    public int c() {
        if (this.b == null) {
            return 0;
        }
        return this.b.c();
    }

    public int d() {
        if (this.b == null) {
            return 0;
        }
        return this.b.d();
    }

    public String e() {
        if (this.b == null) {
            return null;
        }
        return this.b.e();
    }

    @Nullable
    @RequiresPermission("android.permission.ACCESS_COARSE_LOCATION")
    @Deprecated
    public List<NeighboringCellInfo> f() {
        if (this.b == null) {
            return null;
        }
        return this.b.f();
    }
}
