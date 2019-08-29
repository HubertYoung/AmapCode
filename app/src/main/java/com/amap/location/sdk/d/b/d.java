package com.amap.location.sdk.d.b;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.g.b.a;
import com.amap.location.g.b.c;
import com.amap.location.sdk.e.e;
import com.amap.location.security.Core;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.sdk.location.LocationInstrument;
import java.util.List;

/* compiled from: GpsLocationProvider */
public class d extends c {
    private Context b;
    private a c;
    private Looper d;
    /* access modifiers changed from: private */
    public long[] e = new long[5];
    /* access modifiers changed from: private */
    public long[] f = new long[5];
    /* access modifiers changed from: private */
    public volatile long g;
    private volatile long h;
    /* access modifiers changed from: private */
    public volatile long i;
    /* access modifiers changed from: private */
    public volatile long j;
    /* access modifiers changed from: private */
    public int k = 0;
    /* access modifiers changed from: private */
    public volatile Location l;
    private boolean m = false;
    private volatile boolean n = false;
    private long o = 1000;
    private float p = 0.0f;
    /* access modifiers changed from: private */
    public LocationListener q = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (location != null) {
                long currentTimeMillis = System.currentTimeMillis();
                if (d.this.j == 0) {
                    d.this.j = currentTimeMillis;
                }
                if (d.this.i > 0 && currentTimeMillis - d.this.i > StatisticConfig.MIN_UPLOAD_INTERVAL) {
                    synchronized (d.this.q) {
                        d.this.e[d.this.k] = d.this.i - d.this.g;
                        d.this.f[d.this.k] = currentTimeMillis - d.this.g;
                    }
                    d.this.k = d.this.k + 1;
                    if (d.this.k >= 5) {
                        d.this.k = 0;
                    }
                }
                Bundle extras = location.getExtras();
                if (extras == null) {
                    extras = new Bundle();
                }
                try {
                    extras.putFloat(LocationInstrument.OPTIMIZD_ACCURACY_KEY, location.getAccuracy());
                    extras.putString("retype", "0");
                } catch (Exception unused) {
                }
                location.setExtras(extras);
                if (AmapLoc.isLocationCorrect(location)) {
                    com.amap.location.protocol.a.a.a().a(location);
                }
                d.this.l = location;
                d.this.i = currentTimeMillis;
                d.this.a(location);
            }
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            d.this.a.onStatusChanged(str, i, bundle);
        }

        public void onProviderEnabled(String str) {
            d.this.a.onProviderEnabled(str);
        }

        public void onProviderDisabled(String str) {
            d.this.a.onProviderDisabled(str);
        }
    };
    private com.amap.location.g.b.d r = new com.amap.location.g.b.d() {
        public void onFirstFix(int i) {
        }

        public void onStarted() {
        }

        public void onStopped() {
        }

        public void onGpsStatusListener(int i, int i2, float f, List<c> list) {
            StringBuilder sb = new StringBuilder("get satellite:");
            sb.append(i);
            sb.append(",");
            sb.append(i2);
            sb.append(",");
            sb.append(f);
            com.amap.location.common.d.a.b("gpsloc", sb.toString());
        }
    };

    public d(Context context, com.amap.location.sdk.d.a aVar, Looper looper) {
        super(aVar);
        this.b = context;
        this.d = looper;
        this.c = a.a(context);
    }

    public void a(long j2, float f2) {
        this.n = true;
        this.o = j2;
        this.p = f2;
        if (!e.a(this.b, "android.permission.ACCESS_FINE_LOCATION")) {
            com.amap.location.common.d.a.b("gpsloc", "no permission");
            return;
        }
        try {
            e();
            List<String> a = this.c.a();
            if (a == null) {
                com.amap.location.sdk.b.a.d.a(100001, "no location providers".getBytes());
            } else if (a.contains(WidgetType.GPS)) {
                this.c.a(WidgetType.GPS, j2, f2, this.q, this.d);
                this.q.onStatusChanged("sub_gps_switch", 1, null);
                if (com.amap.location.sdk.b.a.d.b()) {
                    com.amap.location.sdk.b.a.d.a(114, "{\"listn\":1}".getBytes());
                } else {
                    com.amap.location.sdk.b.a.d.a(115, "{\"listn\":1}".getBytes());
                }
            } else {
                com.amap.location.sdk.b.a.d.a(100001, "no gps provider".getBytes());
            }
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
            String message = e2.getMessage();
            if (!TextUtils.isEmpty(message)) {
                com.amap.location.sdk.b.a.d.a(100001, message.getBytes());
            }
            StringBuilder sb = new StringBuilder("gps request error:");
            sb.append(Log.getStackTraceString(e2));
            com.amap.location.common.d.a.c("gpsloc", sb.toString());
        }
    }

    public void a() {
        this.n = false;
        if (this.g > 0) {
            this.h = System.currentTimeMillis();
        }
        this.c.a(this.q);
        this.q.onStatusChanged("sub_gps_switch", 0, null);
        if (com.amap.location.sdk.b.a.d.b()) {
            com.amap.location.sdk.b.a.d.a(114, "{\"listn\":2}".getBytes());
        } else {
            com.amap.location.sdk.b.a.d.a(115, "{\"listn\":2}".getBytes());
        }
    }

    public void b() {
        if (!this.m) {
            try {
                this.c.a(this.r, this.d);
                this.m = true;
            } catch (SecurityException e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
        }
    }

    public void c() {
        if (this.m) {
            try {
                this.c.a(this.r);
                this.m = false;
            } catch (SecurityException e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
        }
    }

    public String d() {
        float f2;
        StringBuilder sb = new StringBuilder();
        boolean a = this.c.a((String) WidgetType.GPS);
        long j2 = this.g / 1000;
        String str = "0";
        String str2 = "0";
        Location location = this.l;
        if (location != null) {
            try {
                String ce = Core.ce(location.getLongitude(), 1);
                try {
                    str2 = Core.ce(location.getLatitude(), 2);
                } catch (Exception unused) {
                }
                str = ce;
            } catch (Exception unused2) {
            }
            f2 = location.getAccuracy();
        } else {
            f2 = 0.0f;
        }
        sb.append("gps:");
        sb.append(a ? 1 : 0);
        sb.append(",");
        sb.append(j2);
        sb.append(",");
        if (this.h != 0) {
            sb.append((this.h / 1000) - j2);
            sb.append(",");
        } else {
            sb.append("0,");
        }
        if (this.j != 0) {
            sb.append((this.j / 1000) - j2);
            sb.append(",");
        } else {
            sb.append("0,");
        }
        if (this.i != 0) {
            sb.append((this.i / 1000) - j2);
            sb.append(",");
            sb.append(str);
            sb.append(",");
            sb.append(str2);
            sb.append(",");
            sb.append(f2);
            sb.append(",");
        }
        synchronized (this.q) {
            int i2 = 0;
            while (i2 < 5) {
                try {
                    sb.append(this.e[i2] / 1000);
                    sb.append(",");
                    sb.append(this.f[i2] / 1000);
                    sb.append(",");
                    i2++;
                } catch (Throwable th) {
                    while (true) {
                        throw th;
                    }
                }
            }
        }
        return sb.toString();
    }

    private void e() {
        this.g = System.currentTimeMillis();
        this.h = 0;
        this.i = 0;
        this.j = 0;
        this.k = 0;
        this.l = null;
        for (int i2 = 0; i2 < this.e.length; i2++) {
            this.e[i2] = 0;
        }
        for (int i3 = 0; i3 < this.f.length; i3++) {
            this.f[i3] = 0;
        }
    }
}
