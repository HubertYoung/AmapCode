package com.amap.location.sdk.d;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.amap.api.service.IndoorLocationProvider;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.sdk.d.a.a;
import com.amap.location.sdk.d.b.f;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.sdk.location.LocationInstrument;
import org.json.JSONObject;

/* compiled from: LocationProvider */
public class b {
    /* access modifiers changed from: private */
    public int a = 0;
    /* access modifiers changed from: private */
    public long b;
    /* access modifiers changed from: private */
    public float c;
    private Handler d;
    /* access modifiers changed from: private */
    public f e;
    /* access modifiers changed from: private */
    public a f;
    /* access modifiers changed from: private */
    public LocationListener g;
    /* access modifiers changed from: private */
    public volatile c h;
    private a i = new a() {
        public void a(int i) {
            if (b.this.h != null) {
                b.this.h.a(i);
            }
        }

        public void onLocationChanged(Location location) {
            b.this.f.a(location);
            if (b.this.f.c() != 1 && AmapLoc.isLocationCorrect(location)) {
                b.this.g.onLocationChanged(location);
            }
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            b.this.g.onStatusChanged(str, i, bundle);
        }

        public void onProviderEnabled(String str) {
            b.this.g.onProviderEnabled(str);
            com.amap.location.common.d.a.b("locprovider", "provider enable:".concat(String.valueOf(str)));
        }

        public void onProviderDisabled(String str) {
            b.this.g.onProviderDisabled(str);
            com.amap.location.common.d.a.b("locprovider", "provider disable:".concat(String.valueOf(str)));
        }
    };
    private com.amap.location.d.b j = new com.amap.location.d.b() {
        public void a(Location location) {
            if (AmapLoc.isLocationCorrect(location)) {
                Bundle extras = location.getExtras();
                if (extras != null) {
                    extras.remove("flpProvider");
                    extras.remove("srcProvider");
                    if (!extras.containsKey("retype")) {
                        extras.putString("retype", "-1");
                    }
                    extras.putFloat(LocationInstrument.OPTIMIZD_ACCURACY_KEY, location.getAccuracy());
                }
                b.this.g.onLocationChanged(location);
            }
        }

        public void a(int i) {
            switch (i) {
                case 0:
                case 2:
                    com.amap.location.common.d.a.b("locprovider", "enter indoor:".concat(String.valueOf(i)));
                    b.this.e.a(0, 0, 0.0f, false);
                    return;
                case 1:
                case 3:
                    com.amap.location.common.d.a.b("locprovider", "enter outdoor:".concat(String.valueOf(i)));
                    b.this.e.a(b.this.a, b.this.b, b.this.c, false);
                    break;
            }
        }
    };

    public b(@NonNull Context context, @NonNull LocationListener locationListener, boolean z, @NonNull Looper looper) {
        Context applicationContext = context.getApplicationContext();
        this.d = new Handler(looper);
        this.g = locationListener;
        JSONObject a2 = com.amap.location.sdk.e.b.a();
        f fVar = new f(applicationContext, this.i, a2, z, looper);
        this.e = fVar;
        this.f = new a(applicationContext, this.j, a2, looper);
    }

    public void a(int i2, long j2, float f2, boolean z) {
        if (i2 < 0 || i2 > 7) {
            throw new IllegalArgumentException("wrong location type:".concat(String.valueOf(i2)));
        } else if (i2 == 4) {
            throw new IllegalArgumentException("type indoor is not used alone");
        } else {
            Handler handler = this.d;
            final int i3 = i2;
            final long j3 = j2;
            final float f3 = f2;
            final boolean z2 = z;
            AnonymousClass1 r1 = new Runnable() {
                public void run() {
                    if (i3 != b.this.a || j3 != b.this.b || f3 != b.this.c) {
                        if ((i3 & 4) != (b.this.a & 4)) {
                            if ((i3 & 4) == 4) {
                                b.this.f.a();
                            } else {
                                b.this.f.b();
                            }
                        }
                        if (!((i3 & 4) == 4 && (b.this.f.c() == 0 || b.this.f.c() == 2))) {
                            b.this.e.a(i3, j3, f3, z2);
                        }
                        b.this.a = i3;
                        b.this.b = j3;
                        b.this.c = f3;
                    }
                }
            };
            handler.post(r1);
        }
    }

    public void a() {
        this.d.removeCallbacksAndMessages(null);
        a(0, 0, 0.0f, false);
    }

    public void a(@NonNull final JSONObject jSONObject, final boolean z) {
        this.d.post(new Runnable() {
            public void run() {
                b.this.e.a(jSONObject, z);
                b.this.f.a(jSONObject);
            }
        });
    }

    public void a(boolean z) {
        this.e.a(z);
    }

    public void b(boolean z) {
        this.e.b(z);
    }

    public String a(String str) {
        if (WidgetType.GPS.equals(str)) {
            return this.e.b();
        }
        if ("network".equals(str)) {
            return this.e.a();
        }
        if (IndoorLocationProvider.NAME.equals(str)) {
            return this.f.d();
        }
        return null;
    }

    public void a(c cVar) {
        this.h = cVar;
    }

    public void b() {
        this.e.c();
    }
}
