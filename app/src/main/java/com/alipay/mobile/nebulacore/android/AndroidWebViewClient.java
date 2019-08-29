package com.alipay.mobile.nebulacore.android;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.net.TrafficStats;
import android.net.http.SslError;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alipay.mobile.nebula.util.H5ResourceCORSUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebResourceRequest;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebula.webview.APWebViewClient;
import com.alipay.mobile.nebulacore.env.H5Environment;
import java.util.HashMap;
import java.util.Map;

class AndroidWebViewClient extends WebViewClient {
    private APWebView a;
    private APWebViewClient b;
    private long c;
    private int d = H5Utils.getUid(H5Environment.getContext());

    AndroidWebViewClient(APWebView apWebView, APWebViewClient apWebViewClient) {
        this.b = apWebViewClient;
        this.a = apWebView;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return this.b != null && this.b.shouldOverrideUrlLoading(this.a, url);
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (this.b != null) {
            this.c = a();
            this.b.onPageStarted(this.a, url, favicon);
        }
    }

    private long a() {
        long begUidRx = 0;
        try {
            return TrafficStats.getUidRxBytes(this.d);
        } catch (Exception e) {
            return begUidRx;
        }
    }

    public void onPageFinished(WebView view, String url) {
        if (this.b != null) {
            this.b.onPageFinished(this.a, url, a() - this.c);
        }
    }

    public void onLoadResource(WebView view, String url) {
        if (this.b != null) {
            this.b.onLoadResource(this.a, url);
        }
    }

    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (this.b != null) {
            return this.b.shouldInterceptRequest(this.a, url);
        }
        return null;
    }

    @TargetApi(21)
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        WebResourceResponse response = null;
        if (this.b != null) {
            response = this.b.shouldInterceptRequest(this.a, (APWebResourceRequest) new AndroidWebResourceRequest(request));
            if (response != null) {
                String requestUrl = request.getUrl().toString();
                Map rspHeader = new HashMap();
                rspHeader.put("Cache-Control", "no-cache");
                String pageUrl = this.b.getPageUrl();
                if (pageUrl != null && H5ResourceCORSUtil.needAddHeader(requestUrl)) {
                    String corsUrl = H5ResourceCORSUtil.getCORSUrl(pageUrl);
                    if (corsUrl != null) {
                        rspHeader.put("Access-Control-Allow-Origin", corsUrl);
                    }
                }
                String headUrl = H5ResourceCORSUtil.getCORSUrl(pageUrl);
                if (!rspHeader.containsKey("Access-Control-Allow-Origin") && H5Utils.enableCheckCrossOrigin() && !TextUtils.isEmpty(requestUrl) && H5Utils.containNebulaAddcors(requestUrl) && !TextUtils.isEmpty(pageUrl) && !TextUtils.isEmpty(headUrl)) {
                    rspHeader.put("Access-Control-Allow-Origin", headUrl);
                }
                if (!rspHeader.containsKey("Access-Control-Allow-Origin") && H5Utils.addChooseImageCrossOrigin(requestUrl) && !TextUtils.isEmpty(pageUrl) && !TextUtils.isEmpty(headUrl)) {
                    rspHeader.put("Access-Control-Allow-Origin", headUrl);
                }
                response.setResponseHeaders(rspHeader);
            }
        }
        return response;
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (this.b != null) {
            this.b.onReceivedError(this.a, errorCode, description, failingUrl);
        }
    }

    @TargetApi(23)
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        if (this.b != null) {
            this.b.onReceivedHttpError(this.a, errorResponse != null ? errorResponse.getStatusCode() : 0, request != null ? request.getUrl().toString() : "");
        }
    }

    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        if (this.b != null) {
            this.b.onFormResubmission(this.a, dontResend, resend);
        }
    }

    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if (this.b != null) {
            this.b.doUpdateVisitedHistory(this.a, url, isReload);
        }
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (this.b != null) {
            this.b.onReceivedSslError(this.a, new AndroidSslErrorHandler(handler), new AndroidSslError(error));
        }
    }

    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (this.b != null) {
            this.b.onReceivedHttpAuthRequest(this.a, new AndroidHttpAuthHandler(handler), host, realm);
        }
    }

    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return this.b != null && this.b.shouldOverrideKeyEvent(this.a, event);
    }

    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if (this.a != null) {
            this.b.onUnhandledKeyEvent(this.a, event);
        }
    }

    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        if (this.b != null) {
            this.b.onScaleChanged(this.a, oldScale, newScale);
        }
    }
}
