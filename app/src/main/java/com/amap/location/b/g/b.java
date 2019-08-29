package com.amap.location.b.g;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.amap.location.b.a.C0010a;
import com.amap.location.b.f.e;
import com.amap.location.g.b.a;
import com.amap.location.g.b.c;
import com.amap.location.g.b.d;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.List;

/* compiled from: GpsWifiWrapper */
public class b {
    /* access modifiers changed from: private */
    public c a;
    private LocationListener b;
    private boolean c;
    private a d;
    private d e;
    private final Object f = new Object();
    /* access modifiers changed from: private */
    public Context g;
    private Looper h;
    /* access modifiers changed from: private */
    public a i;
    /* access modifiers changed from: private */
    public boolean j;

    public b(Context context, @NonNull C0010a aVar, @NonNull a aVar2, @NonNull Looper looper) {
        this.g = context;
        this.h = looper;
        this.d = a.a(context);
        this.i = aVar2;
        this.a = new c(context, aVar, looper);
        this.b = new LocationListener() {
            public void onProviderDisabled(String str) {
            }

            public void onProviderEnabled(String str) {
            }

            public void onStatusChanged(String str, int i, Bundle bundle) {
            }

            public void onLocationChanged(Location location) {
                if (b.this.j) {
                    try {
                        if (e.a(location)) {
                            if (!e.a(b.this.g, location)) {
                                b.this.b();
                                if (b.this.i != null) {
                                    c.a f = b.this.a.f();
                                    b.this.i.a(location, f.a, f.b, System.currentTimeMillis());
                                }
                            }
                        }
                    } catch (Throwable th) {
                        com.amap.location.common.d.a.a(th);
                    }
                }
            }
        };
        this.e = new d() {
            public void onFirstFix(int i) {
            }

            public void onStarted() {
            }

            public void onStopped() {
            }

            public void onGpsStatusListener(int i, int i2, float f, List<c> list) {
                b.this.a(i);
            }
        };
    }

    public void a(String str, long j2, float f2) {
        synchronized (this.f) {
            this.j = true;
            try {
                List<String> a2 = this.d.a();
                if (a2.contains(WidgetType.GPS) || a2.contains("passive")) {
                    this.d.a(str, j2, 0.0f, this.b, this.h);
                    this.d.a(this.e, this.h);
                }
            } catch (SecurityException e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }
    }

    public void a() {
        synchronized (this.f) {
            this.j = false;
            try {
                this.d.a(this.b);
                this.d.a(this.e);
            } catch (SecurityException e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }
    }

    public void b() {
        if (!this.a.e()) {
            this.a.a();
        }
    }

    public void c() {
        if (this.a.e()) {
            this.a.b();
        }
    }

    /* access modifiers changed from: private */
    public void a(int i2) {
        boolean z = i2 < 4;
        if (this.c != z) {
            this.c = z;
            if (z) {
                this.a.d();
                return;
            }
            this.a.c();
        }
    }
}
