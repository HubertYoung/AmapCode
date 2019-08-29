package com.alipay.mobile.worker;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebResourceResponse;
import com.alipay.mobile.nebula.appcenter.res.H5ResourceManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.webview.APHttpAuthHandler;
import com.alipay.mobile.nebula.webview.APSslErrorHandler;
import com.alipay.mobile.nebula.webview.APWebResourceRequest;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebula.webview.APWebViewClient;
import com.alipay.mobile.tinyapp.worker.R;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkerWebViewClient implements APWebViewClient {
    private String a = ("WorkerWebViewClient_" + hashCode());
    private WebWorker b;

    public WorkerWebViewClient(WebWorker worker) {
        this.b = worker;
    }

    public String getPageUrl() {
        return null;
    }

    public boolean shouldOverrideUrlLoading(APWebView apWebView, String s) {
        return false;
    }

    public void onPageStarted(APWebView apWebView, String s, Bitmap bitmap) {
    }

    public void onPageFinished(APWebView apWebView, String s, long l) {
    }

    public void onLoadResource(APWebView apWebView, String s) {
    }

    private WebResourceResponse a(String url) {
        WebResourceResponse response;
        if (url.endsWith("workerjs.js")) {
            response = a("application/javascript", H5ResourceManager.getRaw(R.raw.workerjs));
        } else if (url.endsWith(".com/worker")) {
            Log.e(this.a, "shouldInterceptRequest worker.html");
            response = a("text/html", H5ResourceManager.getRaw(R.raw.worker));
        } else {
            response = this.b.getWorkerControllerProvider().shouldInterceptRequest4Worker(url);
        }
        if (VERSION.SDK_INT >= 21) {
            if (response != null) {
                Map rspHeader = new HashMap();
                rspHeader.put("Access-Control-Allow-Origin", "*");
                rspHeader.put("Cache-Control", "no-cache");
                response.setResponseHeaders(rspHeader);
            } else {
                Log.e(this.a, "shouldInterceptRequest response is null! " + url);
            }
        }
        return response;
    }

    public WebResourceResponse shouldInterceptRequest(APWebView apWebView, String url) {
        H5Log.d(this.a, "shouldInterceptRequest url: " + url);
        return a(url);
    }

    public WebResourceResponse shouldInterceptRequest(APWebView apWebView, APWebResourceRequest apWebResourceRequest) {
        H5Log.d(this.a, "shouldInterceptRequest request: " + apWebResourceRequest.getUrl());
        return a(apWebResourceRequest.getUrl().toString());
    }

    private WebResourceResponse a(String mimeType, String content) {
        if (content == null) {
            content = "";
        }
        byte[] data = null;
        try {
            data = content.getBytes("utf-8");
        } catch (Exception e) {
            H5Log.e(this.a, "failed to get byte array", e);
        }
        return new WebResourceResponse(mimeType, "UTF-8", new ByteArrayInputStream(data));
    }

    public void onTooManyRedirects(APWebView apWebView, Message message, Message message1) {
    }

    public void onReceivedError(APWebView apWebView, int i, String s, String s1) {
    }

    public void onReceivedHttpError(APWebView apWebView, int i, String s) {
    }

    public boolean shouldInterceptResponse(APWebView apWebView, HashMap<String, String> hashMap) {
        H5Log.d(this.a, "shouldInterceptResponse url: " + hashMap);
        return false;
    }

    public void onWebViewEvent(APWebView apWebView, int i, Object o) {
    }

    public void onFirstVisuallyRender(APWebView apWebView) {
    }

    public void onFormResubmission(APWebView apWebView, Message message, Message message1) {
    }

    public void doUpdateVisitedHistory(APWebView apWebView, String s, boolean b2) {
    }

    public void onReceivedSslError(APWebView apWebView, APSslErrorHandler apSslErrorHandler, SslError sslError) {
    }

    public void onReceivedHttpAuthRequest(APWebView apWebView, APHttpAuthHandler apHttpAuthHandler, String s, String s1) {
    }

    public boolean shouldOverrideKeyEvent(APWebView apWebView, KeyEvent keyEvent) {
        return false;
    }

    public void onUnhandledKeyEvent(APWebView apWebView, KeyEvent keyEvent) {
    }

    public void onScaleChanged(APWebView apWebView, float v, float v1) {
    }

    public void onReceivedLoginRequest(APWebView apWebView, String s, String s1, String s2) {
    }

    public String getJSBridge() {
        return null;
    }

    public void onResourceResponse(APWebView apWebView, HashMap<String, String> hashMap) {
    }

    public void onResourceFinishLoad(APWebView apWebView, String s, long l) {
    }

    public void onReceivedResponseHeader(Map<String, List<String>> map) {
    }

    public boolean shouldOverrideUrlLoadingForUC(APWebView apWebView, String s, int i) {
        return false;
    }

    public Map getRequestMap() {
        return null;
    }
}
