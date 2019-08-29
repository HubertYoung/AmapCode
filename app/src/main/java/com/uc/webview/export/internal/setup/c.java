package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;

/* compiled from: ProGuard */
final class c implements ValueCallback<t> {
    final /* synthetic */ BrowserSetupTask a;

    c(BrowserSetupTask browserSetupTask) {
        this.a = browserSetupTask;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        if (UCSetupTask.getTotalLoadedUCM() != null) {
            tVar.stop();
        }
        this.a.i.onReceiveValue(tVar);
    }
}
