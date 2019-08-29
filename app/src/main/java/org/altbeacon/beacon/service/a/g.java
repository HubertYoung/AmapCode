package org.altbeacon.beacon.service.a;

import android.support.annotation.MainThread;

/* compiled from: CycledLeScannerForJellyBeanMr2 */
final class g implements Runnable {
    final /* synthetic */ f a;

    g(f this$0) {
        this.a = this$0;
    }

    @MainThread
    public final void run() {
        this.a.a(Boolean.valueOf(true));
    }
}
