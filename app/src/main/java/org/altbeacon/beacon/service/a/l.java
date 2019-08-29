package org.altbeacon.beacon.service.a;

import android.support.annotation.MainThread;

/* compiled from: CycledLeScannerForLollipop */
final class l implements Runnable {
    final /* synthetic */ k a;

    l(k this$0) {
        this.a = this$0;
    }

    @MainThread
    public final void run() {
        this.a.a(Boolean.valueOf(true));
    }
}
