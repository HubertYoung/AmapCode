package defpackage;

import java.util.List;
import java.util.Map;

/* renamed from: bps reason: default package */
/* compiled from: HeaderParser */
public final class bps {
    public static String a(Map<String, List<String>> map, String str) {
        if (map == null) {
            return str;
        }
        List list = map.get("Content-Type");
        String str2 = null;
        if (list != null) {
            str2 = (String) list.get(list.size() - 1);
        }
        if (str2 != null) {
            String[] split = str2.split(";");
            for (int i = 1; i < split.length; i++) {
                String[] split2 = split[i].trim().split("=");
                if (split2.length == 2 && split2[0].equals("charset")) {
                    return split2[1];
                }
            }
        }
        return str;
    }

    public static String b(Map<String, List<String>> map, String str) {
        String str2 = null;
        if (map == null) {
            return null;
        }
        List list = map.get(str);
        if (list != null) {
            str2 = (String) list.get(list.size() - 1);
        }
        return str2;
    }
}
