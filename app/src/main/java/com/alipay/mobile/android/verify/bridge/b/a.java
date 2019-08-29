package com.alipay.mobile.android.verify.bridge.b;

import android.text.TextUtils;
import android.webkit.WebView;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEventTypes;
import com.alipay.mobile.android.verify.bridge.protocol.IPlugin;
import com.alipay.mobile.android.verify.logger.Logger;
import com.squareup.otto.Subscribe;

/* compiled from: InvokePlugin */
public class a implements IPlugin {
    private final WebView a;

    public a(WebView webView) {
        this.a = webView;
    }

    @Subscribe
    public void handle(BridgeEvent bridgeEvent) {
        if (bridgeEvent == null || TextUtils.isEmpty(bridgeEvent.action)) {
            Logger.t("InvokePlugin").w("null or empty action", new Object[0]);
            return;
        }
        if (BridgeEventTypes.JS_INVOKE.equalsIgnoreCase(bridgeEvent.action)) {
            try {
                String format = String.format("javascript:(function(){if(typeof APVJSBridge==='object'){%s}}());", new Object[]{String.format("APVJSBridge.invoke('%s')", new Object[]{bridgeEvent.data != null ? bridgeEvent.data.getString("jsAction") : ""})});
                Logger.t("InvokePlugin").i("invoke %s", format);
                this.a.loadUrl(format);
            } catch (Exception unused) {
                Logger.t("InvokePlugin").e("invoke got error", new Object[0]);
            }
        }
    }
}
