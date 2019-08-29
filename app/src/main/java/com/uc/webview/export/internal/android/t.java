package com.uc.webview.export.internal.android;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebViewClient;
import com.uc.webview.export.WebResourceError;
import com.uc.webview.export.WebSettings;
import com.uc.webview.export.WebView;
import com.uc.webview.export.internal.SDKFactory;
import com.uc.webview.export.internal.interfaces.IPreloadManager;
import com.uc.webview.export.internal.interfaces.IWaStat.WaStat;

/* compiled from: ProGuard */
public final class t extends WebViewClient {
    private WebView a;
    private com.uc.webview.export.WebViewClient b;

    @TargetApi(23)
    /* compiled from: ProGuard */
    static class a extends WebResourceError {
        private final android.webkit.WebResourceError a;

        public a(android.webkit.WebResourceError webResourceError) {
            this.a = webResourceError;
        }

        public final int getErrorCode() {
            return this.a.getErrorCode();
        }

        public final CharSequence getDescription() {
            return this.a.getDescription();
        }
    }

    public t(WebView webView, com.uc.webview.export.WebViewClient webViewClient) {
        this.a = webView;
        this.b = webViewClient;
    }

    public final boolean shouldOverrideUrlLoading(android.webkit.WebView webView, String str) {
        return this.b.shouldOverrideUrlLoading(this.a, str);
    }

    @TargetApi(21)
    public final boolean shouldOverrideUrlLoading(android.webkit.WebView webView, WebResourceRequest webResourceRequest) {
        com.uc.webview.export.WebResourceRequest webResourceRequest2 = new com.uc.webview.export.WebResourceRequest(webResourceRequest.getMethod(), webResourceRequest.getRequestHeaders(), webResourceRequest.getUrl().toString(), webResourceRequest.hasGesture(), webResourceRequest.isForMainFrame());
        return this.b.shouldOverrideUrlLoading(this.a, webResourceRequest2);
    }

    public final void onPageStarted(android.webkit.WebView webView, String str, Bitmap bitmap) {
        this.b.onPageStarted(this.a, str, bitmap);
    }

    public final void onPageFinished(android.webkit.WebView webView, String str) {
        WaStat.statPV(str);
        this.b.onPageFinished(this.a, str);
    }

    public final void onLoadResource(android.webkit.WebView webView, String str) {
        this.b.onLoadResource(this.a, str);
    }

    @TargetApi(11)
    public final WebResourceResponse shouldInterceptRequest(android.webkit.WebView webView, String str) {
        com.uc.webview.export.WebResourceResponse shouldInterceptRequest = this.b.shouldInterceptRequest(this.a, str);
        if (shouldInterceptRequest == null) {
            try {
                if (this.a != null && !this.a.isDestroied()) {
                    WebSettings settings = this.a.getSettings();
                    if (settings != null) {
                        String preCacheScope = settings.getPreCacheScope();
                        if (preCacheScope != null && preCacheScope.length() > 0) {
                            IPreloadManager iPreloadManager = (IPreloadManager) SDKFactory.invoke(10060, new Object[0]);
                            if (iPreloadManager != null) {
                                shouldInterceptRequest = iPreloadManager.getPreloadResource(preCacheScope, IPreloadManager.SIR_COMMON_TYPE, str);
                            }
                        }
                    }
                }
            } catch (Throwable unused) {
            }
        }
        if (shouldInterceptRequest == null) {
            return null;
        }
        WebResourceResponse webResourceResponse = new WebResourceResponse(shouldInterceptRequest.getMimeType(), shouldInterceptRequest.getEncoding(), shouldInterceptRequest.getData());
        if (VERSION.SDK_INT >= 21) {
            webResourceResponse.setResponseHeaders(shouldInterceptRequest.getResponseHeaders());
            if (shouldInterceptRequest.getReasonPhrase() != null) {
                webResourceResponse.setStatusCodeAndReasonPhrase(shouldInterceptRequest.getStatusCode(), shouldInterceptRequest.getReasonPhrase());
            }
        }
        return webResourceResponse;
    }

