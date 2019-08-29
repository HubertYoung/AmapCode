package com.uc.crashsdk.a;

import com.uc.crashsdk.b;

/* compiled from: ProGuard */
final class g implements Runnable {
    g() {
    }

    public final void run() {
        synchronized (f.b) {
            f.f = null;
            f.b(!b.t());
            if (h.b(f.f)) {
                k.b(f.f);
            }
        }
    }
}
