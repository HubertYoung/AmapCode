package com.alipay.mobile.android.verify.sdk;

import android.app.Activity;
import android.text.TextUtils;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.android.verify.bridge.BridgeContainer;
import com.alipay.mobile.android.verify.bridge.BusProvider;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEventTypes;
import com.alipay.mobile.android.verify.bridge.protocol.IPlugin;
import com.alipay.mobile.android.verify.logger.Logger;
import com.alipay.mobile.android.verify.sdk.b.a;
import com.alipay.mobile.android.verify.sdk.b.c;
import com.alipay.mobile.android.verify.sdk.b.d;
import com.alipay.mobile.android.verify.sdk.interfaces.ICallback;
import com.alipay.sdk.util.j;
import com.squareup.otto.Subscribe;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/* compiled from: SDKContainer */
class b extends BridgeContainer implements IPlugin {
    private final String a = "SDKContainer";
    private ICallback b;
    private Map<String, String> c;
    private JSONObject d;

    public b(Activity activity, String str, ICallback iCallback) {
        super(activity, str);
        Logger.addLogAdapter(new com.alipay.mobile.android.verify.sdk.a.b(activity.getApplicationContext()));
        this.b = iCallback;
        a(activity);
    }

    private void a(Activity activity) {
        addPlugin(new c());
        addPlugin(new a());
        addPlugin(new d(activity));
        addPlugin(new com.alipay.mobile.android.verify.sdk.b.b(activity));
    }

    @Subscribe
    public void handle(BridgeEvent bridgeEvent) {
        super.handle(bridgeEvent);
        if (bridgeEvent == null || TextUtils.isEmpty(bridgeEvent.action)) {
            Logger.t("SDKContainer").w("null or empty event", new Object[0]);
        } else if ("saveVerifyResult".equalsIgnoreCase(bridgeEvent.action)) {
            Logger.t("SDKContainer").i("handle save z result", new Object[0]);
            this.c = new HashMap();
            if (bridgeEvent.data != null && bridgeEvent.data.size() > 0) {
                for (String next : bridgeEvent.data.keySet()) {
                    String string = bridgeEvent.data.getString(next);
                    if (!TextUtils.isEmpty(string)) {
                        try {
                            this.c.put(next, URLDecoder.decode(string, "UTF-8"));
                        } catch (UnsupportedEncodingException e) {
                            Logger.t("SDKContainer").e(e, "value decode error", new Object[0]);
                        }
                    }
                }
            }
            dismiss();
        } else if (BridgeEventTypes.BACK_PRESSED.equalsIgnoreCase(bridgeEvent.action)) {
            Logger.t("SDKContainer").i("handle back pressed", new Object[0]);
            BridgeEvent bridgeEvent2 = new BridgeEvent();
            bridgeEvent2.action = "saveVerifyResult";
            bridgeEvent2.data = BridgeEvent.response();
            bridgeEvent2.data.put((String) j.a, (Object) "6001");
            bridgeEvent2.data.put((String) "result.certifyId", (Object) (this.d == null || this.d.getString("certifyId") == null) ? "" : this.d.getString("certifyId"));
            BusProvider.getInstance().post(bridgeEvent2);
        } else {
            if ("getRequestInfo".equalsIgnoreCase(bridgeEvent.action)) {
                Logger.t("SDKContainer").i("handle get request info", new Object[0]);
                BridgeEvent cloneAsResponse = BridgeEvent.cloneAsResponse(bridgeEvent);
                cloneAsResponse.data = BridgeEvent.response();
                cloneAsResponse.data.put((String) "requestInfo", (Object) this.d);
                BusProvider.getInstance().post(cloneAsResponse);
            }
        }
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.b != null) {
            if (this.c == null || this.c.size() == 0) {
                Logger.t("SDKContainer").i("nothing back to invoker", new Object[0]);
                this.c = new HashMap();
                this.c.put("cancel", "true");
            }
            Logger.t("SDKContainer").i("execute callback", new Object[0]);
            com.alipay.mobile.android.verify.sdk.a.a.b("zmCallback");
            com.alipay.mobile.android.verify.sdk.a.a.a((String) null);
            this.b.onResponse(this.c);
            this.b = null;
        }
    }

    public void a(JSONObject jSONObject) {
        this.d = jSONObject;
    }
}
