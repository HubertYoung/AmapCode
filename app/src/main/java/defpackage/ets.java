package defpackage;

import com.danikula.videocache.ProxyCacheException;
import java.lang.Thread.State;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: ets reason: default package */
/* compiled from: ProxyCache */
class ets {
    private final etu a;
    private final Object b = new Object();
    private final AtomicInteger c;
    final eth d;
    final Object e = new Object();
    volatile Thread f;
    volatile boolean g;
    private volatile int h = -1;

    /* renamed from: ets$a */
    /* compiled from: ProxyCache */
    class a implements Runnable {
        private a() {
        }

        /* synthetic */ a(ets ets, byte b) {
            this();
        }

        public final void run() {
            ets.a(ets.this);
        }
    }

    /* access modifiers changed from: protected */
    public void a(int i) {
    }

    public ets(etu etu, eth eth) {
        this.a = (etu) etr.a(etu);
        this.d = (eth) etr.a(eth);
        this.c = new AtomicInteger();
    }

    private synchronized void a() throws ProxyCacheException {
        boolean z = (this.f == null || this.f.getState() == State.TERMINATED) ? false : true;
        if (!this.g && !this.d.d() && !z) {
            a aVar = new a(this, 0);
            StringBuilder sb = new StringBuilder("Source reader for ");
            sb.append(this.a);
            this.f = new Thread(aVar, sb.toString());
            this.f.start();
        }
    }

    private boolean b() {
        return Thread.currentThread().isInterrupted() || this.g;
    }

    private void c() {
        try {
            this.a.b();
        } catch (ProxyCacheException e2) {
            StringBuilder sb = new StringBuilder("Error closing source ");
            sb.append(this.a);
            new ProxyCacheException(sb.toString(), e2);
        }
    }

    public final int a(byte[] bArr, long j) throws ProxyCacheException {
        etr.a(bArr, (String) "Buffer must be not null!");
        etr.a(j >= 0, (String) "Data offset must be positive!");
        etr.a(true, (String) "Length must be in range [0..buffer.length]");
        while (!this.d.d() && this.d.a() < 8192 + j && !this.g) {
            a();
            synchronized (this.b) {
                try {
                    this.b.wait(1000);
                } catch (InterruptedException e2) {
                    throw new ProxyCacheException("Waiting source data is interrupted!", e2);
                }
            }
            int i = this.c.get();
            if (i > 0) {
                this.c.set(0);
                StringBuilder sb = new StringBuilder("Error reading source ");
                sb.append(i);
                sb.append(" times");
                throw new ProxyCacheException(sb.toString());
            }
        }
        int a2 = this.d.a(bArr, j);
        if (this.d.d() && this.h != 100) {
            this.h = 100;
            a(100);
        }
        return a2;
    }

