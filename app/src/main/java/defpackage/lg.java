package defpackage;

import android.os.Handler;
import android.os.SystemClock;

/* renamed from: lg reason: default package */
/* compiled from: TimeOutWatcher */
public final class lg {
    long a;
    boolean b = false;
    public boolean c = true;
    a d;
    private final long e;
    private Handler f = new Handler() {
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0036, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final void handleMessage(android.os.Message r6) {
            /*
                r5 = this;
                lg r6 = defpackage.lg.this
                monitor-enter(r6)
                lg r0 = defpackage.lg.this     // Catch:{ all -> 0x0037 }
                boolean r0 = r0.b     // Catch:{ all -> 0x0037 }
                if (r0 == 0) goto L_0x000b
                monitor-exit(r6)     // Catch:{ all -> 0x0037 }
                return
            L_0x000b:
                lg r0 = defpackage.lg.this     // Catch:{ all -> 0x0037 }
                long r0 = r0.a     // Catch:{ all -> 0x0037 }
                long r2 = android.os.SystemClock.elapsedRealtime()     // Catch:{ all -> 0x0037 }
                r4 = 0
                long r0 = r0 - r2
                r2 = 0
                int r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
                r3 = 1
                if (r2 > 0) goto L_0x002e
                lg r2 = defpackage.lg.this     // Catch:{ all -> 0x0037 }
                lg$a r2 = r2.d     // Catch:{ all -> 0x0037 }
                if (r2 == 0) goto L_0x002e
                lg r0 = defpackage.lg.this     // Catch:{ all -> 0x0037 }
                r0.c = r3     // Catch:{ all -> 0x0037 }
                lg r0 = defpackage.lg.this     // Catch:{ all -> 0x0037 }
                lg$a r0 = r0.d     // Catch:{ all -> 0x0037 }
                r0.onTimeOut()     // Catch:{ all -> 0x0037 }
                goto L_0x0035
            L_0x002e:
                android.os.Message r2 = r5.obtainMessage(r3)     // Catch:{ all -> 0x0037 }
                r5.sendMessageDelayed(r2, r0)     // Catch:{ all -> 0x0037 }
            L_0x0035:
                monitor-exit(r6)     // Catch:{ all -> 0x0037 }
                return
            L_0x0037:
                r0 = move-exception
                monitor-exit(r6)     // Catch:{ all -> 0x0037 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.lg.AnonymousClass1.handleMessage(android.os.Message):void");
        }
    };

    /* renamed from: lg$a */
    /* compiled from: TimeOutWatcher */
    public interface a {
        void onTimeOut();

        void onTimeReset();
    }

    public lg(long j) {
        this.e = j;
    }

    public final synchronized void a() {
        this.b = true;
        this.f.removeMessages(1);
    }

    public final synchronized void a(a aVar) {
        this.d = aVar;
    }

    public final synchronized lg b() {
        this.b = false;
        this.c = false;
        if (this.e > 0 || this.d == null) {
            if (this.d != null) {
                this.d.onTimeReset();
            }
            this.a = SystemClock.elapsedRealtime() + this.e;
            this.f.sendMessage(this.f.obtainMessage(1));
            return this;
        }
        this.d.onTimeOut();
        return this;
    }

    public final synchronized void c() {
        if (this.d != null) {
            this.d.onTimeReset();
        }
        this.a = SystemClock.elapsedRealtime() + this.e;
    }
}
