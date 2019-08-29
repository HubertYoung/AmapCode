package com.autonavi.inter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class TRIPGROUP_MultipleImpl_DATA extends HashMap<Class, List<Class>> {
    public TRIPGROUP_MultipleImpl_DATA() {
        doPut(awa.class, deb.class);
        doPut(ema.class, diu.class);
    }

    private void doPut(Class cls, Class cls2) {
        if (!containsKey(cls)) {
            put(cls, new ArrayList());
        }
        ((List) get(cls)).add(cls2);
    }
}
