package com.taobao.applink.i;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.net.URLDecoder;

public class a extends WebViewClient {
    private WebViewClient a;
    private com.taobao.applink.f.a.a b = null;

    public a(WebViewClient webViewClient) {
        this.a = webViewClient;
    }

    public com.taobao.applink.f.a.a a() {
        return this.b;
    }

    public void a(com.taobao.applink.f.a.a aVar) {
        if (aVar != null) {
            this.b = aVar;
        }
    }

    public void doUpdateVisitedHistory(WebView webView, String str, boolean z) {
        this.a.doUpdateVisitedHistory(webView, str, z);
    }

    public void onFormResubmission(WebView webView, Message message, Message message2) {
        this.a.onFormResubmission(webView, message, message2);
    }

    public void onLoadResource(WebView webView, String str) {
        this.a.onLoadResource(webView, str);
    }

    public void onPageFinished(WebView webView, String str) {
        this.a.onPageFinished(webView, str);
        com.taobao.applink.util.a.b(webView);
        com.taobao.applink.util.a.a(webView);
        if (this.b != null) {
            this.b.a(webView);
        }
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        this.a.onPageStarted(webView, str, bitmap);
    }

    public void onReceivedError(WebView webView, int i, String str, String str2) {
        this.a.onReceivedError(webView, i, str, str2);
    }

    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
        this.a.onReceivedHttpAuthRequest(webView, httpAuthHandler, str, str2);
    }

    @TargetApi(12)
    public void onReceivedLoginRequest(WebView webView, String str, String str2, String str3) {
        this.a.onReceivedLoginRequest(webView, str, str2, str3);
    }

    @TargetApi(8)
    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        this.a.onReceivedSslError(webView, sslErrorHandler, sslError);
    }

    public void onScaleChanged(WebView webView, float f, float f2) {
        this.a.onScaleChanged(webView, f, f2);
    }

    public void onTooManyRedirects(WebView webView, Message message, Message message2) {
        this.a.onTooManyRedirects(webView, message, message2);
    }

    public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
        this.a.onUnhandledKeyEvent(webView, keyEvent);
    }

    @TargetApi(11)
    public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        return this.a.shouldInterceptRequest(webView, str);
    }

    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        return this.a.shouldOverrideKeyEvent(webView, keyEvent);
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (this.b != null) {
            try {
                String decode = URLDecoder.decode(str, "UTF-8");
                if (decode.startsWith("tblink://return/")) {
                    this.b.a(webView, decode);
                    return true;
                } else if (decode.startsWith("tblink://")) {
                    this.b.b(webView);
                    return true;
                }
            } catch (Throwable unused) {
            }
        }
        return this.a.shouldOverrideUrlLoading(webView, str);
    }
}
