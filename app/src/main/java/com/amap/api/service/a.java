package com.amap.api.service;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.text.TextUtils;
import com.amap.location.sdk.a.d;
import com.amap.location.sdk.a.e;
import com.amap.location.sdk.a.f;
import com.amap.location.sdk.a.g;
import com.amap.location.sdk.d.b;
import com.amap.location.sdk.d.c;
import com.amap.location.sdk.fusion.LocationManagerProxy;
import org.json.JSONObject;

/* compiled from: AMapServiceHelper */
public class a {
    private static a p;
    /* access modifiers changed from: private */
    public Context a;
    /* access modifiers changed from: private */
    public HandlerThread b;
    /* access modifiers changed from: private */
    public Handler c;
    /* access modifiers changed from: private */
    public boolean d;
    private Handler e;
    private long f = 0;
    /* access modifiers changed from: private */
    public volatile LocationServiceImpl g = null;
    /* access modifiers changed from: private */
    public b h;
    private LocationProviderServiceImpl i;
    /* access modifiers changed from: private */
    public e j;
    private g k;
    private com.amap.location.sdk.a.b l;
    /* access modifiers changed from: private */
    public com.amap.location.sdk.b.b m;
    /* access modifiers changed from: private */
    public com.amap.location.sdk.b.a n;
    /* access modifiers changed from: private */
    public com.amap.location.sdk.c.a o;
    /* access modifiers changed from: private */
    public c q = new c() {
        public void a(int i) {
        }
    };
    private com.amap.location.sdk.a.g.a r = new com.amap.location.sdk.a.g.a() {
        public JSONObject a() {
            if (a.this.j != null) {
                return a.this.j.a((String) "amap7");
            }
            return null;
        }
    };
    /* access modifiers changed from: private */
    public LocationListener s = new LocationListener() {
        public void onLocationChanged(Location location) {
            if (a.this.g != null) {
                a.this.g.b().onLocationChanged(location);
            }
            com.amap.location.icecream.b.a().a(location);
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            if (a.this.g != null) {
                a.this.g.b().onStatusChanged(str, i, bundle);
            }
        }

        public void onProviderEnabled(String str) {
            if (a.this.g != null) {
                a.this.g.b().onProviderEnabled(str);
            }
        }

        public void onProviderDisabled(String str) {
            if (a.this.g != null) {
                a.this.g.b().onProviderDisabled(str);
            }
        }
    };

    public static a a(Context context) {
        if (p == null) {
            p = new a(context);
        }
        return p;
    }

    private a(Context context) {
        LocationManagerProxy.addCrashInfo("init AMapServiceHelper start");
        this.a = context;
        b();
        LocationManagerProxy.addCrashInfo("init AMapServiceHelper end");
    }

