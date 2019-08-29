package com.alipay.mobile.android.verify.bridge.b;

import android.text.TextUtils;
import android.webkit.WebView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEventTypes;
import com.alipay.mobile.android.verify.bridge.protocol.IPlugin;
import com.alipay.mobile.android.verify.logger.Logger;
import com.squareup.otto.Subscribe;

/* compiled from: JSCallbackPlugin */
public class b implements IPlugin {
    private final WebView a;

    public b(WebView webView) {
        this.a = webView;
    }

    @Subscribe
    public void handle(BridgeEvent bridgeEvent) {
        if (bridgeEvent == null || TextUtils.isEmpty(bridgeEvent.action)) {
            Logger.t("JSCallbackPlugin").w("null or empty action", new Object[0]);
            return;
        }
        if (BridgeEventTypes.JS_CALLBACK.equalsIgnoreCase(bridgeEvent.action)) {
            Logger.t("JSCallbackPlugin").i("handle event: %s", bridgeEvent.id);
            try {
                Logger.t("JSCallbackPlugin").json(bridgeEvent.data != null ? bridgeEvent.data.toJSONString() : "");
                JSONObject jSONObject = new JSONObject();
                jSONObject.put((String) "eventId", (Object) bridgeEvent.id);
                jSONObject.put((String) "data", (Object) bridgeEvent.data);
                String format = String.format("javascript:(function(){if(typeof APVJSBridge==='object'){%s}}());", new Object[]{String.format("APVJSBridge.callback(%s)", new Object[]{JSON.toJSONString(jSONObject)})});
                Logger.t("JSCallbackPlugin").i("js callback execute: %s", format);
                this.a.loadUrl(format);
            } catch (Exception e) {
                Logger.t("JSCallbackPlugin").e(e, "handle js callback error", new Object[0]);
            }
        }
    }
}
