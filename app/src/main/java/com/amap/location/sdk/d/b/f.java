package com.amap.location.sdk.d.b;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.sdk.b.a.c;
import com.amap.location.sdk.b.a.d;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import org.json.JSONObject;

/* compiled from: OutdoorLocationProvider */
public class f {
    /* access modifiers changed from: private */
    public static int d = 10000;
    /* access modifiers changed from: private */
    public int a = 0;
    /* access modifiers changed from: private */
    public long b;
    private float c;
    /* access modifiers changed from: private */
    public boolean e;
    /* access modifiers changed from: private */
    public Handler f;
    /* access modifiers changed from: private */
    public d g;
    /* access modifiers changed from: private */
    public e h;
    /* access modifiers changed from: private */
    public com.amap.location.sdk.d.a i;
    /* access modifiers changed from: private */
    public a j = new a();
    private Context k;
    /* access modifiers changed from: private */
    public Runnable l = new Runnable() {
        public void run() {
            com.amap.location.common.d.a.b("outloc", "gps timeout");
            f.this.g.b();
            f.this.e();
        }
    };
    private com.amap.location.sdk.d.a m = new com.amap.location.sdk.d.a() {
        public void a(int i) {
            f.this.i.a(i);
            if (i == 1) {
                d.a(111);
            }
        }

        public void onLocationChanged(Location location) {
            f.this.e = true;
            if (f.this.h.c()) {
                f.this.h.a();
            }
            f.this.j.a();
            f.this.g.c();
            f.this.i.onLocationChanged(location);
            f.this.f.removeCallbacks(f.this.l);
            f.this.f.postDelayed(f.this.l, (long) f.d);
            d.a(106);
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            f.this.i.onStatusChanged(str, i, bundle);
        }

        public void onProviderEnabled(String str) {
            f.this.i.onProviderEnabled(str);
        }

        public void onProviderDisabled(String str) {
            f.this.i.onProviderDisabled(str);
            if (WidgetType.GPS.equals(str)) {
                f.this.f.removeCallbacks(f.this.l);
                com.amap.location.common.d.a.b("outloc", "gps closed");
                f.this.e();
            }
        }
    };
    private com.amap.location.sdk.d.b.e.a n = new com.amap.location.sdk.d.b.e.a() {
        public void a(AmapLoc amapLoc) {
            StringBuilder sb = new StringBuilder("netloc timeout loc == null ? ");
            sb.append(amapLoc == null);
            com.amap.location.common.d.a.b("outloc", sb.toString());
            if (amapLoc == null) {
                f.this.j.a();
            }
            f.this.j.a(2);
            d.a(112);
        }
    };

