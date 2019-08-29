package com.alipay.mobile.security.bio.utils;

import com.autonavi.miniapp.plugin.map.route.MiniAppShowRouteConfigHelper;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class StringUtil {
    public static final int INT_20 = 20;

    public static boolean isNullorEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static int getInt(String str, int i) {
        if (isNullorEmpty(str)) {
            return 0;
        }
        String str2 = "";
        for (int i2 = 0; i2 < str.length() && str2.length() < i; i2++) {
            char charAt = str.charAt(i2);
            if (Character.isDigit(charAt)) {
                str2 = str2 + charAt;
            }
        }
        if (!isNullorEmpty(str2)) {
            return Integer.parseInt(str2);
        }
        return 0;
    }

    public static String data2String(byte[] bArr) {
        if (bArr == null || bArr.length <= 20) {
            return Arrays.toString(bArr);
        }
        StringBuilder sb = new StringBuilder(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
        sb.append('[');
        sb.append(bArr[0]);
        for (int i = 1; i < 20; i++) {
            sb.append(", ");
            sb.append(bArr[i]);
        }
        sb.append(']');
        return sb.toString();
    }

    public static String data2String(short[] sArr) {
        if (sArr == null || sArr.length <= 20) {
            return Arrays.toString(sArr);
        }
        StringBuilder sb = new StringBuilder(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
        sb.append('[');
        sb.append(sArr[0]);
        for (int i = 1; i < 20; i++) {
            sb.append(", ");
            sb.append(sArr[i]);
        }
        sb.append(']');
        return sb.toString();
    }

    public static String data2String(int[] iArr) {
        if (iArr == null || iArr.length <= 20) {
            return Arrays.toString(iArr);
        }
        StringBuilder sb = new StringBuilder(MiniAppShowRouteConfigHelper.LINE_TYPE_VIA_ROAD_LINE_FREE_);
        sb.append('[');
        sb.append(iArr[0]);
        for (int i = 1; i < 20; i++) {
            sb.append(", ");
            sb.append(iArr[i]);
        }
        sb.append(']');
        return sb.toString();
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
        StringBuilder sb = new StringBuilder("[size=" + collection.size() + ": ");
        for (T next : collection) {
            if (next != null) {
                sb.append(next.toString()).append(",");
            }
        }
        int lastIndexOf = sb.lastIndexOf(",");
        if (-1 != lastIndexOf) {
            sb.deleteCharAt(lastIndexOf);
        }
        sb.append("]");
        return sb.toString();
    }

    public static <T> String array2String(T[] tArr) {
        if (tArr == null || tArr.length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder("[size=" + tArr.length + ": ");
        for (T t : tArr) {
            if (t != null) {
                sb.append(t.toString()).append(",");
            }
        }
        int lastIndexOf = sb.lastIndexOf(",");
        if (-1 != lastIndexOf) {
            sb.deleteCharAt(lastIndexOf);
        }
        sb.append("]");
        return sb.toString();
    }

    public static <T> String join(T[] tArr, String str) {
        if (tArr == null || tArr.length <= 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (T t : tArr) {
            if (t != null) {
                sb.append(t.toString()).append(str);
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
                sb.append(next.toString()).append(str);
            }
        }
        int lastIndexOf = sb.lastIndexOf(str);
        if (-1 != lastIndexOf) {
            sb.delete(lastIndexOf, sb.length());
        }
        return sb.toString();
    }
}
