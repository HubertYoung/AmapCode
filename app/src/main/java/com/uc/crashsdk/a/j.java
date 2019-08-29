package com.uc.crashsdk.a;

/* compiled from: ProGuard */
final class j implements Runnable {
    final /* synthetic */ Runnable a;

    j(Runnable runnable) {
        this.a = runnable;
    }

    public final void run() {
        synchronized (i.e) {
            if (i.e.get(this.a) != null) {
                i.e.remove(this.a);
            }
        }
        c.a("pre-run: " + this.a);
        this.a.run();
        c.a("end-run: " + this.a);
    }
}
