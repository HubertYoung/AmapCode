package com.autonavi.inter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class SEARCHCOMMON_MultipleImpl_DATA extends HashMap<Class, List<Class>> {
    public SEARCHCOMMON_MultipleImpl_DATA() {
        doPut(xw.class, bsi.class);
        doPut(xx.class, bsj.class);
        doPut(xy.class, bsk.class);
    }

    private void doPut(Class cls, Class cls2) {
        if (!containsKey(cls)) {
            put(cls, new ArrayList());
        }
        ((List) get(cls)).add(cls2);
    }
}
