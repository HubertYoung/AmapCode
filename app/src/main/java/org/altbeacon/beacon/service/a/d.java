package org.altbeacon.beacon.service.a;

import android.support.annotation.MainThread;

/* compiled from: CycledLeScanner */
final class d implements Runnable {
    final /* synthetic */ b a;

    d(b this$0) {
        this.a = this$0;
    }

    @MainThread
    public final void run() {
        this.a.i();
    }
}
