package defpackage;

import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: cq reason: default package */
/* compiled from: HttpHelper */
public final class cq {
    public static Map<String, List<String>> a(Map<String, List<String>> map) {
        if (map == null) {
            return null;
        }
        if (map.isEmpty()) {
            return Collections.EMPTY_MAP;
        }
        HashMap hashMap = new HashMap(map.size());
        for (Entry next : map.entrySet()) {
            hashMap.put(next.getKey(), new ArrayList((Collection) next.getValue()));
        }
        return hashMap;
    }

    private static List<String> c(Map<String, List<String>> map, String str) {
        if (map == null || map.isEmpty() || TextUtils.isEmpty(str)) {
            return null;
        }
        for (Entry next : map.entrySet()) {
            if (str.equalsIgnoreCase((String) next.getKey())) {
                return (List) next.getValue();
            }
        }
        return null;
    }

    public static String a(Map<String, List<String>> map, String str) {
        List<String> c = c(map, str);
        if (c == null || c.isEmpty()) {
            return null;
        }
        return c.get(0);
    }

    public static void b(Map<String, List<String>> map, String str) {
        Iterator<String> it = map.keySet().iterator();
        while (true) {
            if (it.hasNext()) {
                if (str.equalsIgnoreCase(it.next())) {
                    break;
                }
            } else {
                str = null;
                break;
            }
        }
        if (str != null) {
            map.remove(str);
        }
    }

    public static int b(Map<String, List<String>> map) {
        try {
            return Integer.parseInt(a(map, (String) "Content-Length"));
        } catch (Exception unused) {
            return 0;
        }
    }

    public static long c(Map<String, List<String>> map) {
        try {
            List list = map.get("s-rt");
            if (list != null && !list.isEmpty()) {
                return Long.parseLong((String) list.get(0));
            }
        } catch (NumberFormatException unused) {
        }
        return 0;
    }

    public static int d(Map<String, List<String>> map) {
        try {
            List list = map.get(":status");
            if (list != null && !list.isEmpty()) {
                return Integer.parseInt((String) list.get(0));
            }
        } catch (NumberFormatException unused) {
        }
        return 0;
    }

    public static String a(String str) {
        String str2;
        if (str == null) {
            return null;
        }
        try {
            int length = str.length();
            if (length <= 1) {
                return null;
            }
            int lastIndexOf = str.lastIndexOf(47);
            if (lastIndexOf != -1) {
                if (lastIndexOf != length - 1) {
                    int lastIndexOf2 = str.lastIndexOf(46);
                    if (lastIndexOf2 != -1) {
                        if (lastIndexOf2 > lastIndexOf) {
                            str2 = str.substring(lastIndexOf2 + 1, length);
                            return str2;
                        }
                    }
                    return null;
                }
            }
            return null;
        } catch (Exception unused) {
            str2 = null;
        }
    }

    public static boolean a(ay ayVar, int i) {
        return ayVar.d && i >= 300 && i < 400 && i != 304 && ayVar.f < 10;
    }
}
