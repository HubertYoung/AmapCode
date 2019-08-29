package com.amap.bundle.webview.util;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepName
@KeepImplementations
public interface IBaichuanSDKWebviewApi {
    public static final IBaichuanSDKWebviewApi b = new IBaichuanSDKWebviewApi() {
        public final void a(WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient, String str) {
        }

        public final void a(anq anq) {
        }

        public final boolean a() {
            return false;
        }

        public final boolean a(String str) {
            return false;
        }

        public final void b(anq anq) {
        }

        public final boolean b() {
            return false;
        }

        public final boolean b(String str) {
            return false;
        }

        public final void c(anq anq) {
        }

        public final boolean c() {
            return false;
        }
    };

    void a(WebView webView, WebViewClient webViewClient, WebChromeClient webChromeClient, String str);

    void a(anq anq);

    boolean a();

    boolean a(String str);

    void b(anq anq);

    boolean b();

    boolean b(String str);

    void c(anq anq);

    boolean c();
}
