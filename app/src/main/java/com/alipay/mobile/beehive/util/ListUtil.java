package com.alipay.mobile.beehive.util;

import java.util.List;

public class ListUtil {
    public static boolean isEmpty(List<?> list) {
        return list == null || list.size() <= 0;
    }
}
