package defpackage;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* renamed from: epq reason: default package */
/* compiled from: DataWriter */
class epq {
    private static final String a = "epq";
    private static epq c;
    /* access modifiers changed from: private */
    public int b;
    private List<epm> d = new ArrayList();
    /* access modifiers changed from: private */
    public List<epm> e = new ArrayList();
    /* access modifiers changed from: private */
    public PrintWriter f;
    /* access modifiers changed from: private */
    public Thread g;

    private epq() {
    }

    public static epq a() {
        if (c == null) {
            synchronized (epq.class) {
                try {
                    if (c == null) {
                        c = new epq();
                    }
                }
            }
        }
        return c;
    }

    public final synchronized void a(epr epr, epr epr2, epr epr3, long j, int i, int i2, boolean z) {
        synchronized (this) {
            try {
                eps eps = new eps(epr, epr2, epr3, j, i, i2, z);
                a((epm) eps);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    private synchronized void a(epm epm) {
        this.b++;
        this.d.add(epm);
        if (this.b % 500 == 0) {
            a(false);
        }
    }

    private synchronized void a(final boolean z) {
        if (this.f != null) {
            this.e.clear();
            this.e.addAll(this.d);
            this.d.clear();
            this.g = new Thread(new Runnable() {
                public final void run() {
                    for (epm epm : epq.this.e) {
                        epq.this.f.println(epm.toString());
                    }
                    epq.this.f.flush();
                    epq.this.g = null;
                    if (z) {
                        epq.this.f.close();
                        epq.this.f = null;
                        epq.this.b = 0;
                        epn.a();
                    }
                }
            }, "DataWriter");
            this.g.start();
        }
    }

    public final synchronized void b() {
        if (this.g == null || !this.g.isAlive()) {
            a(true);
        }
    }
}
