package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;

/* compiled from: ProGuard */
final class aw implements ValueCallback<t> {
    final /* synthetic */ au a;

    aw(au auVar) {
        this.a = auVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        t tVar = (t) obj;
        if (this.a.c != null) {
            this.a.c.onReceiveValue(tVar.getStat());
        }
    }
}
