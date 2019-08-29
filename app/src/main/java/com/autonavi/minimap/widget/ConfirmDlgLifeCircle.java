package com.autonavi.minimap.widget;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConfirmDlgLifeCircle {
    private static Map<Integer, Object> dialogPools = new LinkedHashMap();

    public static void addPool(int i, Object obj) {
        dialogPools.put(Integer.valueOf(i), obj);
    }

    public static void popPool(int i, Object obj) {
        if (dialogPools.containsKey(Integer.valueOf(i))) {
            dialogPools.remove(Integer.valueOf(i));
        }
    }

    public static void removeAll() {
        for (Object next : dialogPools.values()) {
            if (next instanceof ConfirmDlg) {
                ((ConfirmDlg) next).dismiss();
            }
        }
    }
}
