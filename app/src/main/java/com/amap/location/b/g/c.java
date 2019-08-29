package com.amap.location.b.g;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.amap.location.b.a.C0010a;
import com.amap.location.b.f.g;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/* compiled from: WifiScanner */
public class c {
    private Context a;
    private Looper b;
    /* access modifiers changed from: private */
    public com.amap.location.g.d.a c;
    /* access modifiers changed from: private */
    public ReentrantReadWriteLock d;
    private boolean e = false;
    private BroadcastReceiver f;
    /* access modifiers changed from: private */
    public Handler g = null;
    /* access modifiers changed from: private */
    public boolean h = true;
    private boolean i = true;
    /* access modifiers changed from: private */
    public int j = 20000;
    private C0010a k;
    /* access modifiers changed from: private */
    public final Object l = new Object();
    /* access modifiers changed from: private */
    public List<ScanResult> m = new ArrayList();
    /* access modifiers changed from: private */
    public long n = 0;
    private Comparator<ScanResult> o = new Comparator<ScanResult>() {
        /* renamed from: a */
        public int compare(ScanResult scanResult, ScanResult scanResult2) {
            int compareTo = scanResult.BSSID.compareTo(scanResult.BSSID);
            if (compareTo > 0) {
                return 1;
            }
            return compareTo == 0 ? 0 : -1;
        }
    };

    /* compiled from: WifiScanner */
    public static class a {
        public List<ScanResult> a = new ArrayList();
        public long b;
    }

    /* compiled from: WifiScanner */
    final class b extends Handler {
        public b(Looper looper) {
            super(looper);
        }

