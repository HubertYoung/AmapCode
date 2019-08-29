package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.setup.UCSetupTask.a;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class cl implements ValueCallback<CALLBACK_TYPE> {
    final /* synthetic */ a a;

    cl(a aVar) {
        this.a = aVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        StringBuilder sb = new StringBuilder("SetupCrashImprover.startCallback MCE(");
        sb.append(this.a.b.exists());
        sb.append(",");
        sb.append(this.a.a.exists());
        sb.append(",");
        sb.append(this.a.c.exists());
        sb.append(")");
        Log.d("UCSetupTask", sb.toString());
        a.a(this.a);
        a aVar = this.a;
        try {
            if (!aVar.a.exists()) {
                aVar.a.createNewFile();
            } else if (!aVar.c.exists()) {
                aVar.c.createNewFile();
            } else {
                if (!aVar.b.exists()) {
                    aVar.b.createNewFile();
                }
            }
        } catch (Throwable unused) {
        }
    }
}
