package com.amap.location.b.b;

import android.content.Context;
import android.location.Location;
import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.amap.location.b.a.C0010a;
import com.amap.location.b.c.e;
import com.amap.location.b.c.g;
import com.amap.location.b.c.l;
import com.amap.location.g.b.a;
import java.util.List;

/* compiled from: FpsCollector */
public class b {
    private Context a;
    private Handler b;
    private e c;
    private a d;
    private d e;
    private com.amap.location.g.b.b f;
    /* access modifiers changed from: private */
    public long g;
    private long h;
    private Location i;
    private com.amap.location.b.a.b j;
    private g k = new g();

    public b(Context context, e eVar, C0010a aVar, Looper looper) {
        this.a = context;
        this.c = eVar;
        this.b = new Handler(looper);
        this.d = new a(this.a, looper);
        this.e = new d(this.a, looper);
        this.j = new com.amap.location.b.a.b();
    }

    public void a() {
        this.d.a();
        this.e.a();
        this.f = new com.amap.location.g.b.b() {
            public void onNmeaReceived(long j, String str) {
                b.this.g = j;
            }
        };
        try {
            a.a(this.a).a(this.f, this.b.getLooper());
        } catch (SecurityException e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        } catch (Exception e3) {
            com.amap.location.common.d.a.a((Throwable) e3);
        }
    }

    public void b() {
        try {
            a.a(this.a).a(this.f);
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
        this.b.removeCallbacksAndMessages(null);
        this.d.b();
        this.e.b();
    }

    public void a(Location location, List<ScanResult> list, long j2, long j3) {
        Location location2 = location;
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.i == null || location2.distanceTo(this.i) >= 10.0f) {
            com.amap.location.b.c.b a2 = this.d.a(location2);
            List<l> a3 = this.e.a(location2, list, j2, j3);
            if (!(a2 == null && a3 == null)) {
                com.amap.location.b.f.e.a(this.k, location2, this.g, j3);
                byte[] a4 = this.j.a(this.a, this.k, a2, this.e.c(), a3);
                if (a4 != null) {
                    this.c.a(0, a4);
                }
            }
            this.i = location2;
            this.h = elapsedRealtime;
        }
    }
}
