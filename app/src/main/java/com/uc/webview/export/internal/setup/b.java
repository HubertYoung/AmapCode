package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;
import com.uc.webview.export.extension.UCCore;

/* compiled from: ProGuard */
final class b implements ValueCallback<p> {
    final /* synthetic */ a a;

    b(a aVar) {
        this.a = aVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        ValueCallback valueCallback = (ValueCallback) this.a.a.invokeO(10007, UCCore.EVENT_DELETE_FILE_FINISH);
        if (valueCallback != null) {
            valueCallback.onReceiveValue(this.a.a);
        }
    }
}
