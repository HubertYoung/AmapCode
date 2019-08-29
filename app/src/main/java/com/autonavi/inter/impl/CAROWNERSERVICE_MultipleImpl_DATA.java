package com.autonavi.inter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class CAROWNERSERVICE_MultipleImpl_DATA extends HashMap<Class, List<Class>> {
    public CAROWNERSERVICE_MultipleImpl_DATA() {
        doPut(xw.class, cpr.class);
        doPut(xy.class, cpt.class);
        doPut(xx.class, cps.class);
        doPut(xx.class, brf.class);
        doPut(xy.class, brg.class);
        doPut(xw.class, bre.class);
    }

    private void doPut(Class cls, Class cls2) {
        if (!containsKey(cls)) {
            put(cls, new ArrayList());
        }
        ((List) get(cls)).add(cls2);
    }
}
