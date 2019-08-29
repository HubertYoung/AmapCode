package com.alipay.mobile.android.verify.sdk.b;

import android.text.TextUtils;
import com.alipay.mobile.android.verify.bridge.BusProvider;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.bridge.protocol.IPlugin;
import com.alipay.mobile.android.verify.logger.Logger;
import com.squareup.otto.Subscribe;

/* compiled from: BehaviorLogPlugin */
public class a implements IPlugin {
    @Subscribe
    public void handle(BridgeEvent bridgeEvent) {
        if (bridgeEvent == null || TextUtils.isEmpty(bridgeEvent.action) || TextUtils.isEmpty(bridgeEvent.id)) {
            Logger.t("BehaviorLogPlugin").i("null or empty action", new Object[0]);
            return;
        }
        BridgeEvent cloneAsResponse = BridgeEvent.cloneAsResponse(bridgeEvent);
        cloneAsResponse.data = BridgeEvent.response();
        if ("behaviorLog".equalsIgnoreCase(bridgeEvent.action)) {
            Logger.t("BehaviorLogPlugin").i("handle behavior log event", new Object[0]);
            if (bridgeEvent.data == null || TextUtils.isEmpty(bridgeEvent.data.getString("seed"))) {
                cloneAsResponse.data.put((String) "success", (Object) Boolean.FALSE);
                cloneAsResponse.data.put((String) "errorMessage", (Object) "缺少必要参数");
                BusProvider.getInstance().post(cloneAsResponse);
                return;
            }
            com.alipay.mobile.android.verify.sdk.a.a.a(bridgeEvent.data);
            BusProvider.getInstance().post(cloneAsResponse);
        }
    }
}
