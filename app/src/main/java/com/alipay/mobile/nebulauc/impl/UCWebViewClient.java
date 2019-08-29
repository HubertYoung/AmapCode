package com.alipay.mobile.nebulauc.impl;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.nebula.log.H5LogData;
import com.alipay.mobile.nebula.log.H5LogUtil;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5ResourceCORSUtil;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebula.webview.APWebViewClient;
import com.alipay.mobile.nebulauc.util.CommonUtil;
import com.alipay.mobile.nebulauc.util.H5ConfigUtil;
import com.alipay.mobile.security.bio.common.record.MetaRecord;
import com.uc.webview.export.HttpAuthHandler;
import com.uc.webview.export.SslErrorHandler;
import com.uc.webview.export.WebResourceRequest;
import com.uc.webview.export.WebResourceResponse;
import com.uc.webview.export.WebView;
import com.uc.webview.export.WebViewClient;
import com.uc.webview.export.extension.RenderProcessGoneDetail;
import com.uc.webview.export.extension.UCCore;
import java.util.HashMap;
import java.util.Map;

class UCWebViewClient extends WebViewClient {
    private static final String TAG = "H5UCWebViewClient";
    /* access modifiers changed from: private */
    public APWebView mAPView;
    private APWebViewClient mClient;
    private long mCurrentTrafficFlow;

