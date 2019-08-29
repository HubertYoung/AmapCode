package com.amap.bundle.webview.util;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class BaichuanSDKProxy implements IBaichuanSDKWebviewApi {
    private IBaichuanSDKWebviewApi a;

    static class a {
        static BaichuanSDKProxy a = new BaichuanSDKProxy(0);
    }

    /* synthetic */ BaichuanSDKProxy(byte b) {
        this();
    }

    private BaichuanSDKProxy() {
    }

    public static void a(IBaichuanSDKWebviewApi iBaichuanSDKWebviewApi) {
        if (a.a.a == null) {
            a.a.a = iBaichuanSDKWebviewApi;
        }
    }

    public static IBaichuanSDKWebviewApi d() {
        return a.a;
    }

    public final void a(WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient, String str) {
        this.a.a(webView, webViewClient, webChromeClient, str);
    }

    public final boolean a(String str) {
        return this.a.a(str);
    }

    public final boolean a() {
        return this.a.a();
    }

    public final boolean b() {
        return this.a.b();
    }

    public final boolean c() {
        return this.a.c();
    }

    public final boolean b(String str) {
        return this.a.b(str);
    }

    public final void a(anq anq) {
        this.a.a(anq);
    }

    public final void b(anq anq) {
        this.a.b(anq);
    }

    public final void c(anq anq) {
        this.a.c(anq);
    }
}
