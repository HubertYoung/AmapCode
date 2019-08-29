package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.extension.UCCore;

/* compiled from: ProGuard */
final class ah implements ValueCallback<p> {
    final /* synthetic */ ag a;

    ah(ag agVar) {
        this.a = agVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        ValueCallback valueCallback = (ValueCallback) this.a.a.invokeO(10007, UCCore.EVENT_DELETE_FILE_FINISH);
        if (valueCallback != null) {
            valueCallback.onReceiveValue(this.a.a);
        }
    }
}
