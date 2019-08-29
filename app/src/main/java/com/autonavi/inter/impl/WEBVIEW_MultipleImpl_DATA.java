package com.autonavi.inter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class WEBVIEW_MultipleImpl_DATA extends HashMap<Class, List<Class>> {
    public WEBVIEW_MultipleImpl_DATA() {
        doPut(xw.class, cfx.class);
        doPut(xx.class, cfy.class);
        doPut(xy.class, cfz.class);
    }

    private void doPut(Class cls, Class cls2) {
        if (!containsKey(cls)) {
            put(cls, new ArrayList());
        }
        ((List) get(cls)).add(cls2);
    }
}
