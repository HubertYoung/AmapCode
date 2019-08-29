package com.autonavi.inter.impl;

import com.autonavi.minimap.ajx3.AjxRegister;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class AMAP_MODULE_AJX_MultipleImpl_DATA extends HashMap<Class, List<Class>> {
    public AMAP_MODULE_AJX_MultipleImpl_DATA() {
        doPut(ema.class, AjxRegister.class);
    }

    private void doPut(Class cls, Class cls2) {
        if (!containsKey(cls)) {
            put(cls, new ArrayList());
        }
        ((List) get(cls)).add(cls2);
    }
}
