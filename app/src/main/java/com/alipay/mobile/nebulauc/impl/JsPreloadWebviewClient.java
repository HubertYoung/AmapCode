package com.alipay.mobile.nebulauc.impl;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebResourceResponse;
import com.alipay.mobile.h5container.service.H5EventHandlerService;
import com.alipay.mobile.nebula.util.H5FileUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ServiceUtils;
import com.alipay.mobile.nebula.util.H5UrlHelper;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APHttpAuthHandler;
import com.alipay.mobile.nebula.webview.APSslErrorHandler;
import com.alipay.mobile.nebula.webview.APWebResourceRequest;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebula.webview.APWebViewClient;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsPreloadWebviewClient implements APWebViewClient {
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

    public WebResourceResponse shouldInterceptRequest(APWebView apWebView, APWebResourceRequest apWebResourceRequest) {
        return null;
    }

    private WebResourceResponse buildContent(Uri uri, String url, InputStream inputStream) {
        String mimeType = null;
        if (!TextUtils.isEmpty(url)) {
            if (uri == null) {
                uri = H5UrlHelper.parseUrl(url);
            }
            if (uri != null) {
                mimeType = H5FileUtil.getMimeType(uri.getPath());
            }
        }
        return new WebResourceResponse(mimeType, "UTF-8", inputStream);
    }

    public WebResourceResponse shouldInterceptRequest(APWebView apWebView, String s) {
        if ("https://appx/af-appx.min.js".equals(s)) {
            H5Utils.runOnMain(new Runnable() {
                public void run() {
                    try {
                        H5EventHandlerService h5EventHandlerService = (H5EventHandlerService) H5Utils.findServiceByInterface(H5EventHandlerService.class.getName());
                        if (h5EventHandlerService != null) {
                            h5EventHandlerService.onLiteProcessPreloadComplete();
                        }
                        H5Log.d("JsPreloadWebviewClient", "appx preload completed");
                    } catch (Throwable thr) {
                        H5Log.w("JsPreloadWebviewClient", "call LiteProcessClientManager.onLiteProcessPreloadComplete() error!", thr);
                    }
                }
            });
        }
        byte[] data = getResource(s);
        if (data != null) {
            return buildContent(null, s, new ByteArrayInputStream(data));
        }
        return null;
    }

    public void onTooManyRedirects(APWebView apWebView, Message message, Message message1) {
    }

    public void onReceivedError(APWebView apWebView, int i, String s, String s1) {
    }

    public void onReceivedHttpError(APWebView apWebView, int i, String s) {
    }

    public boolean shouldInterceptResponse(APWebView apWebView, HashMap<String, String> hashMap) {
        return false;
    }

    public void onWebViewEvent(APWebView apWebView, int i, Object o) {
    }

    public void onFirstVisuallyRender(APWebView apWebView) {
    }

    public void onFormResubmission(APWebView apWebView, Message message, Message message1) {
    }

    public void doUpdateVisitedHistory(APWebView apWebView, String s, boolean b) {
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

    static byte[] getResource(String url) {
        try {
            return H5ServiceUtils.getH5Service().getH5GlobalDegradePkg(url);
        } catch (Throwable thr) {
            H5Log.e("JsPreloadWebviewClient", "getResource error!", thr);
            return null;
        }
    }
}
