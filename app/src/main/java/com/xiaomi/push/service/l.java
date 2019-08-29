package com.xiaomi.push.service;

import android.os.SystemClock;
import java.util.concurrent.RejectedExecutionException;

public class l {
    private static long a;
    private static long b;
    private static long c;
    private final c d;
    private final a e;

    static final class a {
        private final c a;

        a(c cVar) {
            this.a = cVar;
        }

        /* access modifiers changed from: protected */
        public final void finalize() {
            try {
                synchronized (this.a) {
                    this.a.e = true;
                    this.a.notify();
                }
                super.finalize();
            } catch (Throwable th) {
                super.finalize();
                throw th;
            }
        }
    }

    public static abstract class b implements Runnable {
        protected int a;

        public b(int i) {
            this.a = i;
        }
    }

    static final class c extends Thread {
        private volatile long a = 0;
        private volatile boolean b = false;
        private long c = 50;
        /* access modifiers changed from: private */
        public boolean d;
        /* access modifiers changed from: private */
        public boolean e;
        /* access modifiers changed from: private */
        public a f = new a();

        static final class a {
            private int a;
            private d[] b;
            private int c;
            private int d;

            private a() {
                this.a = 256;
                this.b = new d[this.a];
                this.c = 0;
                this.d = 0;
            }

            /* access modifiers changed from: private */
            public int b(d dVar) {
                for (int i = 0; i < this.b.length; i++) {
                    if (this.b[i] == dVar) {
                        return i;
                    }
                }
                return -1;
            }

            private void d(int i) {
                int i2 = (i * 2) + 1;
                while (i2 < this.c && this.c > 0) {
                    int i3 = i2 + 1;
                    if (i3 < this.c && this.b[i3].c < this.b[i2].c) {
                        i2 = i3;
                    }
                    if (this.b[i].c >= this.b[i2].c) {
                        d dVar = this.b[i];
                        d[] dVarArr = this.b;
                        dVarArr[i] = dVarArr[i2];
                        this.b[i2] = dVar;
                        int i4 = i2;
                        i2 = (i2 * 2) + 1;
                        i = i4;
                    } else {
                        return;
                    }
                }
            }

            private void e() {
                int i = this.c - 1;
                int i2 = (i - 1) / 2;
                while (this.b[i].c < this.b[i2].c) {
                    d dVar = this.b[i];
                    d[] dVarArr = this.b;
                    dVarArr[i] = dVarArr[i2];
                    this.b[i2] = dVar;
                    int i3 = i2;
                    i2 = (i2 - 1) / 2;
                    i = i3;
                }
            }

            public final d a() {
                return this.b[0];
            }

            public final void a(int i, b bVar) {
                for (int i2 = 0; i2 < this.c; i2++) {
                    if (this.b[i2].d == bVar) {
                        this.b[i2].a();
                    }
                }
                d();
            }

            public final void a(d dVar) {
                if (this.b.length == this.c) {
                    d[] dVarArr = new d[(this.c * 2)];
                    System.arraycopy(this.b, 0, dVarArr, 0, this.c);
                    this.b = dVarArr;
                }
                d[] dVarArr2 = this.b;
                int i = this.c;
                this.c = i + 1;
                dVarArr2[i] = dVar;
                e();
            }

            public final boolean a(int i) {
                for (int i2 = 0; i2 < this.c; i2++) {
                    if (this.b[i2].e == i) {
                        return true;
                    }
                }
                return false;
            }

            public final void b(int i) {
                for (int i2 = 0; i2 < this.c; i2++) {
                    if (this.b[i2].e == i) {
                        this.b[i2].a();
                    }
                }
                d();
            }

            public final boolean b() {
                return this.c == 0;
            }

            public final void c() {
                this.b = new d[this.a];
                this.c = 0;
            }

            public final void c(int i) {
                if (i >= 0 && i < this.c) {
                    d[] dVarArr = this.b;
                    int i2 = this.c - 1;
                    this.c = i2;
                    dVarArr[i] = dVarArr[i2];
                    this.b[this.c] = null;
                    d(i);
                }
            }

            public final void d() {
                int i = 0;
                while (i < this.c) {
                    if (this.b[i].b) {
                        this.d++;
                        c(i);
                        i--;
                    }
                    i++;
                }
            }
        }

        c(String str, boolean z) {
            setName(str);
            setDaemon(z);
            start();
        }

        /* access modifiers changed from: private */
        public void a(d dVar) {
            this.f.a(dVar);
            notify();
        }

        public final synchronized void a() {
            this.d = true;
            this.f.c();
            notify();
        }

        public final boolean b() {
            return this.b && SystemClock.uptimeMillis() - this.a > 600000;
        }

