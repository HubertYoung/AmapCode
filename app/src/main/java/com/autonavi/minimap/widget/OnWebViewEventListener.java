package com.autonavi.minimap.widget;

import com.uc.webview.export.WebView;

public interface OnWebViewEventListener {
    void onReceivedTitle(WebView webView, String str);

    void onWebViewPageCanceled(WebView webView);

    void onWebViewPageFinished(WebView webView);

    void onWebViewPageRefresh(WebView webView);

    void onWebViewPageStart(WebView webView);
}
