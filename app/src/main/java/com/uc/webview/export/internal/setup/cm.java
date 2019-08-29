package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.setup.UCSetupTask.a;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class cm implements ValueCallback<CALLBACK_TYPE> {
    final /* synthetic */ a a;

    cm(a aVar) {
        this.a = aVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        StringBuilder sb = new StringBuilder("SetupCrashImprover.dieCallback MCE(");
        sb.append(this.a.b.exists());
        sb.append(",");
        sb.append(this.a.a.exists());
        sb.append(",");
        sb.append(this.a.c.exists());
        sb.append(")");
        Log.d("UCSetupTask", sb.toString());
        this.a.a();
    }
}