        /* JADX WARNING: Can't wrap try/catch for region: R(3:6|(2:8|(3:83|10|11)(2:12|13))(2:17|26)|14) */
        /* JADX WARNING: Code restructure failed: missing block: B:57:?, code lost:
            r10.a = android.os.SystemClock.uptimeMillis();
            r10.b = true;
            r2.d.run();
            r10.b = false;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:58:0x00a8, code lost:
            r1 = move-exception;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:59:0x00a9, code lost:
            monitor-enter(r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:61:?, code lost:
            r10.d = true;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:63:0x00ad, code lost:
            throw r1;
         */
        /* JADX WARNING: Missing exception handler attribute for start block: B:14:0x0018 */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void run() {
            /*
                r10 = this;
            L_0x0000:
                monitor-enter(r10)
                boolean r0 = r10.d     // Catch:{ all -> 0x00b7 }
                if (r0 == 0) goto L_0x0007
                monitor-exit(r10)     // Catch:{ all -> 0x00b7 }
                return
            L_0x0007:
                com.xiaomi.push.service.l$c$a r0 = r10.f     // Catch:{ all -> 0x00b7 }
                boolean r0 = r0.b()     // Catch:{ all -> 0x00b7 }
                if (r0 == 0) goto L_0x001a
                boolean r0 = r10.e     // Catch:{ all -> 0x00b7 }
                if (r0 == 0) goto L_0x0015
                monitor-exit(r10)     // Catch:{ all -> 0x00b7 }
                return
            L_0x0015:
                r10.wait()     // Catch:{ InterruptedException -> 0x0018 }
            L_0x0018:
                monitor-exit(r10)     // Catch:{ all -> 0x00b7 }
                goto L_0x0000
            L_0x001a:
                long r0 = com.xiaomi.push.service.l.a()     // Catch:{ all -> 0x00b7 }
                com.xiaomi.push.service.l$c$a r2 = r10.f     // Catch:{ all -> 0x00b7 }
                com.xiaomi.push.service.l$d r2 = r2.a()     // Catch:{ all -> 0x00b7 }
                java.lang.Object r3 = r2.a     // Catch:{ all -> 0x00b7 }
                monitor-enter(r3)     // Catch:{ all -> 0x00b7 }
                boolean r4 = r2.b     // Catch:{ all -> 0x00b4 }
                r5 = 0
                if (r4 == 0) goto L_0x0033
                com.xiaomi.push.service.l$c$a r0 = r10.f     // Catch:{ all -> 0x00b4 }
                r0.c(r5)     // Catch:{ all -> 0x00b4 }
                monitor-exit(r3)     // Catch:{ all -> 0x00b4 }
                goto L_0x0018
            L_0x0033:
                long r6 = r2.c     // Catch:{ all -> 0x00b4 }
                r4 = 0
                long r6 = r6 - r0
                monitor-exit(r3)     // Catch:{ all -> 0x00b4 }
                r0 = 0
                int r3 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
                r8 = 50
                if (r3 <= 0) goto L_0x005c
                long r0 = r10.c     // Catch:{ all -> 0x00b7 }
                int r0 = (r6 > r0 ? 1 : (r6 == r0 ? 0 : -1))
                if (r0 <= 0) goto L_0x0048
                long r6 = r10.c     // Catch:{ all -> 0x00b7 }
            L_0x0048:
                long r0 = r10.c     // Catch:{ all -> 0x00b7 }
                r2 = 0
                long r0 = r0 + r8
                r10.c = r0     // Catch:{ all -> 0x00b7 }
                long r0 = r10.c     // Catch:{ all -> 0x00b7 }
                r2 = 500(0x1f4, double:2.47E-321)
                int r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                if (r0 <= 0) goto L_0x0058
                r10.c = r2     // Catch:{ all -> 0x00b7 }
            L_0x0058:
                r10.wait(r6)     // Catch:{ InterruptedException -> 0x0018 }
                goto L_0x0018
            L_0x005c:
                r10.c = r8     // Catch:{ all -> 0x00b7 }
                java.lang.Object r3 = r2.a     // Catch:{ all -> 0x00b7 }
                monitor-enter(r3)     // Catch:{ all -> 0x00b7 }
                com.xiaomi.push.service.l$c$a r4 = r10.f     // Catch:{ all -> 0x00b1 }
                com.xiaomi.push.service.l$d r4 = r4.a()     // Catch:{ all -> 0x00b1 }
                long r6 = r4.c     // Catch:{ all -> 0x00b1 }
                long r8 = r2.c     // Catch:{ all -> 0x00b1 }
                int r4 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
                if (r4 == 0) goto L_0x0076
                com.xiaomi.push.service.l$c$a r4 = r10.f     // Catch:{ all -> 0x00b1 }
                int r4 = r4.b(r2)     // Catch:{ all -> 0x00b1 }
                goto L_0x0077
            L_0x0076:
                r4 = 0
            L_0x0077:
                boolean r6 = r2.b     // Catch:{ all -> 0x00b1 }
                if (r6 == 0) goto L_0x0088
                com.xiaomi.push.service.l$c$a r0 = r10.f     // Catch:{ all -> 0x00b1 }
                com.xiaomi.push.service.l$c$a r1 = r10.f     // Catch:{ all -> 0x00b1 }
                int r1 = r1.b(r2)     // Catch:{ all -> 0x00b1 }
                r0.c(r1)     // Catch:{ all -> 0x00b1 }
                monitor-exit(r3)     // Catch:{ all -> 0x00b1 }
                goto L_0x0018
            L_0x0088:
                long r6 = r2.c     // Catch:{ all -> 0x00b1 }
                r2.a(r6)     // Catch:{ all -> 0x00b1 }
                com.xiaomi.push.service.l$c$a r6 = r10.f     // Catch:{ all -> 0x00b1 }
                r6.c(r4)     // Catch:{ all -> 0x00b1 }
                r2.c = r0     // Catch:{ all -> 0x00b1 }
                monitor-exit(r3)     // Catch:{ all -> 0x00b1 }
                monitor-exit(r10)     // Catch:{ all -> 0x00b7 }
                r0 = 1
                long r3 = android.os.SystemClock.uptimeMillis()     // Catch:{ all -> 0x00a8 }
                r10.a = r3     // Catch:{ all -> 0x00a8 }
                r10.b = r0     // Catch:{ all -> 0x00a8 }
                com.xiaomi.push.service.l$b r1 = r2.d     // Catch:{ all -> 0x00a8 }
                r1.run()     // Catch:{ all -> 0x00a8 }
                r10.b = r5     // Catch:{ all -> 0x00a8 }
                goto L_0x0000
            L_0x00a8:
                r1 = move-exception
                monitor-enter(r10)
                r10.d = r0     // Catch:{ all -> 0x00ae }
                monitor-exit(r10)     // Catch:{ all -> 0x00ae }
                throw r1
            L_0x00ae:
                r0 = move-exception
                monitor-exit(r10)     // Catch:{ all -> 0x00ae }
                throw r0
            L_0x00b1:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x00b1 }
                throw r0     // Catch:{ all -> 0x00b7 }
            L_0x00b4:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x00b4 }
                throw r0     // Catch:{ all -> 0x00b7 }
            L_0x00b7:
                r0 = move-exception
                monitor-exit(r10)     // Catch:{ all -> 0x00b7 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.xiaomi.push.service.l.c.run():void");
        }
    }

    static class d {
        final Object a = new Object();
        boolean b;
        long c;
        b d;
        int e;
        private long f;

        d() {
        }

        /* access modifiers changed from: 0000 */
        public void a(long j) {
            synchronized (this.a) {
                this.f = j;
            }
        }

        public boolean a() {
            boolean z;
            synchronized (this.a) {
                z = !this.b && this.c > 0;
                this.b = true;
            }
            return z;
        }
    }

