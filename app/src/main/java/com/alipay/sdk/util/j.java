package com.alipay.sdk.util;

import com.alipay.sdk.app.statistic.a;
import com.alipay.sdk.app.statistic.c;
import java.util.HashMap;
import java.util.Map;

public final class j {
    public static final String a = "resultStatus";
    public static final String b = "memo";
    public static final String c = "result";

    private static Map<String, String> a() {
        com.alipay.sdk.app.j a2 = com.alipay.sdk.app.j.a(com.alipay.sdk.app.j.CANCELED.h);
        HashMap hashMap = new HashMap();
        hashMap.put(a, Integer.toString(a2.h));
        hashMap.put("memo", a2.i);
        hashMap.put("result", "");
        return hashMap;
    }

    private static Map<String, String> b(String str) {
        String[] split = str.split(";");
        HashMap hashMap = new HashMap();
        for (String str2 : split) {
            String substring = str2.substring(0, str2.indexOf("={"));
            StringBuilder sb = new StringBuilder();
            sb.append(substring);
            sb.append("={");
            String sb2 = sb.toString();
            hashMap.put(substring, str2.substring(str2.indexOf(sb2) + sb2.length(), str2.lastIndexOf(h.d)));
        }
        return hashMap;
    }

    private static String a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        sb.append("={");
        String sb2 = sb.toString();
        return str.substring(str.indexOf(sb2) + sb2.length(), str.lastIndexOf(h.d));
    }

    public static Map<String, String> a(String str) {
        com.alipay.sdk.app.j a2 = com.alipay.sdk.app.j.a(com.alipay.sdk.app.j.CANCELED.h);
        HashMap hashMap = new HashMap();
        hashMap.put(a, Integer.toString(a2.h));
        hashMap.put("memo", a2.i);
        hashMap.put("result", "");
        try {
            return b(str);
        } catch (Throwable th) {
            a.a((String) c.b, (String) c.g, th);
            return hashMap;
        }
    }
}
