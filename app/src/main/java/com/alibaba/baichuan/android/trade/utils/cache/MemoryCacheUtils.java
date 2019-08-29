package com.alibaba.baichuan.android.trade.utils.cache;

import java.util.HashMap;
import java.util.Map;

public class MemoryCacheUtils {
    private static String a = "group1";
    private static Map b = new HashMap();

    public static Object getGlobalProperity(String str) {
        if (str == null) {
            return null;
        }
        return getProperityValue(a, str, null);
    }

    public static Object getGlobalProperity(String str, Object obj) {
        if (str == null) {
            return null;
        }
        return getProperityValue(a, str, obj);
    }

    public static Object getGroupProperity(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        return getProperityValue(str, str2, null);
    }

    public static Object getGroupProperity(String str, String str2, Object obj) {
        if (str == null || str2 == null) {
            return null;
        }
        return getProperityValue(str, str2, obj);
    }

    public static Object getProperityValue(String str, String str2, Object obj) {
        Map map = (Map) b.get(str);
        return (map == null || map.get(str2) == null) ? obj : map.get(str2);
    }

    public static boolean setGlobalProperity(String str, Object obj) {
        if (str == null || obj == null) {
            return false;
        }
        setProperityValue(a, str, obj);
        return true;
    }

    public static boolean setGroupProperity(String str, String str2, Object obj) {
        if (str == null || str2 == null || obj == null) {
            return false;
        }
        setProperityValue(str, str2, obj);
        return true;
    }

    public static void setProperityValue(String str, String str2, Object obj) {
        Map map = (Map) b.get(str);
        if (map == null) {
            map = new HashMap();
            b.put(str, map);
        }
        map.put(str2, obj);
    }
}
