package com.autonavi.minimap.offline.util;

import java.util.Iterator;
import java.util.List;

public final class JsCommonUtils {
    private JsCommonUtils() {
    }

    public static <T> List<T> cleanNullData(List<T> list) {
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            if (it.next() == null) {
                it.remove();
            }
        }
        return list;
    }
}
