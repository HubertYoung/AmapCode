package com.autonavi.inter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class MSGBOX_MultipleImpl_DATA extends HashMap<Class, List<Class>> {
    public MSGBOX_MultipleImpl_DATA() {
        doPut(xx.class, buf.class);
        doPut(xy.class, bug.class);
        doPut(xw.class, bue.class);
    }

    private void doPut(Class cls, Class cls2) {
        if (!containsKey(cls)) {
            put(cls, new ArrayList());
        }
        ((List) get(cls)).add(cls2);
    }
}
