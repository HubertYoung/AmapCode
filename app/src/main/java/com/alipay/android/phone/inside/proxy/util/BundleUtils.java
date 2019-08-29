package com.alipay.android.phone.inside.proxy.util;

import android.os.Bundle;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.util.Iterator;
import org.json.JSONObject;

public class BundleUtils {
    public static Bundle a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return new Bundle();
        }
        Bundle bundle = new Bundle();
        Iterator<String> keys = jSONObject.keys();
        while (keys.hasNext()) {
            String next = keys.next();
            Object opt = jSONObject.opt(next);
            if (opt instanceof JSONObject) {
                bundle.putBundle(next, a((JSONObject) opt));
            } else {
                bundle.putString(next, opt.toString());
            }
        }
        return bundle;
    }

    public static String a(Bundle bundle) {
        JSONObject jSONObject = new JSONObject();
        if (bundle == null) {
            return jSONObject.toString();
        }
        for (String str : bundle.keySet()) {
            try {
                jSONObject.put(str, bundle.get(str));
            } catch (Throwable th) {
                LoggerFactory.f().b((String) "inside", th);
            }
        }
        return jSONObject.toString();
    }
}
