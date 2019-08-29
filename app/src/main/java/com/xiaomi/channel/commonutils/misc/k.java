package com.xiaomi.channel.commonutils.misc;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class k {
    private a a;
    /* access modifiers changed from: private */
    public Handler b;
    /* access modifiers changed from: private */
    public volatile boolean c;
    private final boolean d;
    /* access modifiers changed from: private */
    public int e;
    /* access modifiers changed from: private */
    public volatile b f;

    class a extends Thread {
        private final LinkedBlockingQueue<b> b = new LinkedBlockingQueue<>();

        public a() {
            super("PackageProcessor");
        }

        public void a(b bVar) {
            this.b.add(bVar);
        }

        public void run() {
            long a2 = k.this.e > 0 ? (long) k.this.e : Long.MAX_VALUE;
            while (!k.this.c) {
                try {
                    k.this.f = this.b.poll(a2, TimeUnit.SECONDS);
                    if (k.this.f != null) {
                        k.this.b.sendMessage(k.this.b.obtainMessage(0, k.this.f));
                        k.this.f.b();
                        k.this.b.sendMessage(k.this.b.obtainMessage(1, k.this.f));
                    } else if (k.this.e > 0) {
                        k.this.a();
                    }
                } catch (InterruptedException e) {
                    com.xiaomi.channel.commonutils.logger.b.a((Throwable) e);
                }
            }
        }
    }

    public static abstract class b {
        public void a() {
        }

        public abstract void b();

        public void c() {
        }
    }

    public k() {
        this(false);
    }

    public k(boolean z) {
        this(z, 0);
    }

    public k(boolean z, int i) {
        this.b = null;
        this.c = false;
        this.e = 0;
        this.b = new l(this, Looper.getMainLooper());
        this.d = z;
        this.e = i;
    }

    /* access modifiers changed from: private */
    public synchronized void a() {
        this.a = null;
        this.c = true;
    }

    public synchronized void a(b bVar) {
        if (this.a == null) {
            this.a = new a();
            this.a.setDaemon(this.d);
            this.c = false;
            this.a.start();
        }
        this.a.a(bVar);
    }

    public void a(b bVar, long j) {
        this.b.postDelayed(new m(this, bVar), j);
    }
}
