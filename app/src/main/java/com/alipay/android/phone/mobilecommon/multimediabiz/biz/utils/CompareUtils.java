package com.alipay.android.phone.mobilecommon.multimediabiz.biz.utils;

import java.util.List;

public class CompareUtils {
    public static boolean strInIgnoreCase(String src, String... array) {
        if (!(src == null || array == null || array.length <= 0)) {
            for (String equalsIgnoreCase : array) {
                if (src.equalsIgnoreCase(equalsIgnoreCase)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean in(Object src, Object... array) {
        return arrayContains(src, array);
    }

    public static boolean arrayContains(Object src, Object[] array) {
        if (!(src == null || array == null || array.length <= 0)) {
            for (Object equals : array) {
                if (src.equals(equals)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean equals(Object a, Object b) {
        return (a == null && b == null) || (a != null && a.equals(b)) || (b != null && b.equals(a));
    }

    public static boolean hasBit(int bits, int bit) {
        return (bits & bit) == bit;
    }

    public static boolean containsIgnoreCase(String str, List<String> list) {
        if (str == null || list == null || list.isEmpty()) {
            return false;
        }
        for (String item : list) {
            if (str.equalsIgnoreCase(item)) {
                return true;
            }
        }
        return false;
    }
}
