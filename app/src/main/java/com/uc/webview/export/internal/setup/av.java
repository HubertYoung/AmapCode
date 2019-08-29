package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;

/* compiled from: ProGuard */
final class av implements ValueCallback<t> {
    final /* synthetic */ au a;

    av(au auVar) {
        this.a = auVar;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        ((t) obj).stop();
    }
}
