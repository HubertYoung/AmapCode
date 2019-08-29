package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;

/* compiled from: ProGuard */
final class ax implements ValueCallback<l> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ at b;

    ax(at atVar, ValueCallback valueCallback) {
        this.b = atVar;
        this.a = valueCallback;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        l lVar = (l) obj;
        if (this.a != null) {
            this.a.onReceiveValue(lVar.getStat());
        }
    }
}
