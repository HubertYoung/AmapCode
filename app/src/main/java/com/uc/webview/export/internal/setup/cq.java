package com.uc.webview.export.internal.setup;

import android.util.Pair;
import android.webkit.ValueCallback;
import com.uc.webview.export.internal.utility.Log;
import com.uc.webview.export.internal.utility.g;
import com.uc.webview.export.internal.utility.j;

/* compiled from: ProGuard */
final class cq implements Runnable {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ bx b;
    final /* synthetic */ Pair c;
    final /* synthetic */ cn d;

    cq(cn cnVar, ValueCallback valueCallback, bx bxVar, Pair pair) {
        this.d = cnVar;
        this.a = valueCallback;
        this.b = bxVar;
        this.c = pair;
    }

    public final void run() {
        try {
            String e = g.e();
            Log.d("UpdateSetupTask", ".shareCoreWaitTimeout localDir:".concat(String.valueOf(e)));
            if (!j.a(e)) {
                this.a.onReceiveValue(this.d);
                this.b.a(8, null);
                return;
            }
            String f = g.f();
            Log.d("UpdateSetupTask", ".shareCoreWaitTimeout decFile:".concat(String.valueOf(f)));
            if (!j.a(f)) {
                this.a.onReceiveValue(this.d);
                this.b.a(8, null);
                return;
            }
            if (((Integer) this.c.first).intValue() != 1) {
                this.b.a(((Integer) this.c.first).intValue(), this.c.second);
            }
        } catch (Throwable th) {
            Log.d("UpdateSetupTask", ".shareCoreWaitTimeout Thread ", th);
            if (((Integer) this.c.first).intValue() != 1) {
                this.b.a(((Integer) this.c.first).intValue(), this.c.second);
            }
        }
    }
}
