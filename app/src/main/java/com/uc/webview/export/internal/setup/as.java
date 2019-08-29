package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.amap.bundle.cloudconfig.appinit.FunctionSupportConfiger;
import com.uc.webview.export.internal.utility.Log;

/* compiled from: ProGuard */
final class as implements ValueCallback<t> {
    final /* synthetic */ af a;

    as(af afVar) {
        this.a = afVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        Log.d("SdkSetupTask", "mSwitchCB ".concat(String.valueOf(tVar)));
        ValueCallback valueCallback = (ValueCallback) this.a.invokeO(10007, FunctionSupportConfiger.SWITCH_TAG);
        if (valueCallback != null) {
            valueCallback.onReceiveValue(this.a);
        }
        if (tVar.getLoadedUCM() != null) {
            af.a(this.a, tVar.getLoadedUCM());
            af.e(this.a);
        }
    }
}
