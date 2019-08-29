package com.uc.webview.export.internal.android;

import com.uc.webview.export.JsResult;

/* compiled from: ProGuard */
final class e implements JsResult {
    private android.webkit.JsResult a;

    e(android.webkit.JsResult jsResult) {
        this.a = jsResult;
    }

    public final void cancel() {
        this.a.cancel();
    }

    public final void confirm() {
        this.a.confirm();
    }
}
