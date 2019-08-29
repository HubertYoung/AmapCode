package com.alipay.mobile.h5container.api;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.ConsoleMessage;
import com.alipay.mobile.nebula.webview.APSslErrorHandler;
import com.alipay.mobile.nebula.webview.APWebView;

public interface H5WebDriverHelper {
    public static final H5WebDriverHelper defaultHelper = new H5WebDriverHelper() {
        public final void onWebViewCreated(APWebView view) {
        }

        public final void onWebViewDestroyed(APWebView view) {
        }

        public final void shouldOverrideUrlLoading(APWebView view, String url) {
        }

        public final void onPageStarted(APWebView view, String url, Bitmap favicon) {
        }

        public final void onPageFinished(APWebView view, String url) {
        }

        public final void doUpdateVisitedHistory(APWebView view, String url, boolean isReload) {
        }

        public final void onReceivedError(APWebView view, int errorCode, String description, String failingUrl) {
        }

        public final void onReceivedSslError(APWebView view, APSslErrorHandler handler, SslError error) {
        }

        public final void onConsoleMessage(ConsoleMessage consoleMessage) {
        }
    };

    void doUpdateVisitedHistory(APWebView aPWebView, String str, boolean z);

    void onConsoleMessage(ConsoleMessage consoleMessage);

    void onPageFinished(APWebView aPWebView, String str);

    void onPageStarted(APWebView aPWebView, String str, Bitmap bitmap);

    void onReceivedError(APWebView aPWebView, int i, String str, String str2);

    void onReceivedSslError(APWebView aPWebView, APSslErrorHandler aPSslErrorHandler, SslError sslError);

    void onWebViewCreated(APWebView aPWebView);

    void onWebViewDestroyed(APWebView aPWebView);

    void shouldOverrideUrlLoading(APWebView aPWebView, String str);
}
