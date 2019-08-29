package defpackage;

/* renamed from: fhj reason: default package */
/* compiled from: ConditionVariable */
public final class fhj {
    private volatile boolean a;

    public final synchronized void a(boolean z) {
        if (z) {
            a();
        } else {
            b();
        }
    }

    public final synchronized void a() {
        boolean z = this.a;
        this.a = true;
        if (!z) {
            notify();
        }
    }

    public final synchronized void b() {
        this.a = false;
    }

    public final synchronized void c() throws InterruptedException {
        while (!this.a) {
            wait();
        }
    }
}
