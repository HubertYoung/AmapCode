package com.amap.location.b.b;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.amap.location.b.a.b;
import com.amap.location.b.a.d;
import com.amap.location.b.c.e;
import com.amap.location.b.c.g;
import com.amap.location.b.c.j;
import com.amap.location.g.b.a;
import com.autonavi.widget.ui.BalloonLayout;
import java.util.ArrayList;
import java.util.List;

/* compiled from: TrackCollector */
public class c {
    private Context a;
    private b b;
    private Handler c;
    private final Object d = new Object();
    private e e;
    private d f;
    private com.amap.location.g.b.d g;
    private long h;
    private Location i;
    /* access modifiers changed from: private */
    public long j;
    private List<j> k = new ArrayList();
    /* access modifiers changed from: private */
    public List<com.amap.location.g.b.c> l;
    private g m = new g();

    public c(Context context, e eVar, b bVar, Looper looper) {
        this.a = context;
        this.b = bVar;
        this.e = eVar;
        this.c = new Handler(looper);
        this.f = new d();
    }

    public void a() {
        this.g = new com.amap.location.g.b.d() {
            public void onFirstFix(int i) {
            }

            public void onStarted() {
            }

            public void onStopped() {
            }

            public void onGpsStatusListener(int i, int i2, float f, List<com.amap.location.g.b.c> list) {
                c.this.j = SystemClock.elapsedRealtime();
                c.this.l = list;
            }
        };
        try {
            a.a(this.a).a(this.g, this.c.getLooper());
        } catch (SecurityException e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        } catch (Exception e3) {
            com.amap.location.common.d.a.a((Throwable) e3);
        }
    }

    public void b() {
        try {
            a.a(this.a).a(this.g);
        } catch (SecurityException e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        } catch (Exception e3) {
            com.amap.location.common.d.a.a((Throwable) e3);
        }
        synchronized (this.d) {
            this.c.removeCallbacksAndMessages(null);
            this.c = null;
        }
    }

    public void a(Location location) {
        byte[] b2 = b(location);
        if (b2 != null) {
            this.e.a(1, b2);
        }
    }

    private byte[] b(Location location) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        if (this.i != null && (elapsedRealtime - this.h < 2000 || location.distanceTo(this.i) < 5.0f)) {
            return null;
        }
        this.h = elapsedRealtime;
        this.i = location;
        boolean z = false;
        if (this.b.c() && this.j != 0 && elapsedRealtime - this.j <= BalloonLayout.DEFAULT_DISPLAY_DURATION) {
            z = true;
        }
        com.amap.location.b.f.e.a(this.m, com.amap.location.b.f.e.a(this.k, z, this.l), location, location.getTime(), System.currentTimeMillis());
        return this.f.a(this.a, this.m, this.k, this.b.a());
    }
}
