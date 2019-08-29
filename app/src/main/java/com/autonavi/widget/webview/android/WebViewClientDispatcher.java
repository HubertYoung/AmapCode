package com.autonavi.widget.webview.android;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.ArrayList;
import java.util.List;

public class WebViewClientDispatcher extends WebViewClient {
    private List<WebViewClient> mWebViewClients = new ArrayList();

    public void addWebViewClient(WebViewClient webViewClient) {
        if (!this.mWebViewClients.contains(webViewClient)) {
            this.mWebViewClients.add(webViewClient);
        }
    }

    public void removeWebViewClient(WebViewClient webViewClient) {
        if (this.mWebViewClients.contains(webViewClient)) {
            this.mWebViewClients.remove(webViewClient);
        }
    }

    public void close() {
        this.mWebViewClients.clear();
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).onPageStarted(webView, str, bitmap);
        }
    }

    public void onFormResubmission(WebView webView, Message message, Message message2) {
        try {
            for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
                this.mWebViewClients.get(size).onFormResubmission(webView, message, Message.obtain(message2));
            }
        } catch (Exception unused) {
        }
    }

    public void onLoadResource(WebView webView, String str) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).onLoadResource(webView, str);
        }
    }

    public void onPageCommitVisible(WebView webView, String str) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).onLoadResource(webView, str);
        }
    }

    public void onPageFinished(WebView webView, String str) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).onPageFinished(webView, str);
        }
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            if (this.mWebViewClients.get(size).shouldOverrideUrlLoading(webView, str)) {
                return true;
            }
        }
        return false;
    }

    @TargetApi(21)
    public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            WebResourceResponse shouldInterceptRequest = this.mWebViewClients.get(size).shouldInterceptRequest(webView, webResourceRequest);
            if (shouldInterceptRequest != null) {
                return shouldInterceptRequest;
            }
        }
        return super.shouldInterceptRequest(webView, webResourceRequest);
    }

    @TargetApi(23)
    public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).onReceivedError(webView, webResourceRequest, webResourceError);
        }
    }

    @TargetApi(23)
    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).onReceivedHttpError(webView, webResourceRequest, webResourceResponse);
        }
    }

    public void doUpdateVisitedHistory(WebView webView, String str, boolean z) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).doUpdateVisitedHistory(webView, str, z);
        }
    }

    public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).onReceivedSslError(webView, sslErrorHandler, sslError);
        }
    }

    @TargetApi(23)
    public void onReceivedClientCertRequest(WebView webView, ClientCertRequest clientCertRequest) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).onReceivedClientCertRequest(webView, clientCertRequest);
        }
    }

    public void onReceivedHttpAuthRequest(WebView webView, HttpAuthHandler httpAuthHandler, String str, String str2) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).onReceivedHttpAuthRequest(webView, httpAuthHandler, str, str2);
        }
    }

    public boolean shouldOverrideKeyEvent(WebView webView, KeyEvent keyEvent) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            if (this.mWebViewClients.get(size).shouldOverrideKeyEvent(webView, keyEvent)) {
                return true;
            }
        }
        return false;
    }

    @TargetApi(21)
    public void onUnhandledInputEvent(WebView webView, InputEvent inputEvent) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).onUnhandledInputEvent(webView, inputEvent);
        }
    }

    public void onScaleChanged(WebView webView, float f, float f2) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).onScaleChanged(webView, f, f2);
        }
    }

    public void onReceivedLoginRequest(WebView webView, String str, String str2, String str3) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).onReceivedLoginRequest(webView, str, str2, str3);
        }
    }

    public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            WebResourceResponse shouldInterceptRequest = this.mWebViewClients.get(size).shouldInterceptRequest(webView, str);
            if (shouldInterceptRequest != null) {
                return shouldInterceptRequest;
            }
        }
        return super.shouldInterceptRequest(webView, str);
    }

    public void onTooManyRedirects(WebView webView, Message message, Message message2) {
        try {
            for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
                this.mWebViewClients.get(size).onTooManyRedirects(webView, message, Message.obtain(message2));
            }
        } catch (Exception unused) {
        }
    }

    public void onReceivedError(WebView webView, int i, String str, String str2) {
        for (int size = this.mWebViewClients.size() - 1; size >= 0; size--) {
            this.mWebViewClients.get(size).onReceivedError(webView, i, str, str2);
        }
    }

    public void onUnhandledKeyEvent(WebView webView, KeyEvent keyEvent) {
        super.onUnhandledKeyEvent(webView, keyEvent);
    }
}
