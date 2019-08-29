package com.alipay.android.phone.inside.main.action.utils;

import android.os.Bundle;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import org.json.JSONObject;

public class CommonUtil {
    public static String a(Bundle bundle) {
        if (bundle == null) {
            return "";
        }
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            try {
                jSONObject.put(str, bundle.get(str));
            } catch (Throwable th) {
                LoggerFactory.f().c((String) "OAuth_CommonUtil", th);
            }
        }
        return jSONObject.toString();
    }
}
