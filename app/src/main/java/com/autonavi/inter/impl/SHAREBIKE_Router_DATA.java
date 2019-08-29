package com.autonavi.inter.impl;

import com.autonavi.bundle.sharebike.ajx.ModuleBicycle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class SHAREBIKE_Router_DATA extends HashMap<String, List<Class>> {
    public SHAREBIKE_Router_DATA() {
        doPut(ModuleBicycle.MODULE_NAME, ega.class);
    }

    private void doPut(String str, Class cls) {
        if (!containsKey(str)) {
            put(str, new ArrayList());
        }
        ((List) get(str)).add(cls);
    }
}
