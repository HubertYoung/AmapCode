package com.amap.location.g.b;

import android.content.Context;
import android.location.GnssStatus.Callback;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.LocationListener;
import android.os.Build.VERSION;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import com.amap.location.g.b.a.d.b;
import com.amap.location.g.b.a.d.c;
import java.util.List;

/* compiled from: AmapGpsManager */
public class a {
    private static volatile a a;
    private com.amap.location.g.b.a.c.a b;
    private b c;
    private com.amap.location.g.b.a.d.a d;
    private c e;
    private com.amap.location.g.b.a.b.a f;
    private com.amap.location.g.b.a.a.a g;

    public static a a(@NonNull Context context) {
        if (a == null) {
            synchronized (a.class) {
                try {
                    if (a == null) {
                        a = new a(context);
                    }
                }
            }
        }
        return a;
    }

    private a(Context context) {
        this.b = com.amap.location.g.b.a.a.a(context);
        this.e = new c(this.b, context);
        if (VERSION.SDK_INT >= 24) {
            this.d = new com.amap.location.g.b.a.d.a(this.b, context);
        }
        this.c = new b(this.b, context);
        this.f = new com.amap.location.g.b.a.b.a(this.b);
        this.g = new com.amap.location.g.b.a.a.a(this.b, context.getApplicationContext());
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public void a(String str, long j, float f2, LocationListener locationListener, Looper looper) {
        if (locationListener != null) {
            this.g.a(str, j, f2, locationListener, looper);
        }
    }

    public void a(LocationListener locationListener) {
        if (locationListener != null) {
            this.g.a(locationListener);
        }
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public boolean a(d dVar, Looper looper) {
        return (dVar == null || this.e == null || !this.e.a(dVar, looper)) ? false : true;
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public void a(d dVar) {
        if (dVar != null && this.e != null) {
            this.e.a(dVar);
        }
    }

    @RequiresApi(api = 24)
    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    @Deprecated
    public boolean a(Callback callback, Looper looper) {
        return (callback == null || this.d == null || !this.d.a(callback, looper)) ? false : true;
    }

    @RequiresApi(api = 24)
    @Deprecated
    public void a(Callback callback) {
        if (callback != null && this.d != null) {
            this.d.a(callback);
        }
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    @Deprecated
    public boolean a(Listener listener, Looper looper) {
        if (listener == null) {
            return false;
        }
        return this.c.a(listener, looper);
    }

    @Deprecated
    public void a(Listener listener) {
        if (listener != null) {
            this.c.a(listener);
        }
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public boolean a(b bVar, Looper looper) {
        if (bVar == null) {
            return false;
        }
        return this.f.a(bVar, looper);
    }

    public void a(b bVar) {
        if (bVar != null) {
            this.f.a(bVar);
        }
    }

    public boolean a(String str) {
        if (this.b == null) {
            return false;
        }
        return this.b.a(str);
    }

    @Deprecated
    public GpsStatus a(GpsStatus gpsStatus) {
        if (this.b == null) {
            return null;
        }
        return this.b.a(gpsStatus);
    }

    public List<String> a() {
        if (this.b == null) {
            return null;
        }
        return this.b.a();
    }
}
