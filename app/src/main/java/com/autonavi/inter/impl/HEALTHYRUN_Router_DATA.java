package com.autonavi.inter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class HEALTHYRUN_Router_DATA extends HashMap<String, List<Class>> {
    public HEALTHYRUN_Router_DATA() {
        doPut("healthyRun", eey.class);
    }

    private void doPut(String str, Class cls) {
        if (!containsKey(str)) {
            put(str, new ArrayList());
        }
        ((List) get(str)).add(cls);
    }
}
