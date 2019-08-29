package com.autonavi.minimap.ajx3.core;

import java.util.ArrayList;
import proguard.annotation.Keep;
import proguard.annotation.KeepClassMembers;

@Keep
@KeepClassMembers
public class GloabalStringPool {
    private static ArrayList<String> sGloabalStringPool = new ArrayList<>();

    private static int putToStringPool(String str) {
        sGloabalStringPool.add(str);
        return sGloabalStringPool.size() - 1;
    }

    private static String getFromStringPool(int i) {
        if (i < 0 || i >= sGloabalStringPool.size()) {
            return null;
        }
        return sGloabalStringPool.get(i);
    }
}