    UCWebViewClient(APWebView apWebView, APWebViewClient apWebViewClient) {
        this.mAPView = apWebView;
        this.mClient = apWebViewClient;
    }

    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return this.mClient != null && this.mClient.shouldOverrideUrlLoading(this.mAPView, url);
    }

    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if (this.mClient != null) {
            this.mCurrentTrafficFlow = getTrafficFlow();
            if (this.mAPView != null && (this.mAPView instanceof UCWebView)) {
                ((UCWebView) this.mAPView).setMultiProcessMode();
                ((UCWebView) this.mAPView).clearPageStartUnCalled();
            }
            this.mClient.onPageStarted(this.mAPView, url, favicon);
        }
    }

    public void onPageFinished(WebView view, String url) {
        if (this.mClient != null) {
            this.mClient.onPageFinished(this.mAPView, url, getTrafficFlow() - this.mCurrentTrafficFlow);
        }
    }

    private long getTrafficFlow() {
        try {
            return ((Long) UCCore.getTraffic().first).longValue() + ((Long) UCCore.getTraffic().second).longValue();
        } catch (Throwable t) {
            H5Log.e(TAG, "exception detail ", t);
            return 0;
        }
    }

    public void onLoadResource(WebView view, String url) {
        if (this.mClient != null) {
            this.mClient.onLoadResource(this.mAPView, url);
        }
    }

    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if (this.mClient == null || view == null) {
            return null;
        }
        android.webkit.WebResourceResponse rsp = this.mClient.shouldInterceptRequest(this.mAPView, url);
        if (rsp == null) {
            return null;
        }
        WebResourceResponse response = new WebResourceResponse(rsp.getMimeType(), rsp.getEncoding(), rsp.getData());
        Map rspHeader = new HashMap();
        rspHeader.put("Cache-Control", "no-cache");
        String pageUrl = this.mClient.getPageUrl();
        if (pageUrl != null && H5ResourceCORSUtil.needAddHeader(url)) {
            String corsUrl = H5ResourceCORSUtil.getCORSUrl(pageUrl);
            if (corsUrl != null) {
                rspHeader.put("Access-Control-Allow-Origin", corsUrl);
            }
        }
        String headUrl = H5ResourceCORSUtil.getCORSUrl(pageUrl);
        if (!rspHeader.containsKey("Access-Control-Allow-Origin") && H5Utils.enableCheckCrossOrigin() && H5Utils.containNebulaAddcors(url) && !TextUtils.isEmpty(pageUrl) && !TextUtils.isEmpty(headUrl)) {
            rspHeader.put("Access-Control-Allow-Origin", headUrl);
        }
        if (!rspHeader.containsKey("Access-Control-Allow-Origin") && H5Utils.addChooseImageCrossOrigin(url) && !TextUtils.isEmpty(pageUrl) && !TextUtils.isEmpty(headUrl)) {
            rspHeader.put("Access-Control-Allow-Origin", headUrl);
        }
        response.setResponseHeaders(rspHeader);
        return response;
    }

    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if (this.mClient != null) {
            this.mClient.onReceivedError(this.mAPView, errorCode, description, failingUrl);
        }
    }

    public void onReceivedHttpError(WebView webView, WebResourceRequest webResourceRequest, WebResourceResponse webResourceResponse) {
        if (this.mClient != null) {
            this.mClient.onReceivedHttpError(this.mAPView, webResourceResponse != null ? webResourceResponse.getStatusCode() : 0, webResourceRequest != null ? webResourceRequest.getUrl().toString() : "");
        }
    }

    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        if (this.mClient != null) {
            this.mClient.onFormResubmission(this.mAPView, dontResend, resend);
        }
    }

    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if (this.mClient != null) {
            this.mClient.doUpdateVisitedHistory(this.mAPView, url, isReload);
        }
    }

    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if (this.mClient != null) {
            this.mClient.onReceivedSslError(this.mAPView, new UCSslErrorHandler(handler), new UCSslError(0, null, error));
        }
    }

    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        if (this.mClient != null) {
            this.mClient.onReceivedHttpAuthRequest(this.mAPView, new UCHttpAuthHandler(handler), host, realm);
        }
    }

    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        return this.mClient != null && this.mClient.shouldOverrideKeyEvent(this.mAPView, event);
    }

    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if (this.mAPView != null) {
            this.mClient.onUnhandledKeyEvent(this.mAPView, event);
        }
    }

    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        if (this.mClient != null) {
            this.mClient.onScaleChanged(this.mAPView, oldScale, newScale);
        }
    }

    public boolean onRenderProcessGone(WebView webView, RenderProcessGoneDetail var2) {
        boolean canRestore = shouldRestoreRenderProcess();
        H5LogData networkData = H5LogData.seedId("H5_UC_RENDER_PROCESS_RESTORE");
        if (this.mClient != null) {
            networkData.param1().add("url", this.mClient.getPageUrl());
        }
        networkData.param3().add("canRestore", Boolean.valueOf(canRestore));
        networkData.param3().add("phoneInfo", Build.MANUFACTURER + MetaRecord.LOG_SEPARATOR + Build.MODEL + MetaRecord.LOG_SEPARATOR + VERSION.SDK_INT);
        networkData.param3().add("multiProcessMode", Integer.valueOf(UcServiceSetup.sProcessMode));
        networkData.param3().add("isFg", Boolean.valueOf(CommonUtil.isForeground()));
        if (this.mAPView != null && (this.mAPView instanceof UCWebView)) {
            networkData.param3().add("isStaticWebView", Boolean.valueOf(((UCWebView) this.mAPView).isMultiProcessPreCreate()));
        }
        H5LogUtil.logNebulaTech(networkData);
        if (!canRestore || this.mAPView == null) {
            return false;
        }
        H5Utils.runOnMain(new Runnable() {
            public void run() {
                try {
                    if (UCWebViewClient.this.mAPView != null) {
                        UCWebViewClient.this.mAPView.reload();
                    }
                } catch (Throwable thr) {
                    H5Log.w(UCWebViewClient.TAG, "uc webview reload failed!", thr);
                }
            }
        }, 100);
        return true;
    }

    private boolean shouldRestoreRenderProcess() {
        JSONObject jsonObj = H5ConfigUtil.getConfigJSONObject("h5_ucDisableRenderProcessReload");
        if (H5Utils.getBoolean(jsonObj, (String) "all", false)) {
            H5Log.d(TAG, "disableRenderProcessReload all");
            return false;
        }
        JSONArray jsonArray = H5Utils.getJSONArray(jsonObj, "deviceList", null);
        if (jsonArray != null && !jsonArray.isEmpty()) {
            String phoneInfo = Build.MANUFACTURER + MetaRecord.LOG_SEPARATOR + Build.MODEL + MetaRecord.LOG_SEPARATOR + VERSION.SDK_INT;
            for (int i = 0; i < jsonArray.size(); i++) {
                if (TextUtils.equals(phoneInfo, jsonArray.getString(i))) {
                    H5Log.d(TAG, "disableRenderProcessReload device " + phoneInfo);
                    return false;
                }
            }
        }
        JSONArray jsonArray2 = H5Utils.getJSONArray(jsonObj, "sdkIntList", null);
        if (jsonArray2 != null && !jsonArray2.isEmpty()) {
            for (int i2 = 0; i2 < jsonArray2.size(); i2++) {
                if (VERSION.SDK_INT == jsonArray2.getIntValue(i2)) {
                    H5Log.d(TAG, "disableRenderProcessReload sdkint" + VERSION.SDK_INT);
                    return false;
                }
            }
        }
        return true;
    }
}
