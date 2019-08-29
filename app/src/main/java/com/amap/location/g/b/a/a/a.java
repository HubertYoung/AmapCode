package com.amap.location.g.b.a.a;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.RequiresPermission;
import com.autonavi.bundle.uitemplate.mapwidget.inter.WidgetType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: GpsLocationManager */
public class a {
    private C0020a a;
    private C0020a b;

    /* renamed from: com.amap.location.g.b.a.a.a$a reason: collision with other inner class name */
    /* compiled from: GpsLocationManager */
    static class C0020a implements LocationListener {
        /* access modifiers changed from: private */
        public com.amap.location.g.b.a.c.a a;
        /* access modifiers changed from: private */
        public String b;
        private Context c;
        private C0021a d = new C0021a(this);
        /* access modifiers changed from: private */
        public final List<b> e = new ArrayList();
        /* access modifiers changed from: private */
        public long f = Long.MAX_VALUE;
        /* access modifiers changed from: private */
        public float g = Float.MAX_VALUE;
        private Location h;

        /* renamed from: com.amap.location.g.b.a.a.a$a$a reason: collision with other inner class name */
        /* compiled from: GpsLocationManager */
        class C0021a extends BroadcastReceiver {
            private LocationListener b;

            public C0021a(LocationListener locationListener) {
                this.b = locationListener;
            }

            public void onReceive(Context context, Intent intent) {
                if (com.amap.location.g.b.a.a(context).a((String) WidgetType.GPS)) {
                    synchronized (C0020a.this.e) {
                        if (C0020a.this.e.size() > 0) {
                            C0020a.this.a.a(this.b);
                            C0020a.this.a.a(C0020a.this.b, C0020a.this.f, C0020a.this.g, this.b, Looper.getMainLooper());
                        }
                    }
                }
            }
        }

        C0020a(String str, com.amap.location.g.b.a.c.a aVar, Context context) {
            this.a = aVar;
            this.b = str;
            this.c = context;
        }

        /* access modifiers changed from: 0000 */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x002d, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void a(long r9, float r11, android.location.LocationListener r12, android.os.Looper r13) {
            /*
                r8 = this;
                java.util.List<com.amap.location.g.b.a.a.b> r0 = r8.e
                monitor-enter(r0)
                java.util.List<com.amap.location.g.b.a.a.b> r1 = r8.e     // Catch:{ all -> 0x005d }
                java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x005d }
            L_0x0009:
                boolean r2 = r1.hasNext()     // Catch:{ all -> 0x005d }
                if (r2 == 0) goto L_0x002e
                java.lang.Object r2 = r1.next()     // Catch:{ all -> 0x005d }
                com.amap.location.g.b.a.a.b r2 = (com.amap.location.g.b.a.a.b) r2     // Catch:{ all -> 0x005d }
                android.location.LocationListener r3 = r2.a     // Catch:{ all -> 0x005d }
                if (r3 != r12) goto L_0x0009
                long r12 = r2.b     // Catch:{ all -> 0x005d }
                int r12 = (r12 > r9 ? 1 : (r12 == r9 ? 0 : -1))
                if (r12 != 0) goto L_0x0025
                float r12 = r2.c     // Catch:{ all -> 0x005d }
                int r12 = (r12 > r11 ? 1 : (r12 == r11 ? 0 : -1))
                if (r12 == 0) goto L_0x002c
            L_0x0025:
                r2.b = r9     // Catch:{ all -> 0x005d }
                r2.c = r11     // Catch:{ all -> 0x005d }
                r8.a()     // Catch:{ all -> 0x005d }
            L_0x002c:
                monitor-exit(r0)     // Catch:{ all -> 0x005d }
                return
            L_0x002e:
                java.util.List<com.amap.location.g.b.a.a.b> r1 = r8.e     // Catch:{ all -> 0x005d }
                int r1 = r1.size()     // Catch:{ all -> 0x005d }
                if (r1 != 0) goto L_0x0049
                android.content.Context r1 = r8.c     // Catch:{ Exception -> 0x0045 }
                com.amap.location.g.b.a.a.a$a$a r2 = r8.d     // Catch:{ Exception -> 0x0045 }
                android.content.IntentFilter r3 = new android.content.IntentFilter     // Catch:{ Exception -> 0x0045 }
                java.lang.String r4 = "android.location.PROVIDERS_CHANGED"
                r3.<init>(r4)     // Catch:{ Exception -> 0x0045 }
                r1.registerReceiver(r2, r3)     // Catch:{ Exception -> 0x0045 }
                goto L_0x0049
            L_0x0045:
                r1 = move-exception
                com.amap.location.common.d.a.a(r1)     // Catch:{ all -> 0x005d }
            L_0x0049:
                com.amap.location.g.b.a.a.b r7 = new com.amap.location.g.b.a.a.b     // Catch:{ all -> 0x005d }
                r1 = r7
                r2 = r12
                r3 = r9
                r5 = r11
                r6 = r13
                r1.<init>(r2, r3, r5, r6)     // Catch:{ all -> 0x005d }
                java.util.List<com.amap.location.g.b.a.a.b> r9 = r8.e     // Catch:{ all -> 0x005d }
                r9.add(r7)     // Catch:{ all -> 0x005d }
                r8.a()     // Catch:{ all -> 0x005d }
                monitor-exit(r0)     // Catch:{ all -> 0x005d }
                return
            L_0x005d:
                r9 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x005d }
                throw r9
            */
            throw new UnsupportedOperationException("Method not decompiled: com.amap.location.g.b.a.a.a.C0020a.a(long, float, android.location.LocationListener, android.os.Looper):void");
        }

