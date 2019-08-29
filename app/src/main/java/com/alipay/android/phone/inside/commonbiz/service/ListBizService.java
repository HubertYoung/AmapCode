package com.alipay.android.phone.inside.commonbiz.service;

import android.os.Bundle;
import com.alipay.android.phone.inside.framework.plugin.PluginManager;
import com.alipay.android.phone.inside.framework.service.AbstractInsideService;
import com.alipay.android.phone.inside.framework.service.ServiceExecutor;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONArray;
import org.json.JSONObject;

public class ListBizService extends AbstractInsideService<Bundle, Bundle> {
    private static String a() {
        JSONArray jSONArray = new JSONArray();
        try {
            boolean z = false;
            if ((PluginManager.b("BARCODE_PLUGIN_CHECK_CODE") != null) && ((Boolean) ServiceExecutor.b("BARCODE_PLUGIN_CHECK_STATUS", new Bundle())).booleanValue()) {
                jSONArray.put(b());
            }
            if (PluginManager.b("BUS_CODE_PLUGIN_STATUS") != null) {
                z = true;
            }
            if (z && ((Boolean) ServiceExecutor.b("BUS_CODE_PLUGIN_STATUS", new Bundle())).booleanValue()) {
                jSONArray.put(c());
            }
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return jSONArray.toString();
    }

    private static JSONObject b() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", "付款码");
            jSONObject.put("isOpen", "true");
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return jSONObject;
    }

    private static JSONObject c() {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("name", "公交码");
            jSONObject.put("isOpen", "true");
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return jSONObject;
    }

    public /* synthetic */ Object startForResult(Object obj) throws Exception {
        Bundle bundle = new Bundle();
        bundle.putString("bizList", a());
        return bundle;
    }
}
