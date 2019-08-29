package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.alipay.mobile.common.logging.api.LogCategory;
import com.uc.webview.export.extension.UCCore;
import com.uc.webview.export.internal.interfaces.IWaStat;
import com.uc.webview.export.internal.setup.UCSubSetupTask.a;
import com.uc.webview.export.internal.utility.f;

/* compiled from: ProGuard */
final class k implements ValueCallback<t> {
    final /* synthetic */ BrowserSetupTask a;

    k(BrowserSetupTask browserSetupTask) {
        this.a = browserSetupTask;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        String message = tVar.getException().getRootCause().getMessage();
        if (tVar.getException().errCode() == 1001 || message.equals("Error on 7z decoding: 1")) {
            f.a(BrowserSetupTask.b(IWaStat.SETUP_DEFAULT_EXCEPTION, tVar), "kernel");
            this.a.callStatException(IWaStat.SETUP_DEFAULT_EXCEPTION, tVar.getException());
            BrowserSetupTask browserSetupTask = this.a;
            browserSetupTask.getClass();
            ((t) ((t) ((t) ((t) ((t) new ac().invoke(10001, this.a)).setOptions(this.a.b.mOptions)).onEvent((String) "stat", (ValueCallback<CALLBACK_TYPE>) new a<CALLBACK_TYPE>())).onEvent((String) "success", this.a.l)).onEvent((String) LogCategory.CATEGORY_EXCEPTION, this.a.m)).start();
        } else if (tVar.getException().errCode() != 3007) {
            f.a(BrowserSetupTask.b(IWaStat.SETUP_DEFAULT_EXCEPTION, tVar), "kernel");
            this.a.callStatException(IWaStat.SETUP_DEFAULT_EXCEPTION, tVar.getException());
            BrowserSetupTask.i(this.a);
            ((t) ((t) ((t) ((t) ((t) ((t) at.a(this.a.mOptions).invoke(10001, this.a)).setup((String) UCCore.OPTION_UCM_ZIP_FILE, (Object) this.a.e.getAbsolutePath())).onEvent("success", this.a.l)).onEvent((String) LogCategory.CATEGORY_EXCEPTION, this.a.m)).onEvent("stat", (ValueCallback<CALLBACK_TYPE>) new a<CALLBACK_TYPE>())).onEvent("setup", (ValueCallback<CALLBACK_TYPE>) new c<CALLBACK_TYPE>(this.a))).start();
        } else {
            this.a.m.onReceiveValue(tVar);
        }
    }
}
