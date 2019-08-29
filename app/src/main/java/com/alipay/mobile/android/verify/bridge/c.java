package com.alipay.mobile.android.verify.bridge;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEventTypes;

/* compiled from: BridgeWebViewClient */
class c extends WebViewClient {
    c() {
    }

    public void onPageFinished(WebView webView, String str) {
        super.onPageFinished(webView, str);
        BridgeEvent bridgeEvent = new BridgeEvent();
        bridgeEvent.action = BridgeEventTypes.PAGE_LOADED;
        BusProvider.getInstance().post(bridgeEvent);
        BridgeEvent bridgeEvent2 = new BridgeEvent();
        bridgeEvent2.action = BridgeEventTypes.RECEIVED_TITLE;
        bridgeEvent2.data = new JSONObject();
        bridgeEvent2.data.put((String) "title", (Object) webView.getTitle());
        BusProvider.getInstance().post(bridgeEvent2);
    }

    public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
        super.onPageStarted(webView, str, bitmap);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "url", (Object) webView.getUrl());
        BridgeEvent bridgeEvent = new BridgeEvent();
        bridgeEvent.action = BridgeEventTypes.PAGE_START;
        bridgeEvent.data = jSONObject;
        BusProvider.getInstance().post(bridgeEvent);
    }
}