    static {
        long j = 0;
        if (SystemClock.elapsedRealtime() > 0) {
            j = SystemClock.elapsedRealtime();
        }
        a = j;
        b = j;
    }

    public l() {
        this(false);
    }

    public l(String str) {
        this(str, false);
    }

    public l(String str, boolean z) {
        if (str == null) {
            throw new NullPointerException("name == null");
        }
        this.d = new c(str, z);
        this.e = new a(this.d);
    }

    /* JADX WARN: Illegal instructions before constructor call commented (this can break semantics) */
    public l(boolean z) {
        // StringBuilder sb = new StringBuilder("Timer-");
        // sb.append(e());
        this(sb.toString(), z);
    }

    static synchronized long a() {
        long j;
        synchronized (l.class) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            if (elapsedRealtime > b) {
                a += elapsedRealtime - b;
            }
            b = elapsedRealtime;
            j = a;
        }
        return j;
    }

    private void b(b bVar, long j) {
        synchronized (this.d) {
            if (this.d.d) {
                throw new IllegalStateException("Timer was canceled");
            }
            long a2 = j + a();
            if (a2 < 0) {
                throw new IllegalArgumentException("Illegal delay to start the TimerTask: ".concat(String.valueOf(a2)));
            }
            d dVar = new d();
            dVar.e = bVar.a;
            dVar.d = bVar;
            dVar.c = a2;
            this.d.a(dVar);
        }
    }

    private static synchronized long e() {
        long j;
        synchronized (l.class) {
            j = c;
            c = 1 + j;
        }
        return j;
    }

    public void a(int i, b bVar) {
        synchronized (this.d) {
            this.d.f.a(i, bVar);
        }
    }

    public void a(b bVar) {
        if (com.xiaomi.channel.commonutils.logger.b.a() > 0 || Thread.currentThread() == this.d) {
            bVar.run();
        } else {
            com.xiaomi.channel.commonutils.logger.b.d("run job outside job job thread");
            throw new RejectedExecutionException("Run job outside job thread");
        }
    }

    public void a(b bVar, long j) {
        if (j < 0) {
            throw new IllegalArgumentException("delay < 0: ".concat(String.valueOf(j)));
        }
        b(bVar, j);
    }

    public boolean a(int i) {
        boolean a2;
        synchronized (this.d) {
            try {
                a2 = this.d.f.a(i);
            }
        }
        return a2;
    }

    public void b() {
        this.d.a();
    }

    public void b(int i) {
        synchronized (this.d) {
            this.d.f.b(i);
        }
    }

    public void c() {
        synchronized (this.d) {
            this.d.f.c();
        }
    }

    public boolean d() {
        return this.d.b();
    }
}
