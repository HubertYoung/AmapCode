package com.alipay.android.phone.inside.config.plugin.service;

import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.commonbiz.ids.StaticConfig;
import com.alipay.android.phone.inside.commonservice.CommonServiceFactory;
import com.alipay.android.phone.inside.config.rpc.ClientSwitchRpcFacade;
import com.alipay.android.phone.inside.config.rpc.model.SwitchInfoReqPbPB;
import com.alipay.android.phone.inside.config.rpc.model.SwitchInfoRespPbPB;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import com.alipay.android.phone.inside.log.api.trace.TraceLogger;
import com.alipay.android.phone.inside.storage.pref.EncryptPrefUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DynamicConfigLoadService extends AbstractInsideService<Bundle, Bundle> {
    private long a = -1;

    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        Bundle bundle = (Bundle) obj;
        String string = bundle.getString("configName", "");
        if (TextUtils.isEmpty(string)) {
            throw new Exception("configName empty");
        }
        String a2 = a(string, bundle.getString("defaultValue", ""));
        if (System.currentTimeMillis() - this.a < 300000) {
            LoggerFactory.f().b((String) "inside", (String) "DynamicConfigLoadService::syncDynamicConfig > unmatch time.");
        } else {
            this.a = System.currentTimeMillis();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        LoggerFactory.f().b((String) "inside", (String) "DynamicConfigLoadService::syncDynamicConfig > req start.");
                        SwitchInfoReqPbPB switchInfoReqPbPB = new SwitchInfoReqPbPB();
                        switchInfoReqPbPB.productId = StaticConfig.d();
                        switchInfoReqPbPB.clientVersion = StaticConfig.c();
                        switchInfoReqPbPB.channelId = StaticConfig.e();
                        switchInfoReqPbPB.systemType = "android";
                        switchInfoReqPbPB.mobileBrand = Build.BRAND;
                        switchInfoReqPbPB.mobileModel = Build.MODEL;
                        switchInfoReqPbPB.osVersion = VERSION.RELEASE;
                        switchInfoReqPbPB.utdid = RunningConfig.f();
                        SwitchInfoRespPbPB switchesPbInside = ((ClientSwitchRpcFacade) CommonServiceFactory.getInstance().getRpcService().getRpcProxy(ClientSwitchRpcFacade.class)).getSwitchesPbInside(switchInfoReqPbPB);
                        LoggerFactory.f().b((String) "inside", (String) "DynamicConfigLoadService::syncDynamicConfig > req end.");
                        JSONObject jSONObject = new JSONObject();
                        JSONArray jSONArray = new JSONArray();
                        for (int i = 0; i < switchesPbInside.switches.size(); i++) {
                            jSONArray.put(switchesPbInside.switches.get(i).format());
                        }
                        jSONObject.put("switches", jSONArray);
                        EncryptPrefUtil.a("alipay_inside_dynamic_config_file", "alipay_inside_dynamic_config_name", jSONObject.toString());
                        LoggerFactory.f().b((String) "inside", (String) "DynamicConfigLoadService::syncDynamicConfig > save end.");
                    } catch (Throwable th) {
                        LoggerFactory.e().a((String) "config", (String) "SyncDynamicConfigEx", th);
                    }
                }
            }).start();
        }
        Bundle bundle2 = new Bundle();
        bundle2.putString("configValue", a2);
        return bundle2;
    }

    private static String a(String str, String str2) throws JSONException {
        TraceLogger f = LoggerFactory.f();
        StringBuilder sb = new StringBuilder("DynamicConfigLoadService::loadDynamicConfig > configName=");
        sb.append(str);
        sb.append(", defaultValue=");
        sb.append(str2);
        f.b((String) "inside", sb.toString());
        String b = EncryptPrefUtil.b("alipay_inside_dynamic_config_file", "alipay_inside_dynamic_config_name", "");
        LoggerFactory.f().b((String) "inside", "DynamicConfigLoadService::loadDynamicConfig > configValue=".concat(String.valueOf(b)));
        if (TextUtils.isEmpty(b)) {
            return str2;
        }
        JSONArray optJSONArray = new JSONObject(b).optJSONArray("switches");
        if (optJSONArray == null) {
            return str2;
        }
        int i = 0;
        while (true) {
            if (i >= optJSONArray.length()) {
                break;
            }
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (TextUtils.equals(str, optJSONObject.optString("key"))) {
                str2 = optJSONObject.optString("value");
                break;
            }
            i++;
        }
        return str2;
    }
}
