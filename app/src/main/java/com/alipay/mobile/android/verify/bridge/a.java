package com.alipay.mobile.android.verify.bridge;

import android.text.TextUtils;
import android.webkit.ConsoleMessage;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEventTypes;
import com.alipay.mobile.android.verify.logger.Logger;

/* compiled from: BridgeChromeClient */
class a extends WebChromeClient {
    a() {
    }

    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        if (consoleMessage == null) {
            return false;
        }
        String message = consoleMessage.message();
        if (TextUtils.isEmpty(message)) {
            return false;
        }
        String str = null;
        if (message.startsWith("bridge.log.message: ")) {
            str = message.replaceFirst("bridge.log.message: ", "");
        }
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        JSONObject a = h.a(str);
        if (a == null || a.isEmpty()) {
            return false;
        }
        String string = a.getString("eventId");
        String string2 = a.getString("action");
        JSONObject jSONObject = a.getJSONObject("data");
        Logger.t("BridgeChromeClient").i("received bridge event %s action %s", string, string2);
        Logger.t("BridgeChromeClient").json(jSONObject != null ? jSONObject.toJSONString() : "");
        if (TextUtils.isEmpty(string)) {
            return false;
        }
        BridgeEvent bridgeEvent = new BridgeEvent();
        bridgeEvent.id = string;
        bridgeEvent.action = string2;
        bridgeEvent.data = jSONObject;
        BusProvider.getInstance().post(bridgeEvent);
        return true;
    }

    public void onReceivedTitle(WebView webView, String str) {
        super.onReceivedTitle(webView, str);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put((String) "url", (Object) webView.getUrl());
        jSONObject.put((String) "title", (Object) str);
        BridgeEvent bridgeEvent = new BridgeEvent();
        bridgeEvent.action = BridgeEventTypes.RECEIVED_TITLE;
        bridgeEvent.data = jSONObject;
        BusProvider.getInstance().post(bridgeEvent);
    }

    public boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        BusProvider.getInstance().post(jsResult);
        return super.onJsAlert(webView, str, str2, jsResult);
    }

    public boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
        BusProvider.getInstance().post(jsResult);
        return super.onJsConfirm(webView, str, str2, jsResult);
    }

    public boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        BusProvider.getInstance().post(jsPromptResult);
        return super.onJsPrompt(webView, str, str2, str3, jsPromptResult);
    }
}
