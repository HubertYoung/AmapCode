package com.amap.location.e;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.alipay.mobile.security.bio.utils.HanziToPinyin.Token;
import com.amap.location.common.model.AmapLoc;
import com.amap.location.common.model.FPS;
import com.amap.location.protocol.LocationRequest;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: LocationScheduler */
public class i implements d {
    /* access modifiers changed from: private */
    public c a;
    /* access modifiers changed from: private */
    public Context b;
    /* access modifiers changed from: private */
    public com.amap.location.e.d.a c;
    /* access modifiers changed from: private */
    public int d = 86400000;
    /* access modifiers changed from: private */
    public volatile b e;
    /* access modifiers changed from: private */
    public ReentrantReadWriteLock f = new ReentrantReadWriteLock();
    /* access modifiers changed from: private */
    public g g;
    /* access modifiers changed from: private */
    public a h;
    private LocationRequest i;
    /* access modifiers changed from: private */
    public Runnable j = new Runnable() {
        public void run() {
            i.this.f.readLock().lock();
            try {
                if (i.this.e != null) {
                    i.this.e.obtainMessage(3).sendToTarget();
                }
            } finally {
                i.this.f.readLock().unlock();
            }
        }
    };

    /* compiled from: LocationScheduler */
    static class a {
        LocationRequest a;
        AmapLoc b;

        a(LocationRequest locationRequest, AmapLoc amapLoc) {
            this.a = locationRequest;
            this.b = amapLoc;
        }
    }

    /* compiled from: LocationScheduler */
    final class b extends Handler {
        private b() {
        }

        public final void handleMessage(Message message) {
            int i = 0;
            switch (message.what) {
                case 1:
                    com.amap.location.common.d.a.b("nlsche", "thread quit");
                    i.this.c.b();
                    i.this.g.d();
                    getLooper().quit();
                    return;
                case 2:
                    i.this.c.a();
                    int h = i.this.d;
                    i.this.d = message.arg1;
                    if (i.this.d < 86400000) {
                        i.this.h = (a) message.obj;
                        boolean z = message.arg2 == 1;
                        if (!z ? !(h != 86400000 || i.this.a.j.h()) : !i.this.a.j.i()) {
                            i = 1;
                        }
                        i.this.g.a(i, z);
                        return;
                    }
                    i.this.h = null;
                    i.this.g.c();
                    return;
                case 3:
                    if (i.this.h != null) {
                        i.this.g.a(0, false);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: LocationScheduler */
    final class c extends HandlerThread {
        public c(String str, int i) {
            super(str, i);
        }

        /* access modifiers changed from: protected */
        public final void onLooperPrepared() {
            i.this.e = new b();
            synchronized (this) {
                notify();
            }
            i.this.g = new g(i.this.b, i.this.a, Looper.myLooper(), i.this);
            i.this.c = new com.amap.location.e.d.a(Looper.myLooper(), i.this.j);
        }
    }

    public i(Context context, c cVar) {
        this.b = context.getApplicationContext();
        this.a = cVar;
    }

    public void b() {
        if (this.g != null) {
            this.g.a();
        }
    }

    public void c() {
        if (this.g != null) {
            this.g.b();
        }
    }

    public void d() {
        boolean z;
        c cVar = new c("LocationScheduler", -4);
        cVar.start();
        synchronized (cVar) {
            z = true;
            while (this.e == null) {
                try {
                    cVar.wait();
                } catch (InterruptedException unused) {
                    z = false;
                }
            }
        }
        com.amap.location.common.d.a.b("nlsche", "start:".concat(String.valueOf(z)));
    }

    public void e() {
        this.f.writeLock().lock();
        b bVar = this.e;
        this.e = null;
        this.f.writeLock().unlock();
        boolean z = true;
        if (bVar != null) {
            bVar.removeCallbacksAndMessages(null);
            bVar.obtainMessage(1).sendToTarget();
        }
        StringBuilder sb = new StringBuilder("stop:");
        if (bVar == null) {
            z = false;
        }
        sb.append(z);
        com.amap.location.common.d.a.b("nlsche", sb.toString());
    }

    public void a(int i2, a aVar, boolean z) {
        StringBuilder sb = new StringBuilder("req:");
        sb.append(i2);
        sb.append(Token.SEPARATOR);
        sb.append(z);
        com.amap.location.common.d.a.b("nlsche", sb.toString());
        int max = Math.max(Math.min(i2, 86400000), 1000);
        this.f.readLock().lock();
        try {
            if (this.e != null) {
                this.e.obtainMessage(2, max, z ? 1 : 0, aVar).sendToTarget();
            }
        } finally {
            this.f.readLock().unlock();
        }
    }

    public void f() {
        com.amap.location.common.d.a.b("nlsche", "remove");
        this.f.readLock().lock();
        try {
            if (this.e != null) {
                this.e.obtainMessage(2, 86400000, 0).sendToTarget();
            }
        } finally {
            this.f.readLock().unlock();
        }
    }

    public void a() {
        this.f.readLock().lock();
        try {
            if (!(this.e == null || this.h == null || this.c == null)) {
                this.c.a((long) this.d);
            }
        } finally {
            this.f.readLock().unlock();
        }
    }

    public void a(LocationRequest locationRequest, AmapLoc amapLoc, FPS fps) {
        a aVar = new a(locationRequest, amapLoc);
        if (aVar.a != null) {
            this.i = aVar.a;
        }
        if (this.h != null) {
            this.h.a(amapLoc, fps);
        }
        this.g.a(amapLoc);
    }

    public String g() {
        LocationRequest locationRequest = this.i;
        if (locationRequest == null || locationRequest.i() == null) {
            return null;
        }
        return locationRequest.i().a();
    }
}
