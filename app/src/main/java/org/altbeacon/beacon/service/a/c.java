package org.altbeacon.beacon.service.a;

import android.support.annotation.WorkerThread;
import org.altbeacon.beacon.b.d;

/* compiled from: CycledLeScanner */
final class c implements Runnable {
    final /* synthetic */ b a;

    c(b this$0) {
        this.a = this$0;
    }

    @WorkerThread
    public final void run() {
        d.a("CycledLeScanner", "Quitting scan thread", new Object[0]);
        this.a.v.quit();
    }
}
