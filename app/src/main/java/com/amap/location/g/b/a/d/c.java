package com.amap.location.g.b.a.d;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.GnssStatus;
import android.location.GnssStatus.Callback;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.amap.location.g.b.d;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: SatelliteStatusManager */
public class c {
    /* access modifiers changed from: private */
    public final List<a> a = new CopyOnWriteArrayList();
    /* access modifiers changed from: private */
    public com.amap.location.g.b.a.c.a b;
    private Context c;
    private b d = new b();
    /* access modifiers changed from: private */
    public Callback e;
    /* access modifiers changed from: private */
    public Listener f;
    /* access modifiers changed from: private */
    public GpsStatus g;

    /* compiled from: SatelliteStatusManager */
    static class a {
        d a;
        private Handler b;

        /* renamed from: com.amap.location.g.b.a.d.c$a$a reason: collision with other inner class name */
        /* compiled from: SatelliteStatusManager */
        static class C0027a extends Handler {
            private d a;

            C0027a(d dVar, Looper looper) {
                super(looper);
                this.a = dVar;
            }

            public void handleMessage(Message message) {
                switch (message.what) {
                    case 1:
                        this.a.onStarted();
                        return;
                    case 2:
                        this.a.onStopped();
                        return;
                    case 3:
                        this.a.onFirstFix(((Integer) message.obj).intValue());
                        return;
                    case 4:
                        C0028c cVar = (C0028c) message.obj;
                        this.a.onGpsStatusListener(cVar.a, cVar.b, cVar.c, cVar.d);
                        break;
                }
            }
        }

        a(d dVar, Looper looper) {
            this.a = dVar;
            this.b = new C0027a(this.a, looper == null ? Looper.getMainLooper() : looper);
        }

        /* access modifiers changed from: 0000 */
        public boolean a(d dVar, Looper looper) {
            if (looper == null) {
                looper = Looper.getMainLooper();
            }
            return this.a == dVar && this.b.getLooper() == looper;
        }

        /* access modifiers changed from: 0000 */
        public void a(int i, Object obj) {
            Message obtainMessage = this.b.obtainMessage();
            obtainMessage.what = i;
            obtainMessage.obj = obj;
            obtainMessage.sendToTarget();
        }
    }

    /* compiled from: SatelliteStatusManager */
    class b extends BroadcastReceiver {
        private b() {
        }

        public void onReceive(Context context, Intent intent) {
            if (com.amap.location.g.b.a.a(context).a((String) WidgetType.GPS)) {
                synchronized (c.this.a) {
                    if (c.this.a.size() > 0) {
                        try {
                            if (VERSION.SDK_INT >= 24) {
                                if (c.this.e != null) {
                                    c.this.b.b(c.this.e);
                                    c.this.b.a(c.this.e);
                                }
                            } else if (c.this.f != null) {
                                c.this.b.b(c.this.f);
                                c.this.b.a(c.this.f);
                            }
                        } catch (SecurityException e) {
                            try {
                                com.amap.location.common.d.a.a("satestmgr", "", e);
                            } catch (SecurityException e2) {
                                com.amap.location.common.d.a.a("satestmgr", "", e2);
                            }
                        }
                    }
                }
            }
        }
    }

    /* renamed from: com.amap.location.g.b.a.d.c$c reason: collision with other inner class name */
    /* compiled from: SatelliteStatusManager */
    class C0028c {
        int a;
        int b;
        float c;
        List<com.amap.location.g.b.c> d;

        public C0028c(int i, int i2, float f, List<com.amap.location.g.b.c> list) {
            this.a = i;
            this.b = i2;
            this.c = f;
            this.d = list;
        }
    }

