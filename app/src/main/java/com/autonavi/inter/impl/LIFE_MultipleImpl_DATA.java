package com.autonavi.inter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class LIFE_MultipleImpl_DATA extends HashMap<Class, List<Class>> {
    public LIFE_MultipleImpl_DATA() {
        doPut(xy.class, dou.class);
        doPut(xw.class, dos.class);
        doPut(xx.class, dot.class);
    }

    private void doPut(Class cls, Class cls2) {
        if (!containsKey(cls)) {
            put(cls, new ArrayList());
        }
        ((List) get(cls)).add(cls2);
    }
}