    /* compiled from: OutdoorLocationProvider */
    class a implements com.amap.location.sdk.d.a {
        /* access modifiers changed from: private */
        public Location b;
        /* access modifiers changed from: private */
        public volatile Location c;
        /* access modifiers changed from: private */
        public volatile boolean d;
        /* access modifiers changed from: private */
        public Runnable e = new Runnable() {
            /* JADX WARNING: Can't wrap try/catch for region: R(5:10|(2:16|(3:18|19|20))|21|22|(1:24)) */
            /* JADX WARNING: Code restructure failed: missing block: B:27:0x00ac, code lost:
                return;
             */
            /* JADX WARNING: Missing exception handler attribute for start block: B:21:0x0068 */
            /* JADX WARNING: Removed duplicated region for block: B:24:0x0092  */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void run() {
                /*
                    r7 = this;
                    com.amap.location.sdk.d.b.f$a r0 = com.amap.location.sdk.d.b.f.a.this
                    java.lang.Runnable r0 = r0.e
                    monitor-enter(r0)
                    com.amap.location.sdk.d.b.f$a r1 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.b.f r1 = com.amap.location.sdk.d.b.f.this     // Catch:{ all -> 0x00ad }
                    int r1 = r1.a     // Catch:{ all -> 0x00ad }
                    r2 = 2
                    r1 = r1 & r2
                    r3 = 0
                    if (r1 != r2) goto L_0x00a6
                    com.amap.location.sdk.d.b.f$a r1 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.b.f r1 = com.amap.location.sdk.d.b.f.this     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.b.e r1 = r1.h     // Catch:{ all -> 0x00ad }
                    boolean r1 = r1.c()     // Catch:{ all -> 0x00ad }
                    if (r1 != 0) goto L_0x0029
                    com.amap.location.sdk.d.b.f$a r1 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    r1.d = r3     // Catch:{ all -> 0x00ad }
                    monitor-exit(r0)     // Catch:{ all -> 0x00ad }
                    return
                L_0x0029:
                    com.amap.location.sdk.d.b.f$a r1 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    android.location.Location r1 = r1.b     // Catch:{ all -> 0x00ad }
                    if (r1 == 0) goto L_0x0068
                    com.amap.location.sdk.d.b.f$a r1 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    android.location.Location r1 = r1.c     // Catch:{ all -> 0x00ad }
                    if (r1 == 0) goto L_0x0068
                    com.amap.location.sdk.d.b.f$a r1 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    android.location.Location r1 = r1.c     // Catch:{ all -> 0x00ad }
                    long r3 = r1.getTime()     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.b.f$a r1 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    android.location.Location r1 = r1.b     // Catch:{ all -> 0x00ad }
                    long r5 = r1.getTime()     // Catch:{ all -> 0x00ad }
                    int r1 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
                    if (r1 != 0) goto L_0x0068
                    com.amap.location.sdk.d.b.f$a r1 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    android.location.Location r1 = r1.c     // Catch:{ all -> 0x00ad }
                    android.os.Bundle r1 = r1.getExtras()     // Catch:{ all -> 0x00ad }
                    if (r1 == 0) goto L_0x0068
                    java.lang.String r3 = "isLast"
                    r4 = 1
                    r1.putBoolean(r3, r4)     // Catch:{ Throwable -> 0x0068 }
                    java.lang.String r3 = "locType"
                    r1.putInt(r3, r2)     // Catch:{ Throwable -> 0x0068 }
                L_0x0068:
                    com.amap.location.sdk.d.b.f$a r1 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.b.f r1 = com.amap.location.sdk.d.b.f.this     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.a r1 = r1.i     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.b.f$a r2 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    android.location.Location r2 = r2.c     // Catch:{ all -> 0x00ad }
                    r1.onLocationChanged(r2)     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.b.f$a r1 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.b.f$a r2 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    android.location.Location r2 = r2.c     // Catch:{ all -> 0x00ad }
                    r1.b = r2     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.b.f$a r1 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.b.f r1 = com.amap.location.sdk.d.b.f.this     // Catch:{ all -> 0x00ad }
                    long r1 = r1.b     // Catch:{ all -> 0x00ad }
                    r3 = 0
                    int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
                    if (r1 <= 0) goto L_0x00ab
                    com.amap.location.sdk.d.b.f$a r1 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.b.f r1 = com.amap.location.sdk.d.b.f.this     // Catch:{ all -> 0x00ad }
                    android.os.Handler r1 = r1.f     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.b.f$a r2 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    com.amap.location.sdk.d.b.f r2 = com.amap.location.sdk.d.b.f.this     // Catch:{ all -> 0x00ad }
                    long r2 = r2.b     // Catch:{ all -> 0x00ad }
                    r1.postDelayed(r7, r2)     // Catch:{ all -> 0x00ad }
                    goto L_0x00ab
                L_0x00a6:
                    com.amap.location.sdk.d.b.f$a r1 = com.amap.location.sdk.d.b.f.a.this     // Catch:{ all -> 0x00ad }
                    r1.d = r3     // Catch:{ all -> 0x00ad }
                L_0x00ab:
                    monitor-exit(r0)     // Catch:{ all -> 0x00ad }
                    return
                L_0x00ad:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch:{ all -> 0x00ad }
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.d.b.f.a.AnonymousClass1.run():void");
            }
        };

        public void onProviderDisabled(String str) {
        }

        public void onProviderEnabled(String str) {
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
        }

        a() {
        }

        public void onLocationChanged(Location location) {
            f.this.a(location);
            this.c = location;
            synchronized (this.e) {
                if (!this.d) {
                    f.this.f.removeCallbacks(this.e);
                    f.this.f.post(this.e);
                    this.d = true;
                }
            }
        }

        public void a() {
            synchronized (this.e) {
                if (this.d) {
                    f.this.f.removeCallbacks(this.e);
                    this.d = false;
                }
            }
        }

        public void a(int i) {
            f.this.i.a(i);
        }
    }

    public f(Context context, com.amap.location.sdk.d.a aVar, JSONObject jSONObject, boolean z, Looper looper) {
        this.i = aVar;
        this.k = context;
        this.f = new Handler(looper);
        this.g = new d(context, this.m, looper);
        this.h = new e(context, this.j, jSONObject, z);
        this.h.a(this.n);
    }

    public void a(int i2, long j2, float f2, boolean z) {
        if (i2 != this.a || j2 != this.b || f2 != this.c) {
            boolean z2 = f2 != this.c;
            boolean z3 = j2 != this.b;
            int i3 = i2 & 1;
            if ((i3 != (this.a & 1)) || z3 || z2) {
                if (i3 == 1) {
                    this.g.a(j2, f2);
                    this.f.removeCallbacks(this.l);
                    this.f.postDelayed(this.l, (long) d);
                } else {
                    this.g.a();
                    this.f.removeCallbacks(this.l);
                    this.e = false;
                }
            }
            if (!this.e) {
                if ((i2 & 2) == 2) {
                    if (!this.h.c() || z3) {
                        this.h.a((int) j2, z);
                    }
                } else if (this.h.c()) {
                    this.h.a();
                    this.j.a();
                }
            }
            this.a = i2;
            this.b = j2;
            this.c = f2;
        }
    }

    public void a(JSONObject jSONObject, boolean z) {
        this.h.a(jSONObject, z);
    }

    public void a(boolean z) {
        d = z ? 5000 : 10000;
        this.h.a(z);
    }

    public void b(boolean z) {
        this.h.b(z);
    }

    public String a() {
        return this.h.b();
    }

    public String b() {
        return this.g.d();
    }

    public void c() {
        this.h.d();
    }

    /* access modifiers changed from: private */
    public void e() {
        this.e = false;
        if ((this.a & 2) == 2) {
            this.h.a((int) this.b, false);
        }
        this.m.a(1);
        d.a(116, c.a(this.k));
    }

    /* access modifiers changed from: private */
    public void a(Location location) {
        try {
            Bundle extras = location.getExtras();
            int i2 = 53;
            if (extras != null) {
                switch (extras.getInt("locType", 0)) {
                    case 1:
                        d.a(108);
                        break;
                    case 2:
                        d.a(100042);
                        d.a(107);
                        break;
                    case 3:
                        d.a(100048);
                        d.a(107);
                        break;
                    case 4:
                        d.a(100044);
                        i2 = 54;
                        break;
                }
            }
            i2 = -1;
            if (i2 > 0) {
                d.a(100004, c.a(location, i2));
            }
        } catch (Exception unused) {
        }
    }
}
