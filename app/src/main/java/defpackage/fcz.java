package defpackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* renamed from: fcz reason: default package */
/* compiled from: HeaderHandlerUtil */
public final class fcz {
    private static List<String> b(Map<String, List<String>> map, String str) {
        if (map == null || map.isEmpty() || fdd.b(str)) {
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
        List<String> b = b(map, str);
        if (b == null || b.isEmpty()) {
            return null;
        }
        return b.get(0);
    }

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
}
