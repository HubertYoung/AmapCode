package com.alipay.mobile.android.verify.sdk.b;

import android.app.Activity;
import android.text.TextUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.mobile.android.verify.bridge.BusProvider;
import com.alipay.mobile.android.verify.bridge.protocol.BridgeEvent;
import com.alipay.mobile.android.verify.bridge.protocol.IPlugin;
import com.alipay.mobile.android.verify.logger.Logger;
import com.alipay.mobile.android.verify.sdk.a.a;
import com.alipay.mobile.security.zim.api.ZIMFacade;
import com.alipay.mobile.security.zim.api.ZIMFacadeBuilder;
import com.squareup.otto.Subscribe;
import java.util.HashMap;
import java.util.Map;

/* compiled from: ZolozPlugin */
public class d implements IPlugin {
    private final Activity a;

    public d(Activity activity) {
        this.a = activity;
        ZIMFacade.install(activity);
    }

    @Subscribe
    public void handle(BridgeEvent bridgeEvent) {
        if (bridgeEvent == null || TextUtils.isEmpty(bridgeEvent.action) || TextUtils.isEmpty(bridgeEvent.id)) {
            Logger.t("ZolozPlugin").i("null or empty action", new Object[0]);
        } else if ("startZVerify".equalsIgnoreCase(bridgeEvent.action)) {
            BridgeEvent cloneAsResponse = BridgeEvent.cloneAsResponse(bridgeEvent);
            cloneAsResponse.data = BridgeEvent.response();
            String string = bridgeEvent.data != null ? bridgeEvent.data.getString("ZIMId") : "";
            a.a(string);
            Logger.t("ZolozPlugin").i("handle start z verify event", new Object[0]);
            Map<String, String> a2 = a(bridgeEvent.data != null ? bridgeEvent.data.getJSONObject("params") : null);
            if (TextUtils.isEmpty(string)) {
                cloneAsResponse.data.put((String) "success", (Object) Boolean.FALSE);
                cloneAsResponse.data.put((String) "errorMessage", (Object) "缺少必要参数");
                BusProvider.getInstance().post(cloneAsResponse);
                return;
            }
            try {
                ZIMFacade create = ZIMFacadeBuilder.create(this.a.getApplicationContext());
                a.b("startZolozSDK");
                create.verify(string, a2, new e(this, cloneAsResponse));
            } catch (Exception e) {
                Logger.t("ZolozPlugin").e(e, "start verify got error", new Object[0]);
                cloneAsResponse.data.put((String) "success", (Object) Boolean.FALSE);
                cloneAsResponse.data.put((String) "errorMessage", (Object) "未知的异常");
                BusProvider.getInstance().post(cloneAsResponse);
            }
        } else {
            if ("getMetaInfo".equalsIgnoreCase(bridgeEvent.action)) {
                BridgeEvent cloneAsResponse2 = BridgeEvent.cloneAsResponse(bridgeEvent);
                cloneAsResponse2.data = BridgeEvent.response();
                String metaInfos = ZIMFacade.getMetaInfos(this.a);
                if (TextUtils.isEmpty(metaInfos)) {
                    cloneAsResponse2.data.put((String) "success", (Object) Boolean.FALSE);
                    BusProvider.getInstance().post(cloneAsResponse2);
                    return;
                }
                try {
                    cloneAsResponse2.data.put((String) "metaInfo", (Object) JSON.parseObject(metaInfos));
                    BusProvider.getInstance().post(cloneAsResponse2);
                } catch (Exception e2) {
                    Logger.t("ZolozPlugin").e(e2, "parse object error", new Object[0]);
                    cloneAsResponse2.data.put((String) "success", (Object) Boolean.FALSE);
                    BusProvider.getInstance().post(cloneAsResponse2);
                }
            }
        }
    }

    private Map<String, String> a(JSONObject jSONObject) {
        HashMap hashMap = new HashMap();
        if (jSONObject != null) {
            for (String next : jSONObject.keySet()) {
                hashMap.put(next, jSONObject.getString(next));
            }
        }
        return hashMap;
    }
}
