package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class cr implements ValueCallback<t> {
    final ValueCallback a = ((ValueCallback) this.b.invokeO(10007, FunctionSupportConfiger.SWITCH_TAG));
    final /* synthetic */ cn b;

    cr(cn cnVar) {
        this.b = cnVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        Log.d("UpdateSetupTask", "switch callback.");
        if (this.a != null) {
            this.a.onReceiveValue(tVar);
        }
    }
}