    public final WebResourceResponse shouldInterceptRequest(android.webkit.WebView webView, WebResourceRequest webResourceRequest) {
        com.uc.webview.export.WebResourceRequest webResourceRequest2;
        if (VERSION.SDK_INT < 21) {
            return null;
        }
        try {
            if (VERSION.SDK_INT >= 24) {
                webResourceRequest2 = new com.uc.webview.export.WebResourceRequest(webResourceRequest.getMethod(), webResourceRequest.getRequestHeaders(), webResourceRequest.getUrl().toString(), webResourceRequest.hasGesture(), webResourceRequest.isForMainFrame(), webResourceRequest.isRedirect());
            } else {
                webResourceRequest2 = new com.uc.webview.export.WebResourceRequest(webResourceRequest.getMethod(), webResourceRequest.getRequestHeaders(), webResourceRequest.getUrl().toString(), webResourceRequest.hasGesture(), webResourceRequest.isForMainFrame());
            }
            com.uc.webview.export.WebResourceResponse shouldInterceptRequest = this.b.shouldInterceptRequest(this.a, webResourceRequest2);
            if (shouldInterceptRequest == null) {
                return shouldInterceptRequest(webView, webResourceRequest.getUrl().toString());
            }
            WebResourceResponse webResourceResponse = new WebResourceResponse(shouldInterceptRequest.getMimeType(), shouldInterceptRequest.getEncoding(), shouldInterceptRequest.getData());
            webResourceResponse.setResponseHeaders(shouldInterceptRequest.getResponseHeaders());
            if (shouldInterceptRequest.getReasonPhrase() != null) {
                webResourceResponse.setStatusCodeAndReasonPhrase(shouldInterceptRequest.getStatusCode(), shouldInterceptRequest.getReasonPhrase());
            }
            return webResourceResponse;
        } catch (Throwable unused) {
            return null;
        }
    }

    public final void onReceivedError(android.webkit.WebView webView, int i, String str, String str2) {
        this.b.onReceivedError(this.a, i, str, str2);
    }

    @TargetApi(23)
    public final void onReceivedError(android.webkit.WebView webView, WebResourceRequest webResourceRequest, android.webkit.WebResourceError webResourceError) {
        if (webResourceRequest.isForMainFrame()) {
            onReceivedError(webView, webResourceError.getErrorCode(), webResourceError.getDescription().toString(), webResourceRequest.getUrl().toString());
        }
        com.uc.webview.export.WebResourceRequest webResourceRequest2 = new com.uc.webview.export.WebResourceRequest(webResourceRequest.getMethod(), webResourceRequest.getRequestHeaders(), webResourceRequest.getUrl(), webResourceRequest.hasGesture(), webResourceRequest.isForMainFrame());
        this.b.onReceivedError(this.a, webResourceRequest2, new a(webResourceError));
    }

    @TargetApi(21)
    public final void onReceivedHttpError(android.webkit.WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        com.uc.webview.export.WebResourceRequest webResourceRequest2 = new com.uc.webview.export.WebResourceRequest(webResourceRequest.getMethod(), webResourceRequest.getRequestHeaders(), webResourceRequest.getUrl(), webResourceRequest.hasGesture(), webResourceRequest.isForMainFrame());
        com.uc.webview.export.WebResourceResponse webResourceResponse2 = new com.uc.webview.export.WebResourceResponse(webResourceResponse.getMimeType(), webResourceResponse.getEncoding(), webResourceResponse.getData());
        webResourceResponse2.setStatusCodeAndReasonPhrase(webResourceResponse.getStatusCode(), webResourceResponse.getReasonPhrase());
        webResourceResponse2.setResponseHeaders(webResourceResponse.getResponseHeaders());
        this.b.onReceivedHttpError(this.a, webResourceRequest2, webResourceResponse2);
    }

    public final void onFormResubmission(android.webkit.WebView webView, Message message, Message message2) {
        this.b.onFormResubmission(this.a, message, message2);
    }

    public final void doUpdateVisitedHistory(android.webkit.WebView webView, String str, boolean z) {
        this.b.doUpdateVisitedHistory(this.a, str, z);
    }

    public final void onReceivedSslError(android.webkit.WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        this.b.onReceivedSslError(this.a, new g(sslErrorHandler), sslError);
    }

    public final void onReceivedHttpAuthRequest(android.webkit.WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
        if (this.b != null) {
            this.b.onReceivedHttpAuthRequest(this.a, new c(httpAuthHandler), str, str2);
        } else {
            httpAuthHandler.cancel();
        }
    }

    public final boolean shouldOverrideKeyEvent(android.webkit.WebView webView, KeyEvent keyEvent) {
        return this.b.shouldOverrideKeyEvent(this.a, keyEvent);
    }

    public final void onUnhandledKeyEvent(android.webkit.WebView webView, KeyEvent keyEvent) {
        this.b.onUnhandledKeyEvent(this.a, keyEvent);
    }

    public final void onScaleChanged(android.webkit.WebView webView, float f, float f2) {
        this.b.onScaleChanged(this.a, f, f2);
    }

    public final void onReceivedLoginRequest(android.webkit.WebView webView, String str, String str2, String str3) {
        this.b.onReceivedLoginRequest(this.a, str, str2, str3);
    }
}
