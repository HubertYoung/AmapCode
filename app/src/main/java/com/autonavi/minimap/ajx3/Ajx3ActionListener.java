package com.autonavi.minimap.ajx3;

import android.text.TextUtils;
import com.alipay.mobile.nebula.permission.H5PermissionManager;
import com.alipay.sdk.authjs.a;
import com.autonavi.minimap.ajx3.upgrade.Ajx3ActionLogUtil;
import com.autonavi.minimap.ajx3.upgrade.Ajx3UpgradeManager;
import com.autonavi.minimap.ajx3.util.AjxDebugUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class Ajx3ActionListener implements AjxActionListener {
    public void dispatchMessage(String str, String str2) {
        if (!TextUtils.isEmpty(str2) && !"coverage_test".equals(str)) {
            if ("onContextCreate".equals(str)) {
                try {
                    JSONObject jSONObject = new JSONObject(str2);
                    long optLong = jSONObject.optLong("shadow", -1);
                    String optString = jSONObject.optString(a.d, "");
                    String optString2 = jSONObject.optString("bundleDependence", "");
                    if (!TextUtils.isEmpty(optString) && optLong >= 0) {
                        Ajx3UpgradeManager.getInstance().onContextCreate(optLong, optString, optString2);
                    }
                } catch (JSONException unused) {
                }
            } else if ("onContextDestroy".equals(str)) {
                try {
                    long optLong2 = new JSONObject(str2).optLong("shadow", -1);
                    if (optLong2 >= 0) {
                        Ajx3UpgradeManager.getInstance().onContextDestroy(optLong2);
                    }
                } catch (JSONException unused2) {
                }
            } else if ("onLineLog".equals(str)) {
                try {
                    JSONObject jSONObject2 = new JSONObject(str2);
                    Ajx3ActionLogUtil.actionLogCommon(jSONObject2.optString("url", ""), jSONObject2.optString("type", ""), jSONObject2.optString("btnId", ""), jSONObject2.optString(H5PermissionManager.level, ""), jSONObject2.optString("msg", ""));
                } catch (Exception unused3) {
                }
            } else if ("onEngineInitialized".equals(str)) {
                AjxInit.onEngineInitialized();
            } else if ("onReShowBlueBall".equals(str)) {
                if (AjxDebugUtils.isMenuShown()) {
                    AjxDebugUtils.hideMenu();
                }
                AjxDebugUtils.showMenu();
            }
        }
    }
}
