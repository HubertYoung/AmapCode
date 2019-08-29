package com.ali.auth.third.ui;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.webkit.WebView;
import com.ali.auth.third.core.trace.SDKLogger;
import com.ali.auth.third.login.task.RefreshSidTask;
import com.ali.auth.third.ui.webview.AuthWebView;
import com.ali.auth.third.ui.webview.BaseWebViewClient;

class b extends BaseWebViewClient {
    final /* synthetic */ LoginWebViewActivity a;

    b(LoginWebViewActivity loginWebViewActivity) {
        this.a = loginWebViewActivity;
    }

    public void onLoadResource(WebView webView, String str) {
        super.onLoadResource(webView, str);
        SDKLogger.d(LoginWebViewActivity.TAG, "onLoadResource url=".concat(String.valueOf(str)));
    }

    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        SDKLogger.d(LoginWebViewActivity.TAG, "onPageFinished url=".concat(String.valueOf(str)));
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
        SDKLogger.d(LoginWebViewActivity.TAG, "onPageStarted url=".concat(String.valueOf(str)));
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        SDKLogger.d(LoginWebViewActivity.TAG, "shouldOverrideUrlLoading url=".concat(String.valueOf(str)));
        Uri parse = Uri.parse(str);
        if (this.a.a.isLoginUrl(str)) {
            new RefreshSidTask(this.a.authWebView).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[0]);
            return true;
        } else if (this.a.checkWebviewBridge(str)) {
            return this.a.a(parse);
        } else {
            if (webView instanceof AuthWebView) {
                ((AuthWebView) webView).loadUrl(str);
                return true;
            }
            webView.loadUrl(str);
            return true;
        }
    }
}
