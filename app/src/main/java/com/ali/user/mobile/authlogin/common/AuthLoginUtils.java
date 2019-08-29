package com.ali.user.mobile.authlogin.common;

import android.text.TextUtils;
import com.ali.user.mobile.log.AliUserLog;
import com.alipay.android.phone.inside.common.setting.InsideSetting;
import com.autonavi.common.SuperId;
import java.util.Map;
import java.util.Map.Entry;

public class AuthLoginUtils {
    public static String a() {
        if (!InsideSetting.b()) {
            return "authlogin-inside-offline-android-aes128";
        }
        AliUserLog.c("AuthLoginUtils", "current state is online");
        return "authlogin_inside_android_aes128";
    }

    public static String b() {
        if (!InsideSetting.b()) {
            return SuperId.BIT_1_NEARBY_SEARCH;
        }
        AliUserLog.c("AuthLoginUtils", "current state is online");
        return "a";
    }

    public static String a(Map<String, String> map) {
        return a(null, map);
    }

    public static String a(String str, Map<String, String> map) {
        String str2;
        if (map == null || map.size() <= 0) {
            str2 = null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (Entry next : map.entrySet()) {
                sb.append((String) next.getKey());
                sb.append("=");
                sb.append((String) next.getValue());
                sb.append("&");
            }
            str2 = sb.substring(0, sb.lastIndexOf("&"));
        }
        if (!TextUtils.isEmpty(str)) {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(str);
            sb2.append("&");
            sb2.append(str2);
            str2 = sb2.toString();
        }
        AliUserLog.c("AuthLoginUtils", str2);
        return str2;
    }
}
