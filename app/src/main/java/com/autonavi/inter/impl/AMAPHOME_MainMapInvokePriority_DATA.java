package com.autonavi.inter.impl;

import java.util.HashMap;
import java.util.Map;
import proguard.annotation.KeepName;

@KeepName
public final class AMAPHOME_MainMapInvokePriority_DATA extends HashMap<String, Map<Class<?>, Float>> {
    public AMAPHOME_MainMapInvokePriority_DATA() {
        put("onResume", new HashMap());
        ((Map) get("onResume")).put(apx.class, Float.valueOf(1.0f));
    }
}
