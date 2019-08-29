package com.alipay.deviceid.module.x;

import android.content.Context;
import java.util.HashMap;
import org.json.JSONObject;

public final class az {
    public static String a(Context context, String str, String str2) {
        if (context == null || e.a(str) || e.a(str2)) {
            return null;
        }
        try {
            String a = v.a(context, str, str2, "");
            if (e.a(a)) {
                return null;
            }
            return i.b(i.a(), a);
        } catch (Exception unused) {
            return null;
        }
    }

    public static String a(String str, String str2) {
        if (e.a(str) || e.a(str2)) {
            return null;
        }
        try {
            String a = t.a(str);
            if (e.a(a)) {
                return null;
            }
            String string = new JSONObject(a).getString(str2);
            if (e.a(string)) {
                return null;
            }
            return i.b(i.a(), string);
        } catch (Exception unused) {
            return null;
        }
    }

    public static void a(Context context, String str, String str2, String str3) {
        if (!e.a(str) && !e.a(str2) && context != null) {
            try {
                String a = i.a(i.a(), str3);
                HashMap hashMap = new HashMap();
                hashMap.put(str2, a);
                v.a(context, str, hashMap);
            } catch (Exception unused) {
            }
        }
    }
}
