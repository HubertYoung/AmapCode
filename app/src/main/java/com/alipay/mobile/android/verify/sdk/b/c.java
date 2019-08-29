package com.alipay.mobile.android.verify.sdk.b;

import android.text.TextUtils;
import com.alipay.mobile.android.verify.bridge.BusProvider;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.bridge.protocol.IPlugin;
import com.alipay.mobile.android.verify.logger.AndroidLogAdapter;
import com.alipay.mobile.android.verify.logger.Logger;
import com.alipay.mobile.android.verify.logger.PrettyFormatStrategy;
import com.squareup.otto.Subscribe;

/* compiled from: LoggerPlugin */
public class c implements IPlugin {
    @Subscribe
    public void handle(BridgeEvent bridgeEvent) {
        if (bridgeEvent == null || TextUtils.isEmpty(bridgeEvent.action) || TextUtils.isEmpty(bridgeEvent.id)) {
            Logger.t("LoggerPlugin").i("null or empty action", new Object[0]);
            return;
        }
        if ("enableLogger".equalsIgnoreCase(bridgeEvent.action)) {
            Logger.t("LoggerPlugin").i("handle enable logger event", new Object[0]);
            Logger.clearLogAdapters();
            Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder().tag("ZMSDK").build()));
            BridgeEvent cloneAsResponse = BridgeEvent.cloneAsResponse(bridgeEvent);
            cloneAsResponse.data = BridgeEvent.response();
            BusProvider.getInstance().post(cloneAsResponse);
        }
    }
}
