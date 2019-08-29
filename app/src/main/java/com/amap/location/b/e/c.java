package com.amap.location.b.e;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import com.alipay.mobile.common.transport.http.multipart.FilePart;
import com.amap.location.b.c.e;
import com.amap.location.b.f.i;
import com.amap.location.common.a.b;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.Executor;

/* compiled from: UploadManager */
public class c {
    /* access modifiers changed from: private */
    public Context a;
    private Handler b;
    /* access modifiers changed from: private */
    public com.amap.location.b.a c;
    private SharedPreferences d;
    /* access modifiers changed from: private */
    public ConnectivityManager e;
    private BroadcastReceiver f;
    /* access modifiers changed from: private */
    public e g;
    /* access modifiers changed from: private */
    public com.amap.location.b.a.e h;
    /* access modifiers changed from: private */
    public com.amap.location.common.e.c i;
    private b j = new b();
    private a k = new a();
    private Looper l;

    /* compiled from: UploadManager */
    class a implements com.amap.location.common.a.b.a {
        private boolean b;

        public void a() {
        }

        public void a(int i) {
        }

        public void b() {
        }

        public long d(int i) {
            return i == 1 ? 512000 : 51200;
        }

        public long e() {
            return 300000;
        }

        public int f() {
            return 20000;
        }

        public void g() {
        }

        public Executor h() {
            return null;
        }

        private a() {
            this.b = true;
        }

        public boolean b(int i) {
            if (i == 1) {
                return true;
            }
            if (i == 0) {
                return c.this.c.i().a();
            }
            return false;
        }

        public long c(int i) {
            return c.this.a(i);
        }

        public void a(int i, Object obj) {
            c.this.a(i, obj);
        }

        public long c() {
            int i;
            if (!this.b) {
                int d = c.this.g.d();
                if (d <= 0) {
                    this.b = true;
                } else if (d < 512000 && c.this.g.c() > 512000) {
                    this.b = true;
                }
            } else {
                int c = c.this.g.c();
                if (c <= 0) {
                    this.b = false;
                } else if (c < 512000 && c.this.g.d() > 512000) {
                    this.b = false;
                }
            }
            if (this.b) {
                i = c.this.g.c();
            } else {
                i = c.this.g.d();
            }
            if (i > 4000) {
                i = 512000;
            }
            return (long) i;
        }

        public Object a(long j) {
            if (this.b) {
                return c.this.a(true, 10000, j);
            }
            return c.this.a(false, 10000, j);
        }

        public boolean a(Object obj) {
            b bVar = (b) obj;
            byte[] a2 = c.this.h.a(c.this.a, c.this.c, bVar);
            if (a2 == null) {
                return false;
            }
            byte[] bArr = null;
            try {
                boolean z = bVar.b.get(0).b() == 0;
                HashMap hashMap = new HashMap();
                hashMap.put("Content-Type", FilePart.DEFAULT_CONTENT_TYPE);
                com.amap.location.common.e.a aVar = new com.amap.location.common.e.a();
                aVar.b = hashMap;
                aVar.c = a2;
                if (z) {
                    aVar.a = com.amap.location.b.a.a ? "http://aps.testing.amap.com/collection/collectData?src=baseCol&ver=v74&" : "http://cgicol.amap.com/collection/collectData?src=baseCol&ver=v74&";
                } else {
                    aVar.a = com.amap.location.b.a.a ? "http://aps.testing.amap.com/collection/collectData?src=extCol&ver=v74&" : "http://cgicol.amap.com/collection/collectData?src=extCol&ver=v74&";
                }
                com.amap.location.common.e.b a3 = c.this.i.a(aVar);
                if (a3 != null && a3.a == 200) {
                    bArr = a3.c;
                }
                if (bArr == null || !"true".equals(new String(bArr, "UTF-8"))) {
                    return false;
                }
                return true;
            } catch (Exception e) {
                com.amap.location.common.d.a.a((Throwable) e);
                return false;
            }
        }

        public void b(Object obj) {
            c.this.a((b) obj);
            this.b = !this.b;
        }

        public int d() {
            return c.this.c.i().d();
        }
    }

    public c(Context context, Looper looper, e eVar, com.amap.location.common.e.c cVar, com.amap.location.b.a aVar) {
        this.a = context;
        this.l = looper;
        this.g = eVar;
        this.i = cVar;
        this.c = aVar;
        this.e = (ConnectivityManager) context.getSystemService("connectivity");
        this.h = new com.amap.location.b.a.e();
        this.d = context.getSharedPreferences("AMAP_LOCATION_COLLECTOR", 0);
        if (!e()) {
            f();
        }
    }

