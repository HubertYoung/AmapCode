package com.alipay.mobile.android.verify.bridge.b;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.alipay.mobile.android.verify.bridge.BusProvider;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEventTypes;
import com.alipay.mobile.android.verify.bridge.protocol.IPlugin;
import com.alipay.mobile.android.verify.logger.Logger;
import com.squareup.otto.Subscribe;

/* compiled from: TitleBarPlugin */
public class g implements IPlugin {
    private final TextView a;
    private final TextView b;
    private final View c;

    public g(TextView textView, TextView textView2, View view) {
        this.a = textView;
        this.b = textView2;
        this.c = view;
    }

    @Subscribe
    public void handle(BridgeEvent bridgeEvent) {
        if (bridgeEvent == null || TextUtils.isEmpty(bridgeEvent.action)) {
            Logger.t("TitleBarPlugin").w("null or empty action", new Object[0]);
            return;
        }
        BridgeEvent cloneAsResponse = BridgeEvent.cloneAsResponse(bridgeEvent);
        cloneAsResponse.data = BridgeEvent.response();
        if (BridgeEventTypes.RECEIVED_TITLE.equalsIgnoreCase(bridgeEvent.action)) {
            if (bridgeEvent.data != null && !TextUtils.isEmpty(bridgeEvent.data.getString("title"))) {
                this.b.setText(bridgeEvent.data.getString("title"));
            }
        } else if ("hideBackButton".equalsIgnoreCase(bridgeEvent.action)) {
            Logger.t("TitleBarPlugin").i("handle hide back button event", new Object[0]);
            this.a.setVisibility(8);
            this.c.setVisibility(8);
            BusProvider.getInstance().post(cloneAsResponse);
        } else if ("showBackButton".equalsIgnoreCase(bridgeEvent.action)) {
            Logger.t("TitleBarPlugin").i("handle show back button event", new Object[0]);
            this.a.setVisibility(0);
            this.c.setVisibility(0);
            BusProvider.getInstance().post(cloneAsResponse);
        }
    }
}
