package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IWaStat;

/* compiled from: ProGuard */
final class g implements ValueCallback<t> {
    final /* synthetic */ t a;
    final /* synthetic */ BrowserSetupTask b;

    g(BrowserSetupTask browserSetupTask, t tVar) {
        this.b = browserSetupTask;
        this.a = tVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        this.b.callStatException(IWaStat.SETUP_EXTRA_EXCEPTION, tVar.getException());
        if (Boolean.FALSE.booleanValue() && this.b.isNeedRestartError(tVar.getException().errCode())) {
            BrowserSetupTask.i(this.b);
        }
        if (this.a != null) {
            this.b.b.start();
        }
    }
}
