package com.alipay.mobile.nebula.webview;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Message;
import android.view.KeyEvent;
import android.webkit.WebResourceResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface APWebViewClient {
    void doUpdateVisitedHistory(APWebView aPWebView, String str, boolean z);

    String getJSBridge();

    String getPageUrl();

    Map getRequestMap();

    void onFirstVisuallyRender(APWebView aPWebView);

    void onFormResubmission(APWebView aPWebView, Message message, Message message2);

    void onLoadResource(APWebView aPWebView, String str);

    void onPageFinished(APWebView aPWebView, String str, long j);

    void onPageStarted(APWebView aPWebView, String str, Bitmap bitmap);

    void onReceivedError(APWebView aPWebView, int i, String str, String str2);

    void onReceivedHttpAuthRequest(APWebView aPWebView, APHttpAuthHandler aPHttpAuthHandler, String str, String str2);

    void onReceivedHttpError(APWebView aPWebView, int i, String str);

    void onReceivedLoginRequest(APWebView aPWebView, String str, String str2, String str3);

    void onReceivedResponseHeader(Map<String, List<String>> map);

    void onReceivedSslError(APWebView aPWebView, APSslErrorHandler aPSslErrorHandler, SslError sslError);

    void onResourceFinishLoad(APWebView aPWebView, String str, long j);

    void onResourceResponse(APWebView aPWebView, HashMap<String, String> hashMap);

    void onScaleChanged(APWebView aPWebView, float f, float f2);

    void onTooManyRedirects(APWebView aPWebView, Message message, Message message2);

    void onUnhandledKeyEvent(APWebView aPWebView, KeyEvent keyEvent);

    void onWebViewEvent(APWebView aPWebView, int i, Object obj);

    WebResourceResponse shouldInterceptRequest(APWebView aPWebView, APWebResourceRequest aPWebResourceRequest);

    WebResourceResponse shouldInterceptRequest(APWebView aPWebView, String str);

    boolean shouldInterceptResponse(APWebView aPWebView, HashMap<String, String> hashMap);

    boolean shouldOverrideKeyEvent(APWebView aPWebView, KeyEvent keyEvent);

    boolean shouldOverrideUrlLoading(APWebView aPWebView, String str);

    boolean shouldOverrideUrlLoadingForUC(APWebView aPWebView, String str, int i);
}
