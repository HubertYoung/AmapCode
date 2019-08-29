package defpackage;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

/* renamed from: asj reason: default package */
/* compiled from: AlipayBusCardUtil */
public final class asj {
    static asj a;

    static String a(Map<String, String> map, String str) {
        Object obj;
        ArrayList arrayList = new ArrayList(map.keySet());
        Collections.sort(arrayList);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayList.size() - 1; i++) {
            String str2 = (String) arrayList.get(i);
            sb.append(a(str2, map.get(str2), false));
            sb.append("&");
        }
        String str3 = (String) arrayList.get(arrayList.size() - 1);
        sb.append(a(str3, map.get(str3), false));
        try {
            obj = URLEncoder.encode(asn.a(sb.toString(), str), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            obj = "";
        }
        return "sign=".concat(String.valueOf(obj));
    }

    static String a(Map<String, String> map) {
        ArrayList arrayList = new ArrayList(map.keySet());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arrayList.size() - 1; i++) {
            String str = (String) arrayList.get(i);
            sb.append(a(str, map.get(str), true));
            sb.append("&");
        }
        String str2 = (String) arrayList.get(arrayList.size() - 1);
        sb.append(a(str2, map.get(str2), true));
        return sb.toString();
    }

    private static String a(String str, String str2, boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("=");
        if (z) {
            try {
                sb.append(URLEncoder.encode(str2, "UTF-8"));
            } catch (UnsupportedEncodingException unused) {
                sb.append(str2);
            }
        } else {
            sb.append(str2);
        }
        return sb.toString();
    }
}
