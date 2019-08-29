package com.alipay.sdk.auth;

import android.webkit.WebView;

final class c implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ AuthActivity b;

    c(AuthActivity authActivity, String str) {
        this.b = authActivity;
        this.a = str;
    }

    public final void run() {
        try {
            WebView h = this.b.c;
            StringBuilder sb = new StringBuilder("javascript:");
            sb.append(this.a);
            h.loadUrl(sb.toString());
        } catch (Exception unused) {
        }
    }
}
