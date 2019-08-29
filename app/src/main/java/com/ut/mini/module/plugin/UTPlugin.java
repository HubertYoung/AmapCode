package com.ut.mini.module.plugin;

import java.util.Map;

public abstract class UTPlugin {
    public abstract int[] getAttentionEventIds();

    public Map<String, String> onEventDispatch(String str, int i, String str2, String str3, String str4) {
        return null;
    }

    public Map<String, String> onEventDispatch(String str, int i, String str2, String str3, String str4, Map<String, String> map) {
        return onEventDispatch(str, i, str2, str3, str4);
    }

    public static boolean isEventIDInRange(int[] iArr, int i) {
        if (iArr != null) {
            if (iArr[0] == -1) {
                return true;
            }
            for (int i2 : iArr) {
                if (i2 == i) {
                    return true;
                }
            }
        }
        return false;
    }
}
