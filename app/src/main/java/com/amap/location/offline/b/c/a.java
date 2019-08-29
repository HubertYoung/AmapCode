package com.amap.location.offline.b.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import com.amap.location.common.f.h;
import com.amap.location.offline.b.b.d;
import com.amap.location.offline.c;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: OfflineDownloader */
public class a {
    /* access modifiers changed from: private */
    public Context a;
    private c b;
    /* access modifiers changed from: private */
    public com.amap.location.offline.a c;
    /* access modifiers changed from: private */
    public C0034a d;
    /* access modifiers changed from: private */
    public ReentrantReadWriteLock e = new ReentrantReadWriteLock();
    private b f;
    /* access modifiers changed from: private */
    public long g;
    private BroadcastReceiver h = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(intent.getAction()) && SystemClock.elapsedRealtime() - a.this.g >= 10000) {
                a.this.e.readLock().lock();
                try {
                    if (a.this.d != null) {
                        a.this.d.removeMessages(1);
                        a.this.d.sendEmptyMessage(1);
                    }
                } finally {
                    a.this.e.readLock().unlock();
                }
            }
        }
    };

    /* renamed from: com.amap.location.offline.b.c.a$a reason: collision with other inner class name */
    /* compiled from: OfflineDownloader */
    class C0034a extends Handler {
        C0034a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 0:
                        if (a.this.e()) {
                            com.amap.location.offline.c.a.a().a(a.this.a);
                            return;
                        }
                        break;
                    case 1:
                        a.this.c();
                        return;
                    case 2:
                        a.this.d();
                        return;
                    case 3:
                        if (a.this.c.c() && com.amap.location.offline.e.c.a(a.this.a, a.this.c.b())) {
                            d.a(a.this.a).c();
                        }
                        d.a(a.this.a).a();
                        d.a(a.this.a).b();
                        return;
                    case 4:
                        a.this.g();
                        getLooper().quit();
                        break;
                }
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }
    }

    /* compiled from: OfflineDownloader */
    class b extends HandlerThread {
        public b(String str, int i) {
            super(str, i);
        }

        /* access modifiers changed from: protected */
        public void onLooperPrepared() {
            a.this.d = new C0034a(Looper.myLooper());
            synchronized (this) {
                notify();
            }
            a.this.g = SystemClock.elapsedRealtime();
            a.this.f();
            a.this.e.readLock().lock();
            try {
                if (a.this.d != null) {
                    a.this.d.removeMessages(0);
                    a.this.d.sendEmptyMessageDelayed(0, 10000);
                    a.this.d.removeMessages(1);
                    a.this.d.sendEmptyMessageDelayed(1, 10000);
                    a.this.d.removeMessages(3);
                    a.this.d.sendEmptyMessageDelayed(3, 15000);
                }
            } finally {
                a.this.e.readLock().unlock();
            }
        }
    }

    public a(@NonNull Context context, @NonNull c cVar, @NonNull com.amap.location.offline.a aVar) {
        this.a = context;
        this.b = cVar;
        this.c = aVar;
        this.f = new b(this.a, this.b, this.c, new a() {
            public void a() {
                a.this.e.readLock().lock();
                try {
                    if (a.this.d != null) {
                        a.this.d.removeMessages(2);
                        a.this.d.sendMessage(a.this.d.obtainMessage(2));
                    }
                } finally {
                    a.this.e.readLock().unlock();
                }
            }
        });
    }

    public void a() {
        b bVar = new b("OfflineDownloader", 10);
        bVar.start();
        synchronized (bVar) {
            while (this.d == null) {
                try {
                    bVar.wait();
                } catch (InterruptedException e2) {
                    com.amap.location.common.d.a.a((Throwable) e2);
                }
            }
        }
    }

    public void b() {
        this.e.writeLock().lock();
        try {
            C0034a aVar = this.d;
            this.d = null;
            if (aVar != null) {
                aVar.removeCallbacksAndMessages(null);
                aVar.sendEmptyMessage(4);
            }
        } finally {
            this.e.writeLock().unlock();
        }
    }

    public void a(@NonNull c cVar) {
        this.b = cVar;
        this.f.a(cVar);
    }

    /* access modifiers changed from: private */
    public void c() {
        if (!this.f.a() && e()) {
            int a2 = h.a(this.a);
            if (a(a2)) {
                this.f.a(0, a2);
            } else if (b(a2)) {
                this.f.a(1, a2);
            }
        }
    }

    /* access modifiers changed from: private */
    public void d() {
        int a2 = h.a(this.a);
        if (!this.f.a() && e() && b(a2)) {
            this.f.a(1, a2);
        }
    }

    /* access modifiers changed from: private */
    public boolean e() {
        return this.b != null && this.b.k && this.c != null && this.c.a();
    }

    private boolean a(int i) {
        if (!this.c.j() || i != 1 || com.amap.location.offline.e.c.a(this.a)) {
            return false;
        }
        return true;
    }

    private boolean b(int i) {
        boolean z = true;
        if (i == 1) {
            return com.amap.location.offline.e.c.d(this.a, this.c.g());
        }
        if (i != 0) {
            return false;
        }
        if (!com.amap.location.offline.e.c.d(this.a, this.c.g()) || !com.amap.location.offline.e.c.e(this.a, this.c.h())) {
            z = false;
        }
        if (z) {
            com.amap.location.offline.d.a.a(100052);
        }
        return z;
    }

    /* access modifiers changed from: private */
    public void f() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            this.a.registerReceiver(this.h, intentFilter, null, this.d);
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        try {
            this.a.unregisterReceiver(this.h);
        } catch (Exception e2) {
            com.amap.location.common.d.a.a((Throwable) e2);
        }
    }
}
