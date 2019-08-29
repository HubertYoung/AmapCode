package com.autonavi.inter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class STATISTICS_MultipleImpl_DATA extends HashMap<Class, List<Class>> {
    public STATISTICS_MultipleImpl_DATA() {
        doPut(xx.class, afd.class);
        doPut(xw.class, afc.class);
        doPut(xy.class, afe.class);
    }

    private void doPut(Class cls, Class cls2) {
        if (!containsKey(cls)) {
            put(cls, new ArrayList());
        }
        ((List) get(cls)).add(cls2);
    }
}
