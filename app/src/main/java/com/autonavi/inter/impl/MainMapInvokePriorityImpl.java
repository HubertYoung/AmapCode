package com.autonavi.inter.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import proguard.annotation.Keep;

@Keep
public final class MainMapInvokePriorityImpl {
    private static final Map<String, Map<Class<?>, Float>> sPriorityMap = new HashMap();

    static {
        doPut(new AMAPHOME_MainMapInvokePriority_DATA());
        doPut(new LIFE_MainMapInvokePriority_DATA());
        doPut(new MAPHOME_MainMapInvokePriority_DATA());
        doPut(new UITEMPLATE_MainMapInvokePriority_DATA());
    }

    public static Map<String, Map<Class<?>, Float>> getPriorityMap() {
        return sPriorityMap;
    }

    private static void doPut(HashMap<String, Map<Class<?>, Float>> hashMap) {
        for (Entry next : hashMap.entrySet()) {
            String str = (String) next.getKey();
            if (!sPriorityMap.containsKey(str)) {
                sPriorityMap.put(str, new HashMap());
            }
            sPriorityMap.get(str).putAll((Map) next.getValue());
        }
    }
}
