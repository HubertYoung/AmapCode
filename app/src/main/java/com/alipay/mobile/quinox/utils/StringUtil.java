package com.alipay.mobile.quinox.utils;

import com.alipay.mobile.quinox.log.Log;
import java.util.Collection;
import java.util.Map;

public final class StringUtil {
    private StringUtil() {
    }

    public static boolean equals(CharSequence charSequence, CharSequence charSequence2) {
        if (charSequence == charSequence2) {
            return true;
        }
        if (!(charSequence == null || charSequence2 == null)) {
            int length = charSequence.length();
            if (length == charSequence2.length()) {
                if ((charSequence instanceof String) && (charSequence2 instanceof String)) {
                    return charSequence.equals(charSequence2);
                }
                for (int i = 0; i < length; i++) {
                    if (charSequence.charAt(i) != charSequence2.charAt(i)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static <K, V> String map2String(Map<K, V> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        return collection2String(map.entrySet());
    }

    public static <T> String collection2String(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("[size=");
        sb.append(collection.size());
        sb.append(": ");
        StringBuilder sb2 = new StringBuilder(sb.toString());
        for (T next : collection) {
            if (next != null) {
                sb2.append(next.toString());
                sb2.append(",");
            }
        }
        int lastIndexOf = sb2.lastIndexOf(",");
        if (-1 != lastIndexOf) {
            sb2.deleteCharAt(lastIndexOf);
        }
        sb2.append("]");
        return sb2.toString();
    }

    public static <T> String array2String(T[] tArr) {
        if (tArr == null || tArr.length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder("[size=");
        sb.append(tArr.length);
        sb.append(": ");
        StringBuilder sb2 = new StringBuilder(sb.toString());
        for (T t : tArr) {
            if (t != null) {
                sb2.append(t.toString());
                sb2.append(",");
            }
        }
        int lastIndexOf = sb2.lastIndexOf(",");
        if (-1 != lastIndexOf) {
            sb2.deleteCharAt(lastIndexOf);
        }
        sb2.append("]");
        return sb2.toString();
    }

    public static <T> String join(T[] tArr, String str) {
        if (tArr == null || tArr.length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (T t : tArr) {
            if (t != null) {
                sb.append(t.toString());
                sb.append(str);
            }
        }
        int lastIndexOf = sb.lastIndexOf(str);
        if (-1 != lastIndexOf) {
            sb.delete(lastIndexOf, sb.length());
        }
        return sb.toString();
    }

    public static <T> String join(Collection<T> collection, String str) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (T next : collection) {
            if (next != null) {
                sb.append(next.toString());
                sb.append(str);
            }
        }
        int lastIndexOf = sb.lastIndexOf(str);
        if (-1 != lastIndexOf) {
            sb.delete(lastIndexOf, sb.length());
        }
        return sb.toString();
    }

    public static boolean compareVersion(String str, String str2) {
        try {
            String[] split = str.split("\\.");
            String[] split2 = str2.split("\\.");
            int min = Math.min(split.length, split2.length);
            for (int i = 0; i < min; i++) {
                int i2 = (Long.parseLong(split[i]) > Long.parseLong(split2[i]) ? 1 : (Long.parseLong(split[i]) == Long.parseLong(split2[i]) ? 0 : -1));
                if (i2 > 0) {
                    return true;
                }
                if (i2 < 0) {
                    return false;
                }
            }
            if (min == split2.length) {
                int length = split.length;
                while (min < length) {
                    int i3 = (Long.parseLong(split[min]) > 0 ? 1 : (Long.parseLong(split[min]) == 0 ? 0 : -1));
                    if (i3 > 0) {
                        return true;
                    }
                    if (i3 < 0) {
                        return false;
                    }
                    min++;
                }
            }
        } catch (Throwable th) {
            StringBuilder sb = new StringBuilder("compareVersion(");
            sb.append(str);
            sb.append(", ");
            sb.append(str2);
            sb.append(")");
            Log.w("compareVersion", sb.toString(), th);
        }
        return false;
    }
}
