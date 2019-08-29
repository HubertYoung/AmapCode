package com.autonavi.inter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class ROUTECOMMON_MultipleImpl_DATA extends HashMap<Class, List<Class>> {
    public ROUTECOMMON_MultipleImpl_DATA() {
        doPut(xw.class, bux.class);
        doPut(xy.class, buz.class);
        doPut(xx.class, buy.class);
        doPut(ema.class, eib.class);
        doPut(xy.class, dzz.class);
        doPut(xx.class, dzy.class);
        doPut(xw.class, dzx.class);
    }

    private void doPut(Class cls, Class cls2) {
        if (!containsKey(cls)) {
            put(cls, new ArrayList());
        }
        ((List) get(cls)).add(cls2);
    }
}
