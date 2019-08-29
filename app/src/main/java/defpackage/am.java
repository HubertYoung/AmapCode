package defpackage;

import com.alipay.mobile.tinyappcommon.h5plugin.H5SensorPlugin;
import java.util.concurrent.TimeUnit;

/* renamed from: am reason: default package */
/* compiled from: DefaultHeartbeatImpl */
public final class am implements an, Runnable {
    private p a;
    private volatile long b = 0;
    private volatile boolean c = false;
    private long d = 0;

    public final void a(p pVar) {
        if (pVar == null) {
            throw new NullPointerException("session is null");
        }
        this.a = pVar;
        this.d = (long) pVar.k.i();
        if (this.d <= 0) {
            this.d = 45000;
        }
        cl.b("awcn.DefaultHeartbeatImpl", "heartbeat start", pVar.p, "session", pVar, H5SensorPlugin.PARAM_INTERVAL, Long.valueOf(this.d));
        a(this.d);
    }

    public final void a() {
        if (this.a != null) {
            cl.b("awcn.DefaultHeartbeatImpl", "heartbeat stop", this.a.p, "session", this.a);
            this.c = true;
        }
    }

    public final void b() {
        this.b = System.currentTimeMillis() + this.d;
    }

    public final void run() {
        if (!this.c) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis < this.b - 1000) {
                a(this.b - currentTimeMillis);
            } else if (!m.h()) {
                if (cl.a(1)) {
                    cl.a("awcn.DefaultHeartbeatImpl", "heartbeat", this.a.p, "session", this.a);
                }
                this.a.d();
                a(this.d);
            } else {
                cl.d("awcn.DefaultHeartbeatImpl", "close session in background", this.a.p, "session", this.a);
                this.a.a(false);
            }
        }
    }

    private void a(long j) {
        try {
            this.b = System.currentTimeMillis() + j;
            ck.a(this, j + 50, TimeUnit.MILLISECONDS);
        } catch (Exception unused) {
            cl.e("awcn.DefaultHeartbeatImpl", "Submit heartbeat task failed.", this.a.p, new Object[0]);
        }
    }
}
