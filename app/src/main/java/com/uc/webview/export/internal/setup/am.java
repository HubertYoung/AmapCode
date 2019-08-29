package com.uc.webview.export.internal.setup;

import android.webkit.ValueCallback;

/* compiled from: ProGuard */
final class am implements ValueCallback<t> {
    final /* synthetic */ String a;
    final /* synthetic */ af b;

    am(af afVar, String str) {
        this.b = afVar;
        this.a = str;
    }

    public final /* synthetic */ void onReceiveValue(Object obj) {
        new bk();
        bk.a(this.a, ((cn) ((t) obj)).b);
    }
}
