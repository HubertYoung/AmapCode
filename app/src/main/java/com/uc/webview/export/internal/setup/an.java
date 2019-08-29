package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;

/* compiled from: ProGuard */
final class an implements ValueCallback<t> {
    final ValueCallback a = ((ValueCallback) this.b.invokeO(10007, "downloadException"));
    final /* synthetic */ af b;

    an(af afVar) {
        this.b = afVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        if (this.a != null) {
            if (tVar.getExtraException() != null) {
                this.b.setExtraException(tVar.getExtraException());
            }
            this.a.onReceiveValue(this.b);
        }
    }
}
