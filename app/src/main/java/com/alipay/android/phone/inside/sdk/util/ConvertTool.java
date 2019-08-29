package com.alipay.android.phone.inside.sdk.util;

import android.os.Bundle;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class ConvertTool {
    public static String convertBundleToJsonStr(Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return "";
        }
        try {
            JSONObject jSONObject = new JSONObject();
            for (String str : bundle.keySet()) {
                jSONObject.put(str, bundle.get(str));
            }
            return jSONObject.toString();
        } catch (Throwable th) {
            LoggerFactory.f().c(ConvertTool.class.getName(), th);
            return "";
        }
    }
}
