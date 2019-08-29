package defpackage;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import mtopsdk.common.util.TBSdkLog;

/* renamed from: ffu reason: default package */
/* compiled from: NetworkConverterUtils */
public final class ffu {
    public static URL a(String str, Map<String, String> map) {
        URL url;
        if (fdd.b(str)) {
            TBSdkLog.d("mtopsdk.NetworkConverterUtils", "[initUrl]baseUrl is blank,initUrl error");
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder(str);
            if (map != null) {
                String a = a(map, (String) "utf-8");
                if (fdd.a(a) && str.indexOf("?") == -1) {
                    sb.append("?");
                    sb.append(a);
                }
            }
            url = new URL(sb.toString());
        } catch (Exception e) {
            TBSdkLog.b((String) "mtopsdk.NetworkConverterUtils", (String) "[initUrl] build fullUrl error", (Throwable) e);
            url = null;
        }
        return url;
    }

    public static String a(Map<String, String> map, String str) {
        if (map == null) {
            return null;
        }
        if (fdd.b(str)) {
            str = "utf-8";
        }
        StringBuilder sb = new StringBuilder(64);
        Iterator<Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Entry next = it.next();
            try {
                String encode = next.getKey() != null ? URLEncoder.encode((String) next.getKey(), str) : null;
                Object encode2 = next.getValue() != null ? URLEncoder.encode((String) next.getValue(), str) : null;
                sb.append(encode);
                sb.append("=");
                sb.append(encode2);
                if (it.hasNext()) {
                    sb.append("&");
                }
            } catch (Throwable th) {
                StringBuilder sb2 = new StringBuilder("[createParamQueryStr]getQueryStr error ---");
                sb2.append(th.toString());
                TBSdkLog.d("mtopsdk.NetworkConverterUtils", sb2.toString());
            }
        }
        return sb.toString();
    }
}
