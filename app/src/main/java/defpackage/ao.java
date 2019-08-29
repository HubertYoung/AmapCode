package defpackage;

import java.util.concurrent.TimeUnit;

/* renamed from: ao reason: default package */
/* compiled from: SelfKillHeartbeatImpl */
public final class ao implements an, Runnable {
    private p a = null;
    private volatile boolean b = false;
    private volatile long c = System.currentTimeMillis();

    public final void a(p pVar) {
        if (pVar == null) {
            throw new NullPointerException("session is null");
        }
        this.a = pVar;
        this.c = System.currentTimeMillis() + 45000;
        ck.a(this, 45000, TimeUnit.MILLISECONDS);
    }

    public final void a() {
        this.b = true;
    }

    public final void b() {
        this.c = System.currentTimeMillis() + 45000;
    }

    public final void run() {
        if (!this.b) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < this.c - 1000) {
                ck.a(this, this.c - currentTimeMillis, TimeUnit.MILLISECONDS);
            } else {
                this.a.a(false);
            }
        }
    }
}
