package com.alipay.deviceid.module.x;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import java.util.Map;

public final class v {
    public static String a(Context context, String str, String str2, String str3) {
        return context.getSharedPreferences(str, 0).getString(str2, str3);
    }

    public static void a(Context context, String str, Map<String, String> map) {
        Editor edit = context.getSharedPreferences(str, 0).edit();
        if (edit != null) {
            for (String next : map.keySet()) {
                edit.putString(next, map.get(next));
            }
            edit.commit();
        }
    }
}
