package com.alipay.mobile.worker.multiworker;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.webkit.WebResourceResponse;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5Session;
import com.alipay.mobile.h5container.service.H5Service;
import com.alipay.mobile.nebula.appcenter.res.H5ResourceManager;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;
import com.alipay.mobile.nebula.webview.APHttpAuthHandler;
import com.alipay.mobile.nebula.webview.APSslErrorHandler;
import com.alipay.mobile.nebula.webview.APWebResourceRequest;
import com.alipay.mobile.nebula.webview.APWebView;
import com.alipay.mobile.nebula.webview.APWebViewClient;
import com.alipay.mobile.tinyapp.worker.R;
import com.alipay.mobile.tinyappcommon.h5plugin.H5VConsolePlugin;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class MultiJsWorkerWebViewClient implements APWebViewClient {
    private MultiJsWorker a;

    public MultiJsWorkerWebViewClient(MultiJsWorker worker) {
        this.a = worker;
    }

    public String getPageUrl() {
        return null;
    }

    public boolean shouldOverrideUrlLoading(APWebView view, String url) {
        return false;
    }

    public void onPageStarted(APWebView view, String url, Bitmap favicon) {
    }

    public void onPageFinished(APWebView view, String url, long pageSize) {
    }

    public void onLoadResource(APWebView view, String url) {
    }

    private static WebResourceResponse a(String mimeType, String content) {
        if (content == null) {
            content = "";
        }
        byte[] data = null;
        try {
            data = content.getBytes("utf-8");
        } catch (Exception e) {
            H5Log.e("MultiJsWorkerWebViewClient", "failed to get byte array", e);
        }
        return new WebResourceResponse(mimeType, "UTF-8", new ByteArrayInputStream(data));
    }

    private WebResourceResponse a(String url) {
        WebResourceResponse response = null;
        if ((this.a != null && url.contains(this.a.getWorkerUrl())) || url.endsWith("create_worker.js")) {
            H5Session session = a();
            if (session != null) {
                response = session.getWebProvider().getContent(this.a.getWorkerUrl());
            }
        } else if (url.endsWith("com/create_worker")) {
            response = a("text/html", H5ResourceManager.getRaw(R.raw.create_worker));
        } else {
            H5Session session2 = a();
            if (session2 != null) {
                response = session2.getWebProvider().getContent(url);
            }
        }
        if (VERSION.SDK_INT >= 21) {
            if (response != null) {
                Map rspHeader = new HashMap();
                rspHeader.put("Access-Control-Allow-Origin", "*");
                rspHeader.put("Cache-Control", "no-cache");
                response.setResponseHeaders(rspHeader);
            } else {
                H5Log.d("MultiJsWorkerWebViewClient", "shouldInterceptRequest response is null! " + url);
            }
        }
        return response;
    }

    public WebResourceResponse shouldInterceptRequest(APWebView view, APWebResourceRequest wr) {
        return a(wr.getUrl().toString());
    }

    public WebResourceResponse shouldInterceptRequest(APWebView view, String url) {
        return a(url);
    }

    public void onTooManyRedirects(APWebView view, Message cancelMsg, Message continueMsg) {
    }

    public void onReceivedError(APWebView view, int errorCode, String description, String failingUrl) {
    }

    public void onReceivedHttpError(APWebView view, int statusCode, String requestUrl) {
    }

    public boolean shouldInterceptResponse(APWebView view, HashMap<String, String> hashMap) {
        return false;
    }

    public void onWebViewEvent(APWebView webview, int type, Object object) {
    }

    public void onFirstVisuallyRender(APWebView webview) {
    }

    public void onFormResubmission(APWebView view, Message dontResend, Message resend) {
    }

    public void doUpdateVisitedHistory(APWebView view, String url, boolean isReload) {
    }

    public void onReceivedSslError(APWebView view, APSslErrorHandler handler, SslError error) {
    }

    public void onReceivedHttpAuthRequest(APWebView view, APHttpAuthHandler handler, String host, String realm) {
    }

    public boolean shouldOverrideKeyEvent(APWebView view, KeyEvent event) {
        return false;
    }

    public void onUnhandledKeyEvent(APWebView view, KeyEvent event) {
    }

    public void onScaleChanged(APWebView view, float oldScale, float newScale) {
    }

    public void onReceivedLoginRequest(APWebView view, String realm, String account, String args) {
    }

    public String getJSBridge() {
        return null;
    }

    public void onResourceResponse(APWebView view, HashMap<String, String> hashMap) {
    }

    public void onResourceFinishLoad(APWebView view, String url, long size) {
    }

    public void onReceivedResponseHeader(Map<String, List<String>> responseHeaders) {
    }

    public boolean shouldOverrideUrlLoadingForUC(APWebView view, String url, int nonStandardType) {
        return false;
    }

    public Map getRequestMap() {
        return null;
    }

    private static H5Session a() {
        H5Service h5Service = (H5Service) H5Utils.findServiceByInterface(H5Service.class.getName());
        if (h5Service == null) {
            return null;
        }
        try {
            Stack sessions = h5Service.getSessions();
            if (sessions == null) {
                return null;
            }
            synchronized (sessions) {
                for (int index = sessions.size() - 1; index >= 0; index--) {
                    H5Session session = (H5Session) sessions.get(index);
                    if (session != null) {
                        String id = session.getId();
                        H5Log.d("MultiJsWorkerWebViewClient", "sessionId:" + id);
                        if (!b(id)) {
                            H5Page h5Page = session.getTopPage();
                            if (h5Page == null) {
                                continue;
                            } else {
                                if (!(!TextUtils.isEmpty(H5Utils.getString(h5Page.getParams(), (String) "MINI-PROGRAM-WEB-VIEW-TAG")))) {
                                    return session;
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        } catch (Throwable throwable) {
            H5Log.e((String) "MultiJsWorkerWebViewClient", throwable);
        }
        return null;
    }

    private static boolean b(String id) {
        return !TextUtils.isEmpty(id) && id.contains(H5VConsolePlugin.DEBUG_PANEL_PACKAGE_APPID);
    }
}
