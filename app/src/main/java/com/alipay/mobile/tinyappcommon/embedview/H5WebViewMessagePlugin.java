package com.alipay.mobile.tinyappcommon.embedview;

import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.h5container.api.H5BridgeContext;
import com.alipay.mobile.h5container.api.H5Event;
import com.alipay.mobile.h5container.api.H5EventFilter;
import com.alipay.mobile.h5container.api.H5Page;
import com.alipay.mobile.h5container.api.H5SimplePlugin;
import com.alipay.mobile.nebula.util.H5Log;
import com.alipay.mobile.nebula.util.H5Utils;

public class H5WebViewMessagePlugin extends H5SimplePlugin {
    public static final String GET_EMBED_WEBVIEW_ENV = "getEmbedWebViewEnv";
    public static final String POST_WEBVIEW_MESSAGE = "postWebViewMessage";
    protected static final String TAG = "H5WebViewMessagePlugin";
    private static final String TYPE_MESSAGE = "message";
    private static final String WEBVIEW_ON_FROM_MESSAGE = "nbcomponent.webview.onFromMessage";
    private H5Page mH5Page;

    public void onPrepare(H5EventFilter filter) {
        super.onPrepare(filter);
        filter.addAction(POST_WEBVIEW_MESSAGE);
        filter.addAction(GET_EMBED_WEBVIEW_ENV);
    }

    public void onRelease() {
        this.mH5Page = null;
    }

    public boolean handleEvent(H5Event event, H5BridgeContext context) {
        this.mH5Page = event.getH5page();
        if (POST_WEBVIEW_MESSAGE.equals(event.getAction())) {
            postWebViewMessage(event);
        } else if (GET_EMBED_WEBVIEW_ENV.equals(event.getAction())) {
            getEmbedWebViewEnv(context);
        }
        return true;
    }

    private void postWebViewMessage(H5Event event) {
        if (this.mH5Page == null) {
            H5Log.d(TAG, "postWebViewMessage...h5Page is null");
            return;
        }
        try {
            String type = H5Utils.getString(event.getParam(), (String) "type");
            H5Log.d(TAG, "postWebViewMessage...type=" + type);
            if ("message".equals(type)) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put((String) "detail", (Object) H5Utils.getJSONObject(event.getParam(), "detail", null));
                jsonObject.put((String) "type", (Object) "message");
                jsonObject.put((String) "element", (Object) H5Utils.getString(this.mH5Page.getParams(), (String) "element"));
                getMainDOMPage().getBridge().sendDataWarpToWeb(WEBVIEW_ON_FROM_MESSAGE, jsonObject, null);
            } else if (shouldInterceptPostWebViewAction(H5Utils.getString(this.mH5Page.getParams(), (String) "MINI-PROGRAM-WEB-VIEW-TAG"), type)) {
                H5Log.d(TAG, "postWebViewMessage...type:" + type + " will-be-intercepted");
            } else {
                JSONObject forwardObject = new JSONObject();
                int callback = H5Utils.getInt(event.getParam(), (String) "callback");
                JSONObject param = H5Utils.getJSONObject(event.getParam(), "param", null);
                String viewId = H5Utils.getString(this.mH5Page.getParams(), (String) "viewId");
                String element = H5Utils.getString(this.mH5Page.getParams(), (String) "element");
                forwardObject.put((String) "viewId", (Object) viewId);
                forwardObject.put((String) "element", (Object) element);
                forwardObject.put((String) "type", (Object) type);
                forwardObject.put((String) "callback", (Object) Integer.valueOf(callback));
                forwardObject.put((String) "param", (Object) param);
                getMainDOMPage().getBridge().sendDataWarpToWeb(WEBVIEW_ON_FROM_MESSAGE, forwardObject, null);
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, "postWebViewMessage...e=" + e);
        }
    }

    private H5Page getMainDOMPage() {
        H5Page mainDOMPage = this.mH5Page;
        Object obj = this.mH5Page.getExtra(H5EmbedWebView.WEB_VIEW_PAGE_TAG);
        if (obj instanceof H5Page) {
            return (H5Page) obj;
        }
        return mainDOMPage;
    }

    /* access modifiers changed from: 0000 */
    public String getWorkerId() {
        Object o = this.mH5Page.getExtra(H5EmbedWebView.WEB_VIEW_WORK_ID);
        if (o instanceof String) {
            return (String) o;
        }
        return "";
    }

    private void getEmbedWebViewEnv(H5BridgeContext context) {
        boolean z;
        try {
            if (this.mH5Page != null) {
                if (!TextUtils.isEmpty(H5Utils.getString(this.mH5Page.getParams(), (String) "MINI-PROGRAM-WEB-VIEW-TAG"))) {
                    z = true;
                } else {
                    z = false;
                }
                sendEnvResult(z, context);
            }
        } catch (Throwable e) {
            H5Log.e((String) TAG, "getEnv...e=" + e);
        }
        sendEnvResult(false, context);
    }

    private void sendEnvResult(boolean isMiniProgramEnv, H5BridgeContext context) {
        if (context != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put((String) "miniprogram", (Object) Boolean.valueOf(isMiniProgramEnv));
            context.sendBridgeResult(jsonObject);
        }
    }

    /* access modifiers changed from: protected */
    public boolean shouldInterceptPostWebViewAction(String appId, String action) {
        return false;
    }
}
