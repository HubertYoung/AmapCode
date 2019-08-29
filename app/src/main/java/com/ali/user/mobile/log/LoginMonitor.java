package com.ali.user.mobile.log;

import com.alipay.android.phone.inside.common.info.DeviceInfo;
import com.alipay.android.phone.inside.log.api.LoggerFactory;
import java.util.HashMap;
import java.util.Map;

public class LoginMonitor {
    public static HashMap<String, Long> a = new HashMap<>();
    public static HashMap<String, String> b = new HashMap<>();
    public static HashMap<String, TimeConsumingLogAgent> c = new HashMap<>();

    public static String a(String str) {
        if (b.containsKey(str)) {
            return b.get(str);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("_");
        sb.append(DeviceInfo.a().r());
        sb.append("_");
        sb.append(System.currentTimeMillis());
        String sb2 = sb.toString();
        b.put(str, sb2);
        return sb2;
    }

    public static void b(String str) {
        b.remove(str);
    }

    public static void a() {
        a.clear();
        c.clear();
        b.clear();
    }

    public static void a(String str, String str2, String str3, Map<String, String> map) {
        String[] strArr = null;
        if (map != null) {
            try {
                if (!map.isEmpty()) {
                    strArr = new String[]{str3, map.toString()};
                }
            } catch (Throwable th) {
                LoggerFactory.f().b("mtBizReport", "error", th);
                return;
            }
        }
        LoggerFactory.e().a(str, str2, strArr);
    }
}
