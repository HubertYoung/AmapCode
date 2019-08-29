package com.alibaba.baichuan.android.trade.c.b;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebChromeClient.FileChooserParams;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import java.lang.ref.WeakReference;

public class a extends c {
    private WeakReference a;

    public a(WebChromeClient webChromeClient, d dVar) {
        super(dVar, true);
        this.a = new WeakReference(webChromeClient);
    }

    public Bitmap getDefaultVideoPoster() {
        return (this.a == null || this.a.get() == null) ? super.getDefaultVideoPoster() : ((WebChromeClient) this.a.get()).getDefaultVideoPoster();
    }

    public View getVideoLoadingProgressView() {
        return (this.a == null || this.a.get() == null) ? super.getVideoLoadingProgressView() : ((WebChromeClient) this.a.get()).getVideoLoadingProgressView();
    }

    public void getVisitedHistory(ValueCallback valueCallback) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).getVisitedHistory(valueCallback);
        }
        super.getVisitedHistory(valueCallback);
    }

    public void onCloseWindow(WebView webView) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onCloseWindow(webView);
        }
        super.onCloseWindow(webView);
    }

    @Deprecated
    public void onConsoleMessage(String str, int i, String str2) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onConsoleMessage(str, i, str2);
        }
        super.onConsoleMessage(str, i, str2);
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        boolean onConsoleMessage = (this.a == null || this.a.get() == null) ? false : ((WebChromeClient) this.a.get()).onConsoleMessage(consoleMessage);
        return !onConsoleMessage ? super.onConsoleMessage(consoleMessage) : onConsoleMessage;
    }

    public boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
        return (this.a == null || this.a.get() == null) ? super.onCreateWindow(webView, z, z2, message) : ((WebChromeClient) this.a.get()).onCreateWindow(webView, z, z2, message);
    }

    @Deprecated
    public void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, QuotaUpdater quotaUpdater) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onExceededDatabaseQuota(str, str2, j, j2, j3, quotaUpdater);
        }
        super.onExceededDatabaseQuota(str, str2, j, j2, j3, quotaUpdater);
    }

    public void onGeolocationPermissionsHidePrompt() {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onGeolocationPermissionsHidePrompt();
        }
        super.onGeolocationPermissionsHidePrompt();
    }

    public void onGeolocationPermissionsShowPrompt(String str, Callback callback) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onGeolocationPermissionsShowPrompt(str, callback);
        }
        super.onGeolocationPermissionsShowPrompt(str, callback);
    }

    public void onHideCustomView() {
    }

    public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        return (this.a == null || this.a.get() == null) ? super.onJsAlert(webView, str, str2, jsResult) : ((WebChromeClient) this.a.get()).onJsAlert(webView, str, str2, jsResult);
    }

    public boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
        return (this.a == null || this.a.get() == null) ? super.onJsBeforeUnload(webView, str, str2, jsResult) : ((WebChromeClient) this.a.get()).onJsBeforeUnload(webView, str, str2, jsResult);
    }

    public boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
        return (this.a == null || this.a.get() == null) ? super.onJsConfirm(webView, str, str2, jsResult) : ((WebChromeClient) this.a.get()).onJsConfirm(webView, str, str2, jsResult);
    }

    public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        boolean onJsPrompt = (this.a == null || this.a.get() == null) ? false : ((WebChromeClient) this.a.get()).onJsPrompt(webView, str, str2, str3, jsPromptResult);
        return !onJsPrompt ? super.onJsPrompt(webView, str, str2, str3, jsPromptResult) : onJsPrompt;
    }

    public boolean onJsTimeout() {
        return (this.a == null || this.a.get() == null) ? super.onJsTimeout() : ((WebChromeClient) this.a.get()).onJsTimeout();
    }

    @TargetApi(21)
    public void onPermissionRequest(PermissionRequest permissionRequest) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onPermissionRequest(permissionRequest);
        }
        super.onPermissionRequest(permissionRequest);
    }

    @TargetApi(21)
    public void onPermissionRequestCanceled(PermissionRequest permissionRequest) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onPermissionRequestCanceled(permissionRequest);
        }
        super.onPermissionRequestCanceled(permissionRequest);
    }

    public void onProgressChanged(WebView webView, int i) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onProgressChanged(webView, i);
        }
        super.onProgressChanged(webView, i);
    }

    @Deprecated
    public void onReachedMaxAppCacheSize(long j, long j2, QuotaUpdater quotaUpdater) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onReachedMaxAppCacheSize(j, j2, quotaUpdater);
        }
        super.onReachedMaxAppCacheSize(j, j2, quotaUpdater);
    }

    public void onReceivedIcon(WebView webView, Bitmap bitmap) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onReceivedIcon(webView, bitmap);
        }
        super.onReceivedIcon(webView, bitmap);
    }

    public void onReceivedTitle(WebView webView, String str) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onReceivedTitle(webView, str);
        }
        super.onReceivedTitle(webView, str);
    }

    public void onReceivedTouchIconUrl(WebView webView, String str, boolean z) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onReceivedTouchIconUrl(webView, str, z);
        }
        super.onReceivedTouchIconUrl(webView, str, z);
    }

    public void onRequestFocus(WebView webView) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onRequestFocus(webView);
        }
        super.onRequestFocus(webView);
    }

    @TargetApi(14)
    @Deprecated
    public void onShowCustomView(View view, int i, CustomViewCallback customViewCallback) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onShowCustomView(view, i, customViewCallback);
        }
        super.onShowCustomView(view, i, customViewCallback);
    }

    public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
        if (!(this.a == null || this.a.get() == null)) {
            ((WebChromeClient) this.a.get()).onShowCustomView(view, customViewCallback);
        }
        super.onShowCustomView(view, customViewCallback);
    }

    @TargetApi(21)
    public boolean onShowFileChooser(WebView webView, ValueCallback valueCallback, FileChooserParams fileChooserParams) {
        return (this.a == null || this.a.get() == null) ? super.onShowFileChooser(webView, valueCallback, fileChooserParams) : ((WebChromeClient) this.a.get()).onShowFileChooser(webView, valueCallback, fileChooserParams);
    }
}
