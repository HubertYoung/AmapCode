package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;

/* compiled from: ProGuard */
final class al implements ValueCallback<t> {
    final ValueCallback a = ((ValueCallback) this.b.invokeO(10007, "updateProgress"));
    final /* synthetic */ af b;

    al(af afVar) {
        this.b = afVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        this.b.mPercent = ((Integer) ((t) obj).invokeO(UCAsyncTask.getPercent, new Object[0])).intValue();
        if (this.a != null) {
            this.a.onReceiveValue(this.b);
        }
    }
}
