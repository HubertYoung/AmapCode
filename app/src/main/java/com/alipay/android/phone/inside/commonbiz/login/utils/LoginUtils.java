package com.alipay.android.phone.inside.commonbiz.login.utils;

import android.os.Bundle;
import android.text.TextUtils;
import com.alipay.android.phone.inside.commonbiz.ids.OutsideConfig;
import com.alipay.android.phone.inside.commonbiz.ids.RunningConfig;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class LoginUtils {
    public static JSONObject a() {
        boolean p = OutsideConfig.p();
        String a = OutsideConfig.a();
        String b = OutsideConfig.b();
        boolean q = OutsideConfig.q();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("isOpenAuthLogin", p);
            jSONObject.put("authToken", a);
            jSONObject.put("alipayUserId", b);
            jSONObject.put("isThirdPartyApp", q);
        } catch (Throwable th) {
            LoggerFactory.f().c((String) "inside", th);
        }
        return jSONObject;
    }

    public static Bundle b() {
        Bundle bundle = new Bundle();
        bundle.putString("insideLoginType", "normalLogin");
        return bundle;
    }

    public static Bundle c() {
        Bundle bundle = new Bundle();
        bundle.putString("insideLoginType", "withoutPwd");
        return bundle;
    }

    public static boolean a(JSONObject jSONObject) {
        return TextUtils.equals(jSONObject.optString("isThirdPartyApp", ""), "true");
    }

    public static Bundle b(JSONObject jSONObject) {
        Bundle bundle = new Bundle();
        try {
            bundle.putString("openAuthToken", jSONObject.optString("authToken"));
            bundle.putString("openAuthUserId", jSONObject.optString("alipayUserId"));
            bundle.putString("insideLoginType", "openAuthTokenLogin");
            bundle.putString("openMcUid", jSONObject.optString("openMcUid"));
            bundle.putString("openMcUid", jSONObject.optString("openMcUid"));
            bundle.putBoolean("isNewOpenAuthFlow", jSONObject.optBoolean("isNewOpenAuthFlow"));
        } catch (Throwable th) {
            LoggerFactory.f().b((String) "inside", th);
        }
        return bundle;
    }

    public static String c(JSONObject jSONObject) {
        return jSONObject.optString("alipayUserId", "");
    }

    public static String d() {
        return RunningConfig.e();
    }

    public static boolean d(JSONObject jSONObject) {
        return TextUtils.equals(jSONObject.optString("isOpenAuthLogin", ""), "true");
    }
}