        public final void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 0:
                        c.this.g();
                        c.this.i();
                        return;
                    case 1:
                        c.this.h();
                        c.this.d.writeLock().lock();
                        if (c.this.g != null) {
                            c.this.g.removeCallbacksAndMessages(null);
                            c.this.g = null;
                        }
                        c.this.d.writeLock().unlock();
                        return;
                    case 2:
                        c.this.i();
                        break;
                }
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }
    }

    public c(Context context, C0010a aVar, Looper looper) {
        this.a = context;
        this.h = aVar.b();
        this.j = aVar.e();
        this.i = aVar.c();
        this.k = aVar;
        this.b = looper;
        this.d = new ReentrantReadWriteLock();
        this.c = com.amap.location.g.d.a.a(this.a);
    }

    public void a() {
        if (!this.e) {
            this.e = true;
            this.d.writeLock().lock();
            try {
                if (this.g == null) {
                    this.g = new b(this.b);
                }
                this.g.sendEmptyMessage(0);
            } finally {
                this.d.writeLock().unlock();
            }
        }
    }

    public void b() {
        if (this.e) {
            this.e = false;
            this.d.readLock().lock();
            try {
                if (this.g != null) {
                    this.g.sendEmptyMessage(1);
                }
            } finally {
                this.d.readLock().unlock();
            }
        }
    }

    public void c() {
        this.d.readLock().lock();
        try {
            if (this.g != null && !this.g.hasMessages(2)) {
                this.g.sendEmptyMessage(2);
            }
        } finally {
            this.d.readLock().unlock();
        }
    }

    public void d() {
        this.d.readLock().lock();
        try {
            if (this.g != null) {
                this.g.removeMessages(2);
            }
        } finally {
            this.d.readLock().unlock();
        }
    }

    public boolean e() {
        return this.e;
    }

    public a f() {
        a aVar = new a();
        synchronized (this.l) {
            try {
                if (this.m == null) {
                    return aVar;
                }
                for (ScanResult add : this.m) {
                    aVar.a.add(add);
                }
                aVar.b = this.n;
                return aVar;
            }
        }
    }

    /* access modifiers changed from: private */
    public void g() {
        this.f = new BroadcastReceiver() {
            /* JADX WARNING: Code restructure failed: missing block: B:40:0x00af, code lost:
                r3 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:41:0x00b0, code lost:
                com.amap.location.b.g.c.f(r2.a).readLock().unlock();
             */
            /* JADX WARNING: Code restructure failed: missing block: B:42:0x00bd, code lost:
                throw r3;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:43:0x00be, code lost:
                r3 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:44:0x00bf, code lost:
                com.amap.location.common.d.a.a(r3);
             */
            /* JADX WARNING: Code restructure failed: missing block: B:45:0x00c2, code lost:
                return;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:46:0x00c3, code lost:
                r3 = move-exception;
             */
            /* JADX WARNING: Code restructure failed: missing block: B:47:0x00c4, code lost:
                com.amap.location.common.d.a.a((java.lang.Throwable) r3);
             */
            /* JADX WARNING: Failed to process nested try/catch */
            /* JADX WARNING: Removed duplicated region for block: B:46:0x00c3 A[ExcHandler: SecurityException (r3v1 'e' java.lang.SecurityException A[CUSTOM_DECLARE]), Splitter:B:1:0x0002] */
            /* Code decompiled incorrectly, please refer to instructions dump. */
            public void onReceive(android.content.Context r3, android.content.Intent r4) {
                /*
                    r2 = this;
                    if (r4 == 0) goto L_0x00c7
                    java.lang.String r3 = r4.getAction()     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    if (r3 == 0) goto L_0x00c7
                    java.lang.String r3 = "android.net.wifi.SCAN_RESULTS"
                    java.lang.String r0 = r4.getAction()     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    boolean r3 = r3.equals(r0)     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    if (r3 == 0) goto L_0x00c7
                    r3 = 1
                    com.amap.location.b.g.c r0 = com.amap.location.b.g.c.this     // Catch:{ Throwable -> 0x002f, SecurityException -> 0x00c3 }
                    boolean r0 = r0.j()     // Catch:{ Throwable -> 0x002f, SecurityException -> 0x00c3 }
                    if (r0 == 0) goto L_0x002f
                    android.os.Bundle r0 = r4.getExtras()     // Catch:{ Throwable -> 0x002f, SecurityException -> 0x00c3 }
                    if (r0 == 0) goto L_0x002f
                    android.os.Bundle r4 = r4.getExtras()     // Catch:{ Throwable -> 0x002f, SecurityException -> 0x00c3 }
                    java.lang.String r0 = "resultsUpdated"
                    boolean r4 = r4.getBoolean(r0, r3)     // Catch:{ Throwable -> 0x002f, SecurityException -> 0x00c3 }
                    r3 = r4
                L_0x002f:
                    r4 = 100067(0x186e3, float:1.40224E-40)
                    com.amap.location.uptunnel.UpTunnel.addCount(r4)     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    if (r3 == 0) goto L_0x0064
                    com.amap.location.b.g.c r3 = com.amap.location.b.g.c.this     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    java.lang.Object r3 = r3.l     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    monitor-enter(r3)     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    com.amap.location.b.g.c r4 = com.amap.location.b.g.c.this     // Catch:{ all -> 0x0061 }
                    long r0 = java.lang.System.currentTimeMillis()     // Catch:{ all -> 0x0061 }
                    r4.n = r0     // Catch:{ all -> 0x0061 }
                    com.amap.location.b.g.c r4 = com.amap.location.b.g.c.this     // Catch:{ all -> 0x0061 }
                    com.amap.location.b.g.c r0 = com.amap.location.b.g.c.this     // Catch:{ all -> 0x0061 }
                    com.amap.location.g.d.a r0 = r0.c     // Catch:{ all -> 0x0061 }
                    java.util.List r0 = r0.b()     // Catch:{ all -> 0x0061 }
                    r4.m = r0     // Catch:{ all -> 0x0061 }
                    com.amap.location.b.g.c r4 = com.amap.location.b.g.c.this     // Catch:{ all -> 0x0061 }
                    java.util.List r4 = r4.m     // Catch:{ all -> 0x0061 }
                    com.amap.location.b.d.a.b(r4)     // Catch:{ all -> 0x0061 }
                    monitor-exit(r3)     // Catch:{ all -> 0x0061 }
                    goto L_0x006a
                L_0x0061:
                    r4 = move-exception
                    monitor-exit(r3)     // Catch:{ all -> 0x0061 }
                    throw r4     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                L_0x0064:
                    r3 = 100068(0x186e4, float:1.40225E-40)
                    com.amap.location.uptunnel.UpTunnel.addCount(r3)     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                L_0x006a:
                    com.amap.location.b.g.c r3 = com.amap.location.b.g.c.this     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    boolean r3 = r3.h     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    if (r3 == 0) goto L_0x00c7
                    com.amap.location.b.g.c r3 = com.amap.location.b.g.c.this     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    java.util.concurrent.locks.ReentrantReadWriteLock r3 = r3.d     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r3 = r3.readLock()     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    r3.lock()     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    com.amap.location.b.g.c r3 = com.amap.location.b.g.c.this     // Catch:{ all -> 0x00af }
                    android.os.Handler r3 = r3.g     // Catch:{ all -> 0x00af }
                    if (r3 == 0) goto L_0x00a1
                    com.amap.location.b.g.c r3 = com.amap.location.b.g.c.this     // Catch:{ all -> 0x00af }
                    android.os.Handler r3 = r3.g     // Catch:{ all -> 0x00af }
                    r4 = 2
                    r3.removeMessages(r4)     // Catch:{ all -> 0x00af }
                    com.amap.location.b.g.c r3 = com.amap.location.b.g.c.this     // Catch:{ all -> 0x00af }
                    android.os.Handler r3 = r3.g     // Catch:{ all -> 0x00af }
                    com.amap.location.b.g.c r0 = com.amap.location.b.g.c.this     // Catch:{ all -> 0x00af }
                    int r0 = r0.j     // Catch:{ all -> 0x00af }
                    long r0 = (long) r0     // Catch:{ all -> 0x00af }
                    r3.sendEmptyMessageDelayed(r4, r0)     // Catch:{ all -> 0x00af }
                L_0x00a1:
                    com.amap.location.b.g.c r3 = com.amap.location.b.g.c.this     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    java.util.concurrent.locks.ReentrantReadWriteLock r3 = r3.d     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r3 = r3.readLock()     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    r3.unlock()     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    return
                L_0x00af:
                    r3 = move-exception
                    com.amap.location.b.g.c r4 = com.amap.location.b.g.c.this     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    java.util.concurrent.locks.ReentrantReadWriteLock r4 = r4.d     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r4 = r4.readLock()     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    r4.unlock()     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                    throw r3     // Catch:{ SecurityException -> 0x00c3, Throwable -> 0x00be }
                L_0x00be:
                    r3 = move-exception
                    com.amap.location.common.d.a.a(r3)
                    return
                L_0x00c3:
                    r3 = move-exception
                    com.amap.location.common.d.a.a(r3)
                L_0x00c7:
                    return
                */
                throw new UnsupportedOperationException("Method not decompiled: com.amap.location.b.g.c.AnonymousClass1.onReceive(android.content.Context, android.content.Intent):void");
            }
        };
        a(this.f);
    }

    /* access modifiers changed from: private */
    public void h() {
        synchronized (this.l) {
            this.n = 0;
            if (this.m != null) {
                this.m.clear();
            }
        }
        if (this.f != null) {
            b(this.f);
        }
    }

    /* access modifiers changed from: private */
    public void i() {
        if (this.h && this.c != null && this.c.g()) {
            boolean z = false;
            try {
                if (VERSION.SDK_INT < 18 && this.i) {
                    Object a2 = g.a(this.c, "startScanActive", new Object[0]);
                    if (a2 != null && "true".equals(String.valueOf(a2))) {
                        z = true;
                    }
                }
            } catch (Exception e2) {
                com.amap.location.common.d.a.a((Throwable) e2);
            }
            if (!z) {
                try {
                    this.c.a();
                } catch (Exception e3) {
                    com.amap.location.common.d.a.a((Throwable) e3);
                }
            }
        }
    }

    private void a(BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null && this.a != null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
            try {
                this.a.registerReceiver(broadcastReceiver, intentFilter);
            } catch (Throwable th) {
                com.amap.location.common.d.a.a(th);
            }
        }
    }

    private void b(BroadcastReceiver broadcastReceiver) {
        if (broadcastReceiver != null && this.a != null) {
            try {
                this.a.unregisterReceiver(broadcastReceiver);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean j() {
        C0010a aVar = this.k;
        if (this.k != null) {
            return aVar.d();
        }
        return true;
    }
}
