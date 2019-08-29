package com.autonavi.inter.impl;

import java.util.HashMap;
import java.util.Map;
import proguard.annotation.KeepName;

@KeepName
public final class LIFE_MainMapInvokePriority_DATA extends HashMap<String, Map<Class<?>, Float>> {
    public LIFE_MainMapInvokePriority_DATA() {
        put("onCreate", new HashMap());
        ((Map) get("onCreate")).put(dqr.class, Float.valueOf(5.0f));
    }
}
