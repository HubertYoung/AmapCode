package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.amap.location.sdk.fusion.LocationParams;
import java.io.File;

/* compiled from: ProGuard */
final class h implements ValueCallback<t> {
    final /* synthetic */ File a;
    final /* synthetic */ BrowserSetupTask b;

    h(BrowserSetupTask browserSetupTask, File file) {
        this.b = browserSetupTask;
        this.a = file;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        tVar.setException(new UCSetupException(4005, String.format("Multi crash detected in [%s].", new Object[]{this.a.getAbsolutePath()})));
        tVar.onEvent((String) LocationParams.PARA_COMMON_DIE, (ValueCallback<CALLBACK_TYPE>) null);
    }
}
