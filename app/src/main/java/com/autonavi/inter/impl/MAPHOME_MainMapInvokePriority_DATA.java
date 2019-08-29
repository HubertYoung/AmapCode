package com.autonavi.inter.impl;

import java.util.HashMap;
import java.util.Map;
import proguard.annotation.KeepName;

@KeepName
public final class MAPHOME_MainMapInvokePriority_DATA extends HashMap<String, Map<Class<?>, Float>> {
    public MAPHOME_MainMapInvokePriority_DATA() {
        put("onCreate", new HashMap());
        ((Map) get("onCreate")).put(dkz.class, Float.valueOf(4.0f));
        put("onDestroy", new HashMap());
        ((Map) get("onDestroy")).put(dkz.class, Float.valueOf(4.0f));
        put("onResume", new HashMap());
        ((Map) get("onResume")).put(dky.class, Float.valueOf(1.0f));
    }
}
