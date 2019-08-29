package com.alibaba.baichuan.android.trade.c.b;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Message;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.lang.ref.WeakReference;

public class b extends f {
    private WeakReference a;

    public b(WebViewClient webViewClient, d dVar) {
        super(dVar);
        this.a = new WeakReference(webViewClient);
    }

    public void doUpdateVisitedHistory(WebView webView, String str, boolean z) {
        if (this.a != null && this.a.get() != null) {
            ((WebViewClient) this.a.get()).doUpdateVisitedHistory(webView, str, z);
        }
    }

    public void onFormResubmission(WebView webView, Message message, Message message2) {
        if (this.a != null && this.a.get() != null) {
            ((WebViewClient) this.a.get()).onFormResubmission(webView, message, message2);
        }
    }

    public void onLoadResource(WebView webView, String str) {
        if (this.a != null && this.a.get() != null) {
            ((WebViewClient) this.a.get()).onLoadResource(webView, str);
        }
    }

    public void onPageCommitVisible(WebView webView, String str) {
        if (VERSION.SDK_INT >= 23 && this.a != null && this.a.get() != null) {
            ((WebViewClient) this.a.get()).onPageCommitVisible(webView, str);
        }
    }

    public void onPageFinished(WebView webView, String str) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebViewClient) this.a.get()).onPageFinished(webView, str);
        }
        super.onPageFinished(webView, str);
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebViewClient) this.a.get()).onPageStarted(webView, str, bitmap);
        }
        super.onPageStarted(webView, str, bitmap);
    }

    public void onReceivedClientCertRequest(WebView webView, ClientCertRequest clientCertRequest) {
        if (VERSION.SDK_INT >= 21 && this.a != null && this.a.get() != null) {
            ((WebViewClient) this.a.get()).onReceivedClientCertRequest(webView, clientCertRequest);
        }
    }

    public void onReceivedError(WebView webView, int i, String str, String str2) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebViewClient) this.a.get()).onReceivedError(webView, i, str, str2);
        }
        super.onReceivedError(webView, i, str, str2);
    }

    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
        if (this.a != null && this.a.get() != null) {
            ((WebViewClient) this.a.get()).onReceivedHttpAuthRequest(webView, httpAuthHandler, str, str2);
        }
    }

    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        if (VERSION.SDK_INT >= 23 && this.a != null && this.a.get() != null) {
            ((WebViewClient) this.a.get()).onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        }
    }

    public void onReceivedLoginRequest(WebView webView, String str, String str2, String str3) {
        if (VERSION.SDK_INT >= 23 && this.a != null && this.a.get() != null) {
            ((WebViewClient) this.a.get()).onReceivedLoginRequest(webView, str, str2, str3);
        }
    }

    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        if (this.a != null && this.a.get() != null) {
            ((WebViewClient) this.a.get()).onReceivedSslError(webView, sslErrorHandler, sslError);
        }
    }

    public void onScaleChanged(WebView webView, float f, float f2) {
        if (this.a != null && this.a.get() != null) {
            ((WebViewClient) this.a.get()).onScaleChanged(webView, f, f2);
        }
    }

    @Deprecated
    public void onTooManyRedirects(WebView webView, Message message, Message message2) {
        if (this.a != null && this.a.get() != null) {
            ((WebViewClient) this.a.get()).onTooManyRedirects(webView, message, message2);
        }
    }

    public void onUnhandledInputEvent(WebView webView, InputEvent inputEvent) {
        if (VERSION.SDK_INT >= 23 && this.a != null && this.a.get() != null) {
            ((WebViewClient) this.a.get()).onUnhandledInputEvent(webView, inputEvent);
        }
    }

    public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
        if (this.a != null && this.a.get() != null) {
            ((WebViewClient) this.a.get()).onUnhandledKeyEvent(webView, keyEvent);
        }
    }

    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        return (VERSION.SDK_INT < 21 || this.a == null || this.a.get() == null) ? super.shouldInterceptRequest(webView, webResourceRequest) : ((WebViewClient) this.a.get()).shouldInterceptRequest(webView, webResourceRequest);
    }

    @Deprecated
    public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        return (VERSION.SDK_INT <= 23 || this.a == null || this.a.get() == null) ? super.shouldInterceptRequest(webView, str) : ((WebViewClient) this.a.get()).shouldInterceptRequest(webView, str);
    }

    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        return (this.a == null || this.a.get() == null) ? super.shouldOverrideKeyEvent(webView, keyEvent) : ((WebViewClient) this.a.get()).shouldOverrideKeyEvent(webView, keyEvent);
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        if (!((this.a == null || this.a.get() == null) ? false : ((WebViewClient) this.a.get()).shouldOverrideUrlLoading(webView, str))) {
            return super.shouldOverrideUrlLoading(webView, str);
        }
        return true;
    }
}
