package com.alipay.mobile.android.verify.bridge;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/* compiled from: PopWebViewDialog */
class g extends WebViewClient {
    final /* synthetic */ d a;

    g(d dVar) {
        this.a = dVar;
    }

    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        this.a.c.setText(webView.getTitle());
    }
}