    private void b() {
        com.amap.location.sdk.b.c.a(this.a);
        LocationManagerProxy.addCrashInfo("onCreate start");
        this.f = System.currentTimeMillis();
        LocationManagerProxy.addCrashInfo("onCreate 1");
        this.e = new Handler();
        LocationManagerProxy.addCrashInfo("onCreate 2");
        this.b = new HandlerThread("amapservicehelper") {
            /* JADX INFO: used method not loaded: com.amap.location.common.d.a.a(java.lang.Throwable):null, types can be incorrect */
            /* access modifiers changed from: protected */
            /* JADX WARNING: Code restructure failed: missing block: B:10:0x003d, code lost:
                com.amap.location.common.a.c(com.amap.api.service.a.d(r6.a));
             */
            /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
                com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo("onCreate 10");
                com.amap.api.service.a.a(r6.a, new com.amap.location.sdk.c.a(com.amap.api.service.a.d(r6.a), getLooper()));
                com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo("onCreate 11");
                com.amap.api.service.a.e(r6.a).a();
                com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo("onCreate 12");
             */
            /* JADX WARNING: Code restructure failed: missing block: B:13:0x0073, code lost:
                r0 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:14:0x0074, code lost:
                com.amap.location.common.d.a.a((java.lang.Throwable) r0);
             */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onLooperPrepared() {
                /*
                    r6 = this;
                    com.amap.api.service.a r0 = com.amap.api.service.a.this
                    android.os.HandlerThread r0 = r0.b
                    monitor-enter(r0)
                    java.lang.String r1 = "onCreate 5"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r1)     // Catch:{ all -> 0x0143 }
                    com.amap.api.service.a r1 = com.amap.api.service.a.this     // Catch:{ all -> 0x0143 }
                    android.os.Handler r2 = new android.os.Handler     // Catch:{ all -> 0x0143 }
                    android.os.Looper r3 = r6.getLooper()     // Catch:{ all -> 0x0143 }
                    r2.<init>(r3)     // Catch:{ all -> 0x0143 }
                    r1.c = r2     // Catch:{ all -> 0x0143 }
                    java.lang.String r1 = "onCreate 6"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r1)     // Catch:{ all -> 0x0143 }
                    com.amap.api.service.a r1 = com.amap.api.service.a.this     // Catch:{ all -> 0x0143 }
                    boolean r1 = r1.d     // Catch:{ all -> 0x0143 }
                    if (r1 == 0) goto L_0x003c
                    java.lang.String r1 = "onCreate 7"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r1)     // Catch:{ all -> 0x0143 }
                    com.amap.api.service.a r1 = com.amap.api.service.a.this     // Catch:{ all -> 0x0143 }
                    android.os.Handler r1 = r1.c     // Catch:{ all -> 0x0143 }
                    com.amap.api.service.a$1$1 r2 = new com.amap.api.service.a$1$1     // Catch:{ all -> 0x0143 }
                    r2.<init>()     // Catch:{ all -> 0x0143 }
                    r1.post(r2)     // Catch:{ all -> 0x0143 }
                    monitor-exit(r0)     // Catch:{ all -> 0x0143 }
                    return
                L_0x003c:
                    monitor-exit(r0)     // Catch:{ all -> 0x0143 }
                    com.amap.api.service.a r0 = com.amap.api.service.a.this
                    android.content.Context r0 = r0.a
                    com.amap.location.common.a.c(r0)
                    java.lang.String r0 = "onCreate 10"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r0)     // Catch:{ Exception -> 0x0073 }
                    com.amap.api.service.a r0 = com.amap.api.service.a.this     // Catch:{ Exception -> 0x0073 }
                    com.amap.location.sdk.c.a r1 = new com.amap.location.sdk.c.a     // Catch:{ Exception -> 0x0073 }
                    com.amap.api.service.a r2 = com.amap.api.service.a.this     // Catch:{ Exception -> 0x0073 }
                    android.content.Context r2 = r2.a     // Catch:{ Exception -> 0x0073 }
                    android.os.Looper r3 = r6.getLooper()     // Catch:{ Exception -> 0x0073 }
                    r1.<init>(r2, r3)     // Catch:{ Exception -> 0x0073 }
                    r0.o = r1     // Catch:{ Exception -> 0x0073 }
                    java.lang.String r0 = "onCreate 11"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r0)     // Catch:{ Exception -> 0x0073 }
                    com.amap.api.service.a r0 = com.amap.api.service.a.this     // Catch:{ Exception -> 0x0073 }
                    com.amap.location.sdk.c.a r0 = r0.o     // Catch:{ Exception -> 0x0073 }
                    r0.a()     // Catch:{ Exception -> 0x0073 }
                    java.lang.String r0 = "onCreate 12"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r0)     // Catch:{ Exception -> 0x0073 }
                    goto L_0x0077
                L_0x0073:
                    r0 = move-exception
                    com.amap.location.common.d.a.a(r0)
                L_0x0077:
                    com.amap.api.service.a r0 = com.amap.api.service.a.this
                    com.amap.location.sdk.b.a r1 = new com.amap.location.sdk.b.a
                    com.amap.api.service.a r2 = com.amap.api.service.a.this
                    android.content.Context r2 = r2.a
                    r1.<init>(r2)
                    r0.n = r1
                    java.lang.String r0 = "onCreate 13"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r0)
                    com.amap.api.service.a r0 = com.amap.api.service.a.this
                    android.content.Context r0 = r0.a
                    r1 = 1
                    com.amap.location.sdk.b.a.d.a(r0, r1)
                    java.lang.String r0 = "onCreate 14"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r0)
                    com.amap.api.service.a r0 = com.amap.api.service.a.this
                    com.amap.location.sdk.d.b r2 = new com.amap.location.sdk.d.b
                    com.amap.api.service.a r3 = com.amap.api.service.a.this
                    android.content.Context r3 = r3.a
                    com.amap.api.service.a r4 = com.amap.api.service.a.this
                    android.location.LocationListener r4 = r4.s
                    android.os.Looper r5 = r6.getLooper()
                    r2.<init>(r3, r4, r1, r5)
                    r0.h = r2
                    java.lang.String r0 = "onCreate 15"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r0)
                    com.amap.api.service.a r0 = com.amap.api.service.a.this
                    com.amap.location.sdk.d.b r0 = r0.h
                    com.amap.api.service.a r1 = com.amap.api.service.a.this
                    com.amap.location.sdk.d.c r1 = r1.q
                    r0.a(r1)
                    java.lang.String r0 = "onCreate 16"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r0)
                    com.amap.api.service.a r0 = com.amap.api.service.a.this
                    com.amap.location.sdk.b.b r1 = new com.amap.location.sdk.b.b
                    com.amap.api.service.a r2 = com.amap.api.service.a.this
                    android.content.Context r2 = r2.a
                    r1.<init>(r2)
                    r0.m = r1
                    java.lang.String r0 = "onCreate 17"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r0)
                    com.amap.api.service.a r0 = com.amap.api.service.a.this
                    com.amap.location.sdk.b.b r0 = r0.m
                    r0.a()
                    java.lang.String r0 = "onCreate 18"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r0)
                    com.amap.api.service.a r0 = com.amap.api.service.a.this
                    com.amap.api.service.LocationServiceImpl r0 = r0.g
                    if (r0 == 0) goto L_0x0126
                    java.lang.String r0 = "onCreate 19"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r0)
                    com.amap.api.service.a r0 = com.amap.api.service.a.this
                    com.amap.api.service.LocationServiceImpl r0 = r0.g
                    com.amap.api.service.a r1 = com.amap.api.service.a.this
                    com.amap.location.sdk.d.b r1 = r1.h
                    r0.a(r1)
                    java.lang.String r0 = "onCreate 20"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r0)
                    com.amap.api.service.a r0 = com.amap.api.service.a.this
                    com.amap.api.service.LocationServiceImpl r0 = r0.g
                    com.amap.api.service.a r1 = com.amap.api.service.a.this
                    com.amap.location.sdk.b.b r1 = r1.m
                    r0.a(r1)
                    java.lang.String r0 = "onCreate 21"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r0)
                L_0x0126:
                    java.lang.String r0 = "amapservicehelper"
                    java.lang.StringBuilder r1 = new java.lang.StringBuilder
                    java.lang.String r2 = "loc thread id is :"
                    r1.<init>(r2)
                    int r2 = r6.getThreadId()
                    r1.append(r2)
                    java.lang.String r1 = r1.toString()
                    com.amap.location.common.d.a.b(r0, r1)
                    java.lang.String r0 = "onCreate 22"
                    com.amap.location.sdk.fusion.LocationManagerProxy.addCrashInfo(r0)
                    return
                L_0x0143:
                    r1 = move-exception
                    monitor-exit(r0)     // Catch:{ all -> 0x0143 }
                    throw r1
                */
                throw new UnsupportedOperationException("Method not decompiled: com.amap.api.service.a.AnonymousClass1.onLooperPrepared():void");
            }
        };
        LocationManagerProxy.addCrashInfo("onCreate 3");
        this.b.start();
        LocationManagerProxy.addCrashInfo("onCreate 4");
        com.amap.location.common.d.a.b("amapservicehelper", "loc service is on");
        LocationManagerProxy.addCrashInfo("onCreate end");
    }