    public c(com.amap.location.g.b.a.c.a aVar, Context context) {
        this.b = aVar;
        this.c = context;
        if (VERSION.SDK_INT >= 24) {
            this.e = new Callback() {
                public void onStarted() {
                    c.this.a();
                }

                public void onStopped() {
                    c.this.b();
                }

                public void onFirstFix(int i) {
                    c.this.a(i);
                }

                public void onSatelliteStatusChanged(GnssStatus gnssStatus) {
                    c.this.a(gnssStatus);
                }
            };
        } else {
            this.f = new Listener() {
                public void onGpsStatusChanged(int i) {
                    if (i == 1) {
                        c.this.a();
                    } else if (i == 2) {
                        c.this.b();
                    } else {
                        if (i == 3) {
                            if (c.this.g == null) {
                                c.this.g = c.this.b.a((GpsStatus) null);
                            } else {
                                c.this.b.a(c.this.g);
                            }
                            if (c.this.g != null) {
                                c.this.a(c.this.g.getTimeToFirstFix());
                            }
                        } else if (i == 4) {
                            if (c.this.g == null) {
                                c.this.g = c.this.b.a((GpsStatus) null);
                            } else {
                                c.this.b.a(c.this.g);
                            }
                            if (c.this.g != null) {
                                c.this.a(c.this.g.getSatellites());
                            }
                        }
                    }
                }
            };
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0052 A[Catch:{ SecurityException -> 0x0047 }] */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x0058 A[SYNTHETIC, Splitter:B:30:0x0058] */
    @android.support.annotation.RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a(com.amap.location.g.b.d r5, android.os.Looper r6) {
        /*
            r4 = this;
            r0 = 0
            if (r5 != 0) goto L_0x0004
            return r0
        L_0x0004:
            java.util.List<com.amap.location.g.b.a.d.c$a> r1 = r4.a
            monitor-enter(r1)
            com.amap.location.g.b.a.d.c$a r2 = r4.b(r5)     // Catch:{ all -> 0x0074 }
            if (r2 == 0) goto L_0x0013
            boolean r5 = r2.a(r5, r6)     // Catch:{ all -> 0x0074 }
            monitor-exit(r1)     // Catch:{ all -> 0x0074 }
            return r5
        L_0x0013:
            com.amap.location.g.b.a.d.c$a r2 = new com.amap.location.g.b.a.d.c$a     // Catch:{ all -> 0x0074 }
            r2.<init>(r5, r6)     // Catch:{ all -> 0x0074 }
            java.util.List<com.amap.location.g.b.a.d.c$a> r5 = r4.a     // Catch:{ all -> 0x0074 }
            r5.add(r2)     // Catch:{ all -> 0x0074 }
            java.util.List<com.amap.location.g.b.a.d.c$a> r5 = r4.a     // Catch:{ all -> 0x0074 }
            int r5 = r5.size()     // Catch:{ all -> 0x0074 }
            r6 = 1
            if (r5 != r6) goto L_0x0072
            int r5 = android.os.Build.VERSION.SDK_INT     // Catch:{ SecurityException -> 0x0047 }
            r6 = 24
            if (r5 < r6) goto L_0x003a
            android.location.GnssStatus$Callback r5 = r4.e     // Catch:{ SecurityException -> 0x0047 }
            if (r5 == 0) goto L_0x0050
            com.amap.location.g.b.a.c.a r5 = r4.b     // Catch:{ SecurityException -> 0x0047 }
            android.location.GnssStatus$Callback r6 = r4.e     // Catch:{ SecurityException -> 0x0047 }
            boolean r5 = r5.a(r6)     // Catch:{ SecurityException -> 0x0047 }
        L_0x0038:
            r0 = r5
            goto L_0x0050
        L_0x003a:
            android.location.GpsStatus$Listener r5 = r4.f     // Catch:{ SecurityException -> 0x0047 }
            if (r5 == 0) goto L_0x0050
            com.amap.location.g.b.a.c.a r5 = r4.b     // Catch:{ SecurityException -> 0x0047 }
            android.location.GpsStatus$Listener r6 = r4.f     // Catch:{ SecurityException -> 0x0047 }
            boolean r5 = r5.a(r6)     // Catch:{ SecurityException -> 0x0047 }
            goto L_0x0038
        L_0x0047:
            r5 = move-exception
            java.lang.String r6 = "satestmgr"
            java.lang.String r3 = ""
            com.amap.location.common.d.a.a(r6, r3, r5)     // Catch:{ all -> 0x0074 }
        L_0x0050:
            if (r0 != 0) goto L_0x0058
            java.util.List<com.amap.location.g.b.a.d.c$a> r5 = r4.a     // Catch:{ all -> 0x0074 }
            r5.remove(r2)     // Catch:{ all -> 0x0074 }
            goto L_0x0070
        L_0x0058:
            android.content.Context r5 = r4.c     // Catch:{ Exception -> 0x0067 }
            com.amap.location.g.b.a.d.c$b r6 = r4.d     // Catch:{ Exception -> 0x0067 }
            android.content.IntentFilter r2 = new android.content.IntentFilter     // Catch:{ Exception -> 0x0067 }
            java.lang.String r3 = "android.location.PROVIDERS_CHANGED"
            r2.<init>(r3)     // Catch:{ Exception -> 0x0067 }
            r5.registerReceiver(r6, r2)     // Catch:{ Exception -> 0x0067 }
            goto L_0x0070
        L_0x0067:
            r5 = move-exception
            java.lang.String r6 = "satestmgr"
            java.lang.String r2 = ""
            com.amap.location.common.d.a.a(r6, r2, r5)     // Catch:{ all -> 0x0074 }
        L_0x0070:
            monitor-exit(r1)     // Catch:{ all -> 0x0074 }
            return r0
        L_0x0072:
            monitor-exit(r1)     // Catch:{ all -> 0x0074 }
            return r6
        L_0x0074:
            r5 = move-exception
            monitor-exit(r1)     // Catch:{ all -> 0x0074 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.g.b.a.d.c.a(com.amap.location.g.b.d, android.os.Looper):boolean");
    }

    public void a(d dVar) {
        if (dVar != null) {
            synchronized (this.a) {
                a b2 = b(dVar);
                if (b2 != null) {
                    boolean remove = this.a.remove(b2);
                    if (this.a.size() == 0 && remove) {
                        try {
                            if (VERSION.SDK_INT >= 24) {
                                if (this.e != null) {
                                    this.b.b(this.e);
                                }
                            } else if (this.f != null) {
                                this.b.b(this.f);
                            }
                            this.c.unregisterReceiver(this.d);
                        } catch (Exception e2) {
                            com.amap.location.common.d.a.a("satestmgr", "", e2);
                        }
                    }
                }
            }
        }
    }

    /* access modifiers changed from: private */
    public void a() {
        synchronized (this.a) {
            for (a a2 : this.a) {
                a2.a(1, (Object) null);
            }
        }
    }

    /* access modifiers changed from: private */
    public void b() {
        synchronized (this.a) {
            for (a a2 : this.a) {
                a2.a(2, (Object) null);
            }
        }
    }

    /* access modifiers changed from: private */
    public void a(int i) {
        synchronized (this.a) {
            for (a a2 : this.a) {
                a2.a(3, (Object) Integer.valueOf(i));
            }
        }
    }

    private void a(int i, int i2, float f2, List<com.amap.location.g.b.c> list) {
        synchronized (this.a) {
            for (a a2 : this.a) {
                C0028c cVar = new C0028c(i, i2, f2, list);
                a2.a(4, (Object) cVar);
            }
        }
    }

    private a b(d dVar) {
        for (a next : this.a) {
            if (next.a == dVar) {
                return next;
            }
        }
        return null;
    }

    /* access modifiers changed from: private */
    public void a(GnssStatus gnssStatus) {
        try {
            if (VERSION.SDK_INT >= 24 && gnssStatus != null) {
                int satelliteCount = gnssStatus.getSatelliteCount();
                ArrayList arrayList = new ArrayList();
                int i = 0;
                float f2 = 0.0f;
                for (int i2 = 0; i2 < satelliteCount; i2++) {
                    com.amap.location.g.b.c cVar = new com.amap.location.g.b.c(gnssStatus.usedInFix(i2), gnssStatus.getSvid(i2), gnssStatus.getCn0DbHz(i2), gnssStatus.getElevationDegrees(i2), gnssStatus.getAzimuthDegrees(i2), gnssStatus.getConstellationType(i2));
                    arrayList.add(cVar);
                    if (gnssStatus.usedInFix(i2)) {
                        i++;
                        f2 += gnssStatus.getCn0DbHz(i2);
                    }
                }
                if (i != 0) {
                    f2 /= (float) i;
                }
                a(i, satelliteCount, f2, arrayList);
            }
        } catch (Exception unused) {
        }
    }

    /* access modifiers changed from: private */
    public void a(Iterable<GpsSatellite> iterable) {
        if (iterable != null) {
            float f2 = 0.0f;
            try {
                ArrayList arrayList = new ArrayList();
                int i = 0;
                int i2 = 0;
                for (GpsSatellite next : iterable) {
                    if (next != null) {
                        i2++;
                        com.amap.location.g.b.c cVar = new com.amap.location.g.b.c(next.usedInFix(), next.getPrn(), next.getSnr(), next.getElevation(), next.getAzimuth(), 0);
                        arrayList.add(cVar);
                        if (next.usedInFix()) {
                            i++;
                            f2 += next.getSnr();
                        }
                    }
                }
                if (i != 0) {
                    f2 /= (float) i;
                }
                a(i, i2, f2, arrayList);
            } catch (Exception unused) {
            }
        }
    }
}