    private void a(long j, long j2) {
        int i = (j2 > 0 ? 1 : (j2 == 0 ? 0 : -1));
        boolean z = false;
        int i2 = i == 0 ? 100 : (int) ((((float) j) / ((float) j2)) * 100.0f);
        boolean z2 = i2 != this.h;
        if (i >= 0) {
            z = true;
        }
        if (z && z2) {
            a(i2);
        }
        this.h = i2;
        synchronized (this.b) {
            this.b.notifyAll();
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0039, code lost:
        r4 = r4 + ((long) r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0042, code lost:
        r0 = r12.e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0044, code lost:
        monitor-enter(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:0x0049, code lost:
        if (r12.b() != false) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x0059, code lost:
        if (r12.d.a() != r12.a.a()) goto L_0x0060;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x005b, code lost:
        r12.d.c();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0060, code lost:
        monitor-exit(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:?, code lost:
        r12.h = 100;
        r12.a(r12.h);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x006a, code lost:
        r12.c();
        r12.a(r4, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x0070, code lost:
        return;
     */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing exception handler attribute for start block: B:58:0x0084 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ void a(defpackage.ets r12) {
        /*
            r0 = -1
            r2 = 0
            eth r4 = r12.d     // Catch:{ Throwable -> 0x0084 }
            long r4 = r4.a()     // Catch:{ Throwable -> 0x0084 }
            etu r2 = r12.a     // Catch:{ Throwable -> 0x007c, all -> 0x0078 }
            r2.a(r4)     // Catch:{ Throwable -> 0x007c, all -> 0x0078 }
            etu r2 = r12.a     // Catch:{ Throwable -> 0x007c, all -> 0x0078 }
            long r2 = r2.a()     // Catch:{ Throwable -> 0x007c, all -> 0x0078 }
            r0 = 8192(0x2000, float:1.14794E-41)
            byte[] r0 = new byte[r0]     // Catch:{ Throwable -> 0x0076, all -> 0x0074 }
        L_0x0019:
            etu r1 = r12.a     // Catch:{ Throwable -> 0x0076, all -> 0x0074 }
            int r1 = r1.a(r0)     // Catch:{ Throwable -> 0x0076, all -> 0x0074 }
            r6 = -1
            if (r1 == r6) goto L_0x0042
            java.lang.Object r6 = r12.e     // Catch:{ Throwable -> 0x0076, all -> 0x0074 }
            monitor-enter(r6)     // Catch:{ Throwable -> 0x0076, all -> 0x0074 }
            boolean r7 = r12.b()     // Catch:{ all -> 0x003f }
            if (r7 == 0) goto L_0x0033
            monitor-exit(r6)     // Catch:{ all -> 0x003f }
            r12.c()
            r12.a(r4, r2)
            return
        L_0x0033:
            eth r7 = r12.d     // Catch:{ all -> 0x003f }
            r7.a(r0, r1)     // Catch:{ all -> 0x003f }
            monitor-exit(r6)     // Catch:{ all -> 0x003f }
            long r6 = (long) r1
            long r4 = r4 + r6
            r12.a(r4, r2)     // Catch:{ Throwable -> 0x0076, all -> 0x0074 }
            goto L_0x0019
        L_0x003f:
            r0 = move-exception
            monitor-exit(r6)     // Catch:{ all -> 0x003f }
            throw r0     // Catch:{ Throwable -> 0x0076, all -> 0x0074 }
        L_0x0042:
            java.lang.Object r0 = r12.e     // Catch:{ Throwable -> 0x0076, all -> 0x0074 }
            monitor-enter(r0)     // Catch:{ Throwable -> 0x0076, all -> 0x0074 }
            boolean r1 = r12.b()     // Catch:{ all -> 0x0071 }
            if (r1 != 0) goto L_0x0060
            eth r1 = r12.d     // Catch:{ all -> 0x0071 }
            long r6 = r1.a()     // Catch:{ all -> 0x0071 }
            etu r1 = r12.a     // Catch:{ all -> 0x0071 }
            long r8 = r1.a()     // Catch:{ all -> 0x0071 }
            int r1 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r1 != 0) goto L_0x0060
            eth r1 = r12.d     // Catch:{ all -> 0x0071 }
            r1.c()     // Catch:{ all -> 0x0071 }
        L_0x0060:
            monitor-exit(r0)     // Catch:{ all -> 0x0071 }
            r0 = 100
            r12.h = r0     // Catch:{ Throwable -> 0x0076, all -> 0x0074 }
            int r0 = r12.h     // Catch:{ Throwable -> 0x0076, all -> 0x0074 }
            r12.a(r0)     // Catch:{ Throwable -> 0x0076, all -> 0x0074 }
            r12.c()
            r12.a(r4, r2)
            return
        L_0x0071:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0071 }
            throw r1     // Catch:{ Throwable -> 0x0076, all -> 0x0074 }
        L_0x0074:
            r0 = move-exception
            goto L_0x0090
        L_0x0076:
            r0 = r2
            goto L_0x007c
        L_0x0078:
            r2 = move-exception
            r10 = r0
            r0 = r2
            goto L_0x0082
        L_0x007c:
            r2 = r4
            goto L_0x0084
        L_0x007e:
            r4 = move-exception
            r10 = r0
            r0 = r4
            r4 = r2
        L_0x0082:
            r2 = r10
            goto L_0x0090
        L_0x0084:
            java.util.concurrent.atomic.AtomicInteger r4 = r12.c     // Catch:{ all -> 0x007e }
            r4.incrementAndGet()     // Catch:{ all -> 0x007e }
            r12.c()
            r12.a(r2, r0)
            return
        L_0x0090:
            r12.c()
            r12.a(r4, r2)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ets.a(ets):void");
    }
}