    public int a(Intent intent, int i2, int i3) {
        e();
        return 1;
    }

    public IBinder a(Intent intent) {
        String str;
        boolean booleanExtra = intent.getBooleanExtra("foreground", false);
        com.amap.location.common.d.a.b("amapservicehelper", "on bind isAmap:".concat(String.valueOf(booleanExtra)));
        if (!booleanExtra) {
            String stringExtra = intent.getStringExtra("appkey");
            try {
                if (!TextUtils.isEmpty(stringExtra)) {
                    stringExtra = new String(f.a(d.a(stringExtra), com.amap.location.sdk.a.a.b()), "UTF-8");
                }
                str = stringExtra;
            } catch (Exception e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
                str = null;
            }
            if (!TextUtils.isEmpty(str)) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - this.f < 1500) {
                    com.amap.location.sdk.a.c cVar = new com.amap.location.sdk.a.c(this.a, "Wake", str, currentTimeMillis);
                    cVar.start();
                }
                e();
            }
            return c();
        }
        this.e.postDelayed(new Runnable() {
            public void run() {
                a.this.e();
            }
        }, 20000);
        return d();
    }

    public void a() {
        this.e.removeCallbacksAndMessages(null);
        synchronized (this.b) {
            if (this.c != null) {
                this.c.removeCallbacksAndMessages(null);
                this.c.post(new Runnable() {
                    /* JADX WARNING: Failed to process nested try/catch */
                    /* JADX WARNING: Missing exception handler attribute for start block: B:15:0x003f */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public void run() {
                        /*
                            r1 = this;
                            com.amap.api.service.a r0 = com.amap.api.service.a.this
                            com.amap.location.sdk.d.b r0 = r0.h
                            r0.a()
                            com.amap.api.service.a r0 = com.amap.api.service.a.this
                            com.amap.location.sdk.d.b r0 = r0.h
                            r0.b()
                            com.amap.api.service.a r0 = com.amap.api.service.a.this     // Catch:{ Exception -> 0x001c }
                            com.amap.location.sdk.b.a r0 = r0.n     // Catch:{ Exception -> 0x001c }
                            r0.a()     // Catch:{ Exception -> 0x001c }
                            goto L_0x0020
                        L_0x001c:
                            r0 = move-exception
                            com.amap.location.common.d.a.a(r0)
                        L_0x0020:
                            com.amap.api.service.a r0 = com.amap.api.service.a.this     // Catch:{ Exception -> 0x002a }
                            com.amap.location.sdk.b.b r0 = r0.m     // Catch:{ Exception -> 0x002a }
                            r0.b()     // Catch:{ Exception -> 0x002a }
                            goto L_0x002e
                        L_0x002a:
                            r0 = move-exception
                            com.amap.location.common.d.a.a(r0)
                        L_0x002e:
                            com.amap.api.service.a r0 = com.amap.api.service.a.this     // Catch:{ Exception -> 0x0038 }
                            com.amap.location.sdk.c.a r0 = r0.o     // Catch:{ Exception -> 0x0038 }
                            r0.b()     // Catch:{ Exception -> 0x0038 }
                            goto L_0x003c
                        L_0x0038:
                            r0 = move-exception
                            com.amap.location.common.d.a.a(r0)
                        L_0x003c:
                            com.amap.location.sdk.b.a.d.a()     // Catch:{ Exception -> 0x003f }
                        L_0x003f:
                            com.amap.location.icecream.b r0 = com.amap.location.icecream.b.a()     // Catch:{ Exception -> 0x0046 }
                            r0.b()     // Catch:{ Exception -> 0x0046 }
                        L_0x0046:
                            com.amap.api.service.a r0 = com.amap.api.service.a.this
                            android.os.HandlerThread r0 = r0.b
                            r0.quit()
                            return
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.amap.api.service.a.AnonymousClass3.run():void");
                    }
                });
            } else {
                this.d = true;
            }
        }
        if (this.j != null) {
            this.j.a();
        }
        f();
        p = null;
        com.amap.location.common.d.a.b("amapservicehelper", "service destroy");
    }

    private IBinder c() {
        if (this.j == null) {
            this.j = new e();
            this.j.a(this.a);
        }
        if (this.i == null) {
            this.i = new LocationProviderServiceImpl(this.j);
        }
        return this.i;
    }

    private IBinder d() {
        if (this.g == null) {
            this.g = new LocationServiceImpl(this.a);
            this.g.a(this.h);
            this.g.a(this.m);
        }
        return this.g;
    }

    /* access modifiers changed from: private */
    public void e() {
        if (b.b()) {
            if (this.k == null) {
                this.k = new g(this.a, this.r);
                this.k.a();
            }
            if (this.l == null) {
                this.l = new com.amap.location.sdk.a.b(this.a);
                this.l.a();
            }
        }
    }

    private void f() {
        if (b.b()) {
            if (this.l != null) {
                this.l.b();
                this.l = null;
            }
            if (this.k != null) {
                this.k.b();
                this.k = null;
            }
        }
    }
}
