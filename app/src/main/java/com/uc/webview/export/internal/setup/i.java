package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.internal.interfaces.IWaStat;

/* compiled from: ProGuard */
final class i implements ValueCallback<t> {
    final /* synthetic */ BrowserSetupTask a;

    i(BrowserSetupTask browserSetupTask) {
        this.a = browserSetupTask;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        this.a.callStatException(IWaStat.SETUP_DEFAULT_EXCEPTION, tVar.getException());
        this.a.setRepairInfo(new UCMRepairInfo(1));
        this.a.callback("repair");
        BrowserSetupTask.a(this.a, tVar, this.a.getRepairInfo());
    }
}
