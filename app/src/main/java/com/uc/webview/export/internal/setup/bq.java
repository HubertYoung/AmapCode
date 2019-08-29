package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.uc.startup.StartupTrace;

/* compiled from: ProGuard */
final class bq implements ValueCallback<aa> {
    final /* synthetic */ bp a;

    bq(bp bpVar) {
        this.a = bpVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        StartupTrace.traceEvent("StandardSetupTask.runInternal PrecreateWebViewTask.exception");
    }
}
