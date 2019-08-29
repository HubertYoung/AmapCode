package com.amap.location.sdk.fusion;

import android.content.Context;
import android.location.GnssStatus.Callback;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import com.amap.location.common.d.b;
import com.amap.location.sdk.BuildConfig;
import com.amap.location.sdk.b.c;
import com.amap.location.sdk.e.d;
import com.amap.location.sdk.fusion.c.a;
import com.amap.location.sdk.fusion.interfaces.LocationNmeaListener;
import com.amap.location.sdk.fusion.interfaces.LocationSatelliteListener;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import com.autonavi.link.protocol.http.MultipartUtility;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class LocationManagerProxy {
    public static final int AN_LOCATION_ALL = 7;
    public static final int AN_LOCATION_GPS = 1;
    public static final int AN_LOCATION_INDOOR = 4;
    public static final int AN_LOCATION_NETWORK = 2;
    public static final int AN_LOCATION_NONE = 0;
    public static final int PARA_TYPE_AMAP_CLOUD = 6;
    public static final int PARA_TYPE_COMMON = 1;
    public static final int PARA_TYPE_FEEDBACK = 3;
    public static final int PARA_TYPE_FLP = 2;
    public static final int PARA_TYPE_NAVI = 4;
    public static final int PARA_TYPE_TESTURL = 5;
    private static volatile LocationManagerProxy a = null;
    private static Object b = new Object();
    /* access modifiers changed from: private */
    public static boolean g = true;
    private static StringBuffer r = new StringBuffer();
    /* access modifiers changed from: private */
    public Context c;
    /* access modifiers changed from: private */
    public LocationListener d;
    /* access modifiers changed from: private */
    public LocationStatusListener e;
    /* access modifiers changed from: private */
    public boolean f = false;
    /* access modifiers changed from: private */
    public long h = 86400000;
    /* access modifiers changed from: private */
    public float i = 0.0f;
    /* access modifiers changed from: private */
    public int j = 0;
    /* access modifiers changed from: private */
    public JSONObject k;
    private Thread l;
    /* access modifiers changed from: private */
    public volatile Handler m;
    /* access modifiers changed from: private */
    public List<Runnable> n = new ArrayList();
    /* access modifiers changed from: private */
    public a o;
    /* access modifiers changed from: private */
    public a p;
    /* access modifiers changed from: private */
    public a q;
    /* access modifiers changed from: private */
    public boolean s = false;
    /* access modifiers changed from: private */
    public a t = new a() {
        public void a(Location location) {
            Location location2 = new Location(location);
            StringBuilder sb = new StringBuilder("get sub loc callback:");
            sb.append(b.a(location2));
            sb.append(" drive:");
            sb.append(LocationManagerProxy.this.s);
            com.amap.location.common.d.a.b("mainmgrproxy", sb.toString());
            LocationManagerProxy.g = false;
            if (LocationManagerProxy.this.d != null) {
                LocationManagerProxy.this.d.onLocationChanged(location2);
            }
        }

        public void a(String str, int i, Bundle bundle) {
            if (LocationManagerProxy.this.d != null) {
                LocationManagerProxy.this.d.onStatusChanged(str, i, bundle);
            }
        }

        public void a(String str) {
            if (LocationManagerProxy.this.d != null) {
                LocationManagerProxy.this.d.onProviderEnabled(str);
            }
        }

        public void b(String str) {
            if (LocationManagerProxy.this.d != null) {
                LocationManagerProxy.this.d.onProviderDisabled(str);
            }
        }

        public void a() {
            com.amap.location.common.d.a.b("mainmgrproxy", "sub process connect");
            if (LocationManagerProxy.this.q != null) {
                LocationManagerProxy.this.q.b();
                LocationManagerProxy.this.q = LocationManagerProxy.this.o;
                LocationManagerProxy.this.q.a(LocationManagerProxy.this.e);
                if (LocationManagerProxy.this.k != null) {
                    LocationManagerProxy.this.q.a(LocationManagerProxy.this.k);
                }
                if (LocationManagerProxy.this.f) {
                    LocationManagerProxy.this.q.a(LocationManagerProxy.this.j, LocationManagerProxy.this.h, LocationManagerProxy.this.i, LocationManagerProxy.g);
                }
            }
        }

        public void a(int i) {
            com.amap.location.common.d.a.b("mainmgrproxy", "sub process disconnect   reason:".concat(String.valueOf(i)));
            if (LocationManagerProxy.this.q == LocationManagerProxy.this.o) {
                LocationManagerProxy.this.q = LocationManagerProxy.this.p;
                if (LocationManagerProxy.this.k != null) {
                    LocationManagerProxy.this.q.a(LocationManagerProxy.this.k);
                }
                if (LocationManagerProxy.this.f) {
                    LocationManagerProxy.this.q.a(LocationManagerProxy.this.j, LocationManagerProxy.this.h, LocationManagerProxy.this.i, LocationManagerProxy.g);
                }
            }
        }
    };
    /* access modifiers changed from: private */
    public LocationListener u = new LocationListener() {
        public void onLocationChanged(Location location) {
            Location location2 = new Location(location);
            StringBuilder sb = new StringBuilder("get main loc callback:");
            sb.append(com.amap.location.common.d.a.a(b.a(location2)));
            com.amap.location.common.d.a.b("mainmgrproxy", sb.toString());
            LocationManagerProxy.g = false;
            if (LocationManagerProxy.this.d != null) {
                LocationManagerProxy.this.d.onLocationChanged(location2);
            }
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            if (LocationManagerProxy.this.d != null) {
                LocationManagerProxy.this.d.onStatusChanged(str, i, bundle);
            }
        }

        public void onProviderEnabled(String str) {
            if (LocationManagerProxy.this.d != null) {
                LocationManagerProxy.this.d.onProviderEnabled(str);
            }
        }

        public void onProviderDisabled(String str) {
            if (LocationManagerProxy.this.d != null) {
                LocationManagerProxy.this.d.onProviderDisabled(str);
            }
        }
    };

    /* access modifiers changed from: private */
    public boolean a(int i2) {
        return i2 > 0 && i2 <= 7;
    }

    public boolean isFLPValid() {
        return false;
    }

    public static LocationManagerProxy getInstance() {
        if (a == null) {
            synchronized (b) {
                if (a == null) {
                    a = new LocationManagerProxy();
                }
            }
        }
        return a;
    }

    private LocationManagerProxy() {
    }

    public synchronized void init(@NonNull Context context) {
        this.c = context.getApplicationContext();
        c.a(context);
        com.amap.api.service.b.a(this.c);
        com.amap.location.common.b.d(this.c.getPackageName());
        com.amap.location.common.b.b("ABKLWEH8H9LH09NLB5CCAGHK78BYZ89");
        if ("amap_auto".equalsIgnoreCase(com.amap.api.service.b.c())) {
            com.amap.location.common.b.a(3);
        } else {
            com.amap.location.common.b.a(0);
            d.a(context);
        }
        com.amap.location.common.b.a((String) BuildConfig.VERSION_NAME);
        if (this.l == null || this.m == null) {
            this.l = new HandlerThread("locationThread") {
                /* access modifiers changed from: protected */
                public void onLooperPrepared() {
                    synchronized (LocationManagerProxy.this.n) {
                        com.amap.location.common.d.a.b("mainmgrproxy", "loc main process looper ok");
                        Looper looper = getLooper();
                        LocationManagerProxy.this.m = new Handler(looper);
                        com.amap.location.sdk.b.a.d.a(LocationManagerProxy.this.c, false);
                        LocationManagerProxy.this.p = new b(LocationManagerProxy.this.c, LocationManagerProxy.this.u, looper);
                        LocationManagerProxy.this.o = new c(LocationManagerProxy.this.c, LocationManagerProxy.this.t, looper);
                        LocationManagerProxy.this.q = LocationManagerProxy.this.b();
                        if (LocationManagerProxy.this.n.size() > 0) {
                            for (Runnable post : LocationManagerProxy.this.n) {
                                LocationManagerProxy.this.m.post(post);
                            }
                            LocationManagerProxy.this.n.clear();
                        }
                    }
                }
            };
            this.l.start();
        }
    }

    public void destroy() {
        a((Runnable) new Runnable() {
            public void run() {
                com.amap.location.common.d.a.b("mainmgrproxy", "loc main destroy");
                LocationManagerProxy.this.d = null;
                LocationManagerProxy.this.f = false;
                LocationManagerProxy.this.o.c();
                LocationManagerProxy.this.p.c();
            }
        });
    }

    public void setParams(final int i2, final JSONObject jSONObject) {
        a((Runnable) new Runnable() {
            public void run() {
                StringBuilder sb = new StringBuilder("update request mode:");
                sb.append(i2);
                com.amap.location.common.d.a.b("mainmgrproxy", sb.toString());
                int i = i2;
                if (i != 1) {
                    switch (i) {
                        case 3:
                            LocationManagerProxy.this.c();
                            return;
                        case 4:
                            LocationManagerProxy.this.a(jSONObject);
                            return;
                        case 5:
                            LocationManagerProxy.this.b(jSONObject);
                            return;
                        case 6:
                            LocationManagerProxy.this.c(jSONObject);
                            break;
                    }
                } else if (jSONObject != null) {
                    LocationManagerProxy.this.k = jSONObject;
                    LocationManagerProxy.this.q.a(jSONObject);
                    if ((LocationManagerProxy.this.q instanceof b) && "amap_auto".equalsIgnoreCase(com.amap.api.service.b.c())) {
                        String optString = jSONObject.optString(LocationParams.PARA_AUTO_LOG_PATH, "");
                        if (!TextUtils.isEmpty(optString)) {
                            com.amap.location.sdk.e.c.a(optString);
                            d.a(LocationManagerProxy.this.c);
                        }
                    }
                }
            }
        });
    }

    public void setStatusListener(LocationStatusListener locationStatusListener) {
        this.e = locationStatusListener;
        a aVar = this.q;
        if (aVar != null) {
            aVar.a(this.e);
        }
    }

    public synchronized void requestLocationUpdates(int i2, long j2, float f2, LocationListener locationListener) {
        final int i3 = i2;
        final float f3 = f2;
        final LocationListener locationListener2 = locationListener;
        final long j3 = j2;
        AnonymousClass4 r0 = new Runnable() {
            public void run() {
                if (!LocationManagerProxy.this.a(i3)) {
                    StringBuilder sb = new StringBuilder("wrong location type:");
                    sb.append(i3);
                    throw new IllegalArgumentException(sb.toString());
                } else if (f3 >= 0.0f && locationListener2 != null && j3 >= 0) {
                    if (i3 != LocationManagerProxy.this.j || j3 != LocationManagerProxy.this.h || f3 != LocationManagerProxy.this.i || locationListener2 != LocationManagerProxy.this.d || !LocationManagerProxy.this.f) {
                        StringBuilder sb2 = new StringBuilder("request loc:");
                        sb2.append(i3);
                        com.amap.location.common.d.a.b("mainmgrproxy", sb2.toString());
                        if (j3 == 0) {
                            LocationManagerProxy.this.h = 1000;
                        } else {
                            LocationManagerProxy.this.h = j3;
                        }
                        LocationManagerProxy.this.i = f3;
                        LocationManagerProxy.this.j = i3;
                        LocationManagerProxy.this.d = locationListener2;
                        LocationManagerProxy.this.f = true;
                        LocationManagerProxy.this.q.a(i3, LocationManagerProxy.this.h, f3, LocationManagerProxy.g);
                    }
                }
            }
        };
        a((Runnable) r0);
    }

    public synchronized void removeUpdates(LocationListener locationListener) {
        a((Runnable) new Runnable() {
            public void run() {
                com.amap.location.common.d.a.b("mainmgrproxy", "remove loc");
                LocationManagerProxy.this.q.b();
                LocationManagerProxy.this.d = null;
                LocationManagerProxy.this.f = false;
            }
        });
    }

    public boolean isProviderEnabled(int i2) {
        boolean z = false;
        if ((i2 & 1) == 1) {
            try {
                z = com.amap.location.g.b.a.a(this.c).a((String) WidgetType.GPS);
            } catch (Exception unused) {
            }
        }
        if ((i2 & 2) == 2 || (i2 & 4) == 4) {
            return true;
        }
        return z;
    }

    @RequiresApi(api = 24)
    public boolean registerGnssStatusCallback(Callback callback, Looper looper) {
        if (this.c != null) {
            try {
                return com.amap.location.g.b.a.a(this.c).a(callback, looper);
            } catch (SecurityException e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
        }
        return false;
    }

    @RequiresApi(api = 24)
    public void unregisterGnssStatusCallback(Callback callback) {
        if (this.c != null) {
            com.amap.location.g.b.a.a(this.c).a(callback);
        }
    }

    @Deprecated
    public void addGpsStatusListener(Listener listener, Looper looper) {
        if (this.c != null) {
            com.amap.location.g.b.a.a(this.c).a(listener, looper);
        }
    }

    public void removeGpsStatusListener(Listener listener) {
        if (this.c != null) {
            com.amap.location.g.b.a.a(this.c).a(listener);
        }
    }

    public void addSatelliteListener(LocationSatelliteListener locationSatelliteListener, Looper looper) {
        if (!(this.c == null || locationSatelliteListener == null)) {
            try {
                com.amap.location.g.b.a.a(this.c).a(locationSatelliteListener.getAmapSatelliteStatusListener(), looper);
            } catch (SecurityException e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
        }
    }

    public void removeSatelliteListener(LocationSatelliteListener locationSatelliteListener) {
        if (!(this.c == null || locationSatelliteListener == null)) {
            try {
                com.amap.location.g.b.a.a(this.c).a(locationSatelliteListener.getAmapSatelliteStatusListener());
            } catch (SecurityException e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
        }
    }

    public void addNmeaListener(LocationNmeaListener locationNmeaListener, Looper looper) {
        if (!(this.c == null || locationNmeaListener == null)) {
            try {
                com.amap.location.g.b.a.a(this.c).a(locationNmeaListener.getAmapNmeaListener(), looper);
            } catch (SecurityException e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
        }
    }

    public void removeNmeaListener(LocationNmeaListener locationNmeaListener) {
        if (!(this.c == null || locationNmeaListener == null)) {
            try {
                com.amap.location.g.b.a.a(this.c).a(locationNmeaListener.getAmapNmeaListener());
            } catch (SecurityException e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
        }
    }

    public GpsStatus getGpsStatus(GpsStatus gpsStatus) {
        if (gpsStatus != null) {
            return com.amap.location.g.b.a.a(this.c).a(gpsStatus);
        }
        try {
            GpsStatus a2 = com.amap.location.g.b.a.a(this.c).a((GpsStatus) null);
            if (a2 != null) {
                for (GpsSatellite usedInFix : a2.getSatellites()) {
                    usedInFix.usedInFix();
                }
            }
            return a2;
        } catch (Exception unused) {
            return null;
        }
    }

    public String getRequest(String str) {
        if (!TextUtils.isEmpty(str) && this.q != null) {
            return this.q.a(str);
        }
        return null;
    }

    public static String getCrashInfo() {
        try {
            return r.toString();
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
            return "";
        }
    }

    public static void addCrashInfo(String str) {
        try {
            if (!TextUtils.isEmpty(str)) {
                StringBuffer stringBuffer = r;
                stringBuffer.append(str);
                stringBuffer.append(MultipartUtility.LINE_FEED);
            }
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
        }
    }

    private synchronized void a(Runnable runnable) {
        synchronized (this.n) {
            if (this.m == null) {
                this.n.add(runnable);
            } else {
                this.m.post(runnable);
            }
        }
    }

    /* access modifiers changed from: private */
    public a b() {
        if (this.o == null || !this.o.a()) {
            return this.p;
        }
        return this.o;
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x000d  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x000f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void a(org.json.JSONObject r5) {
        /*
            r4 = this;
            r0 = 0
            if (r5 == 0) goto L_0x000a
            java.lang.String r1 = "cmdnavi"
            int r1 = r5.optInt(r1, r0)     // Catch:{ Exception -> 0x000a }
            goto L_0x000b
        L_0x000a:
            r1 = 0
        L_0x000b:
            if (r1 <= 0) goto L_0x000f
            r2 = 1
            goto L_0x0010
        L_0x000f:
            r2 = 0
        L_0x0010:
            r4.s = r2
            com.amap.location.sdk.fusion.a r2 = r4.q
            com.amap.location.sdk.fusion.a r3 = r4.o
            if (r2 != r3) goto L_0x0022
            if (r5 == 0) goto L_0x0022
            com.amap.location.sdk.fusion.a r5 = r4.o
            java.lang.String r2 = "cmdnavi"
            r3 = 0
            r5.a(r2, r1, r0, r3)
        L_0x0022:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.sdk.fusion.LocationManagerProxy.a(org.json.JSONObject):void");
    }

    /* access modifiers changed from: private */
    public void c() {
        d.a();
        com.amap.location.sdk.b.a.d.a(com.amap.location.uptunnel.a.c.a(3));
        com.amap.location.sdk.b.a.d.a(com.amap.location.uptunnel.a.c.a(4));
        if (this.q == this.o) {
            this.o.a((String) LocationParams.PARA_FEEDBAK_ENGINE, 0, 0, (String) null);
        }
    }

    /* access modifiers changed from: private */
    public void b(JSONObject jSONObject) {
        if (this.q == this.o && jSONObject != null) {
            this.o.a((String) LocationParams.PARA_COMMAND_TESTURL, 0, 0, jSONObject.toString());
        }
    }

    /* access modifiers changed from: private */
    public void c(JSONObject jSONObject) {
        if (this.q == this.o && jSONObject != null) {
            this.o.a((String) LocationParams.PARA_COMMAND_AMAP_CLOUD, 0, 0, jSONObject.toString());
        }
    }
}
