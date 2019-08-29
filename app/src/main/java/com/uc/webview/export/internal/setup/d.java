package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IWaStat;

/* compiled from: ProGuard */
final class d implements ValueCallback<t> {
    final /* synthetic */ BrowserSetupTask a;

    d(BrowserSetupTask browserSetupTask) {
        this.a = browserSetupTask;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        if (SDKFactory.B != null) {
            SDKFactory.B.onReceiveValue(tVar.getException());
        }
        if (this.a.c != null) {
            this.a.callStatException(IWaStat.SETUP_REPAIR_EXCEPTION, tVar.getException());
            ((t) ((t) ((t) this.a.c.invoke(10001, this.a)).onEvent((String) "success", this.a.l)).onEvent((String) LogCategory.CATEGORY_EXCEPTION, this.a.m)).start();
            this.a.c = null;
            return;
        }
        if (Boolean.FALSE.booleanValue()) {
            UCSetupException exception = tVar.getException();
            if (this.a.isNoDiskSpaceError(exception.errCode()) || this.a.isNeedRestartError(exception.errCode())) {
                BrowserSetupTask.i(this.a);
            }
        }
        this.a.setException(tVar.getException());
    }
}
