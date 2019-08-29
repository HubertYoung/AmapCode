package com.uc.webview.export.internal.android;

import android.net.Uri;
import android.webkit.ValueCallback;

/* compiled from: ProGuard */
final class k implements ValueCallback<Uri[]> {
    final /* synthetic */ ValueCallback a;
    final /* synthetic */ i b;

    k(i iVar, ValueCallback valueCallback) {
        this.b = iVar;
        this.a = valueCallback;
    }

    public final /* bridge */ /* synthetic */ void onReceiveValue(Object obj) {
        Uri[] uriArr = (Uri[]) obj;
        this.a.onReceiveValue(uriArr == null ? null : uriArr[0]);
    }
}
