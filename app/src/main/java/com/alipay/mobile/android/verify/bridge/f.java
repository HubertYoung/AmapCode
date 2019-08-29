package com.alipay.mobile.android.verify.bridge;

import android.webkit.WebChromeClient;
import android.webkit.WebView;

/* compiled from: PopWebViewDialog */
class f extends WebChromeClient {
    final /* synthetic */ d a;

    f(d dVar) {
        this.a = dVar;
    }

    public void onReceivedTitle(WebView webView, String str) {
        super.onReceivedTitle(webView, str);
        this.a.c.setText(str);
    }
}
