package com.alipay.mobile.android.verify.bridge.b;

import android.app.Activity;
import android.text.TextUtils;
import com.alipay.mobile.android.verify.bridge.BusProvider;
import com.alipay.mobile.android.verify.bridge.d;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.bridge.protocol.IPlugin;
import com.alipay.mobile.android.verify.logger.Logger;
import com.squareup.otto.Subscribe;

/* compiled from: PopWebViewPlugin */
public class c implements IPlugin {
    private final Activity a;

    public c(Activity activity) {
        this.a = activity;
    }

    @Subscribe
    public void handle(BridgeEvent bridgeEvent) {
        if (bridgeEvent == null || TextUtils.isEmpty(bridgeEvent.action)) {
            Logger.t("PopWebViewPlugin").w("null or empty action", new Object[0]);
            return;
        }
        if ("pushWindow".equalsIgnoreCase(bridgeEvent.action)) {
            Logger.t("PopWebViewPlugin").i("handle event: %s", bridgeEvent.id);
            try {
                Logger.t("PopWebViewPlugin").json(bridgeEvent.data != null ? bridgeEvent.data.toJSONString() : "");
                BridgeEvent cloneAsResponse = BridgeEvent.cloneAsResponse(bridgeEvent);
                cloneAsResponse.data = BridgeEvent.response();
                String string = bridgeEvent.data != null ? bridgeEvent.data.getString("url") : "";
                if (TextUtils.isEmpty(string)) {
                    cloneAsResponse.data.put((String) "success", (Object) "false");
                    cloneAsResponse.data.put((String) "errorMessage", (Object) "缺少必要的参数");
                    BusProvider.getInstance().post(cloneAsResponse);
                    return;
                }
                new d(this.a, string).show();
            } catch (Exception e) {
                Logger.t("PopWebViewPlugin").e(e, "handle push window error", new Object[0]);
            }
        }
    }
}