    public void a() {
        this.b = new Handler(this.l);
        this.j.a(this.a, (com.amap.location.common.a.b.a) this.k, this.l);
        c();
        d();
    }

    private void c() {
        this.f = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                boolean z = true;
                try {
                    NetworkInfo activeNetworkInfo = c.this.e.getActiveNetworkInfo();
                    if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
                        z = false;
                    }
                } catch (Throwable th) {
                    com.amap.location.common.d.a.a(th);
                }
                try {
                    if (!isInitialStickyBroadcast() && z) {
                        c.this.d();
                    }
                } catch (Throwable unused) {
                }
            }
        };
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            this.a.registerReceiver(this.f, intentFilter, null, this.b);
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        try {
            if (g()) {
                this.j.a(500);
            }
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
        }
    }

    public void b() {
        try {
            this.j.a();
            if (this.f != null) {
                this.a.unregisterReceiver(this.f);
                this.f = null;
            }
        } catch (Throwable th) {
            com.amap.location.common.d.a.a(th);
        }
        this.b.removeCallbacksAndMessages(null);
        this.b = null;
    }

    public synchronized long a(int i2) {
        int i3;
        i3 = 0;
        if (i2 == 1) {
            try {
                if (!e()) {
                    f();
                }
                i3 = this.c.i().b() - this.d.getInt("uploaded_wifi_size", 0);
            } finally {
            }
        } else if (i2 == 0) {
            if (!e()) {
                f();
            }
            i3 = this.c.i().c() - this.d.getInt("uploaded_gprs_size", 0);
        }
        return (long) i3;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x003d, code lost:
        return;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void a(int r4, java.lang.Object r5) {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r5 != 0) goto L_0x0005
            monitor-exit(r3)
            return
        L_0x0005:
            boolean r0 = r3.e()     // Catch:{ all -> 0x003e }
            if (r0 != 0) goto L_0x000e
            r3.f()     // Catch:{ all -> 0x003e }
        L_0x000e:
            com.amap.location.b.e.b r5 = (com.amap.location.b.e.b) r5     // Catch:{ all -> 0x003e }
            r0 = 1
            r1 = 0
            if (r4 != r0) goto L_0x0028
            java.lang.String r4 = "uploaded_wifi_size"
            android.content.SharedPreferences r0 = r3.d     // Catch:{ all -> 0x003e }
            java.lang.String r2 = "uploaded_wifi_size"
            int r0 = r0.getInt(r2, r1)     // Catch:{ all -> 0x003e }
            int r5 = r5.c     // Catch:{ all -> 0x003e }
            int r0 = r0 + r5
            r3.a(r4, r0)     // Catch:{ all -> 0x003e }
            monitor-exit(r3)
            return
        L_0x0028:
            if (r4 != 0) goto L_0x003c
            java.lang.String r4 = "uploaded_gprs_size"
            android.content.SharedPreferences r0 = r3.d     // Catch:{ all -> 0x003e }
            java.lang.String r2 = "uploaded_gprs_size"
            int r0 = r0.getInt(r2, r1)     // Catch:{ all -> 0x003e }
            int r5 = r5.c     // Catch:{ all -> 0x003e }
            int r0 = r0 + r5
            r3.a(r4, r0)     // Catch:{ all -> 0x003e }
        L_0x003c:
            monitor-exit(r3)
            return
        L_0x003e:
            r4 = move-exception
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amap.location.b.e.c.a(int, java.lang.Object):void");
    }

    public synchronized b a(boolean z, int i2, long j2) {
        try {
        }
        return this.g.a(z, i2, j2);
    }

    public synchronized void a(b bVar) {
        if (bVar != null) {
            this.g.a(bVar);
        }
    }

    private boolean e() {
        return Calendar.getInstance().get(6) == this.d.getInt("today_value", 0);
    }

    private void f() {
        a((String) "today_value", Calendar.getInstance().get(6));
        a((String) "uploaded_wifi_size", 0);
        a((String) "uploaded_gprs_size", 0);
    }

    private void a(String str, int i2) {
        Editor edit = this.d.edit();
        edit.putInt(str, i2);
        if (VERSION.SDK_INT >= 9) {
            edit.apply();
        } else {
            edit.commit();
        }
    }

    private boolean g() {
        return i.a(this.a) || this.c.i().a();
    }
}
