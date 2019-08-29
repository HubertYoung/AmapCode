package com.alipay.android.phone.mobilesdk.permission.utils;

import java.util.Collection;
import java.util.Map;

/* compiled from: StringUtil */
public final class h {
    public static boolean a(CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static <K, V> String a(Map<K, V> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        return a((Collection<T>) map.entrySet());
    }

    private static <T> String a(Collection<T> collection) {
        if (collection == null || collection.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder("[size=" + collection.size() + ": ");
        for (Object t : collection) {
            if (t != null) {
                builder.append(t.toString()).append(",");
            }
        }
        int index = builder.lastIndexOf(",");
        if (-1 != index) {
            builder.deleteCharAt(index);
        }
        builder.append("]");
        return builder.toString();
    }

    public static <T> String a(T[] array) {
        if (array == null || array.length <= 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder("[size=" + array.length + ": ");
        for (Object t : array) {
            if (t != null) {
                builder.append(t.toString()).append(",");
            }
        }
        int index = builder.lastIndexOf(",");
        if (-1 != index) {
            builder.deleteCharAt(index);
        }
        builder.append("]");
        return builder.toString();
    }

    public static <T> String a(T[] array, String separator) {
        if (array == null || array.length <= 0) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (Object t : array) {
            if (t != null) {
                builder.append(t.toString()).append(separator);
            }
        }
        int index = builder.lastIndexOf(separator);
        if (-1 != index) {
            builder.delete(index, builder.length());
        }
        return builder.toString();
    }
}
