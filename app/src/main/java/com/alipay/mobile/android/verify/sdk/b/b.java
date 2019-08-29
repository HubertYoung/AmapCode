package com.alipay.mobile.android.verify.sdk.b;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.alipay.mobile.android.verify.bridge.BusProvider;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.bridge.protocol.IPlugin;
import com.alipay.mobile.android.verify.logger.Logger;
import com.autonavi.minimap.ajx3.loader.AjxHttpLoader;
import com.squareup.otto.Subscribe;

/* compiled from: BrowserPlugin */
public class b implements IPlugin {
    private final Activity a;

    public b(Activity activity) {
        this.a = activity;
    }

    @Subscribe
    public void handle(BridgeEvent bridgeEvent) {
        if (bridgeEvent == null || TextUtils.isEmpty(bridgeEvent.action)) {
            Logger.t("BrowserPlugin").w("null or empty action", new Object[0]);
            return;
        }
        if ("openInBrowser".equalsIgnoreCase(bridgeEvent.action)) {
            BridgeEvent cloneAsResponse = BridgeEvent.cloneAsResponse(bridgeEvent);
            cloneAsResponse.data = BridgeEvent.response();
            String string = bridgeEvent.data != null ? bridgeEvent.data.getString("url") : "";
            if (TextUtils.isEmpty(string)) {
                cloneAsResponse.data.put((String) "success", (Object) "false");
                cloneAsResponse.data.put((String) "errorMessage", (Object) "缺少必要的参数");
                BusProvider.getInstance().post(cloneAsResponse);
            } else if (string.startsWith(AjxHttpLoader.DOMAIN_HTTP) || string.startsWith(AjxHttpLoader.DOMAIN_HTTPS)) {
                Intent intent = new Intent("android.intent.action.VIEW");
                try {
                    intent.setData(Uri.parse(string));
                    this.a.startActivity(intent);
                    BusProvider.getInstance().post(cloneAsResponse);
                } catch (Exception e) {
                    Logger.t("BrowserPlugin").e(e, "handle browser event error", new Object[0]);
                    cloneAsResponse.data.put((String) "success", (Object) "false");
                    cloneAsResponse.data.put((String) "errorMessage", (Object) "执行异常");
                    BusProvider.getInstance().post(cloneAsResponse);
                }
            } else {
                cloneAsResponse.data.put((String) "success", (Object) "false");
                cloneAsResponse.data.put((String) "errorMessage", (Object) "非法的地址");
                BusProvider.getInstance().post(cloneAsResponse);
            }
        }
    }
}