        /* access modifiers changed from: 0000 */
        public void a(LocationListener locationListener) {
            synchronized (this.e) {
                boolean z = false;
                Iterator<b> it = this.e.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    b next = it.next();
                    if (next.a == locationListener) {
                        this.e.remove(next);
                        a();
                        z = true;
                        break;
                    }
                }
                if (this.e.size() == 0 && z) {
                    try {
                        this.c.unregisterReceiver(this.d);
                    } catch (Exception e2) {
                        com.amap.location.common.d.a.a((Throwable) e2);
                    }
                }
            }
        }

        private void a() {
            float f2 = Float.MAX_VALUE;
            long j = Long.MAX_VALUE;
            if (this.e.isEmpty()) {
                this.a.a((LocationListener) this);
                this.h = null;
                this.f = Long.MAX_VALUE;
                this.g = Float.MAX_VALUE;
                return;
            }
            for (b next : this.e) {
                j = Math.min(j, next.b);
                f2 = Math.min(f2, next.c);
            }
            if (!(this.f == j && this.g == f2)) {
                this.f = j;
                this.g = f2;
                this.a.a((LocationListener) this);
                this.a.a(this.b, this.f, this.g, this, Looper.getMainLooper());
            }
        }

        public void onLocationChanged(Location location) {
            if (location != null) {
                float abs = this.h == null ? Float.MAX_VALUE : Math.abs(location.distanceTo(this.h));
                synchronized (this.e) {
                    for (b a2 : this.e) {
                        a2.a(location, abs);
                    }
                }
                this.h = location;
            }
        }

        public void onStatusChanged(String str, int i, Bundle bundle) {
            synchronized (this.e) {
                for (b a2 : this.e) {
                    a2.a(str, i, bundle);
                }
            }
        }

        public void onProviderEnabled(String str) {
            synchronized (this.e) {
                for (b a2 : this.e) {
                    a2.a(str, true);
                }
            }
        }

        public void onProviderDisabled(String str) {
            synchronized (this.e) {
                for (b a2 : this.e) {
                    a2.a(str, false);
                }
            }
        }
    }

    public a(com.amap.location.g.b.a.c.a aVar, Context context) {
        this.a = new C0020a(WidgetType.GPS, aVar, context);
        this.b = new C0020a("passive", aVar, context);
    }

    @RequiresPermission("android.permission.ACCESS_FINE_LOCATION")
    public void a(String str, long j, float f, LocationListener locationListener, Looper looper) {
        if (locationListener != null) {
            C0020a aVar = null;
            if (WidgetType.GPS.equals(str)) {
                aVar = this.a;
            } else if ("passive".equals(str)) {
                aVar = this.b;
            }
            C0020a aVar2 = aVar;
            if (aVar2 != null) {
                aVar2.a(j, f, locationListener, looper);
            }
        }
    }

    public void a(LocationListener locationListener) {
        if (locationListener != null) {
            this.a.a(locationListener);
            this.b.a(locationListener);
        }
    }
}
