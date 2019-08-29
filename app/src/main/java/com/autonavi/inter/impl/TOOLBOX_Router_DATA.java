package com.autonavi.inter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class TOOLBOX_Router_DATA extends HashMap<String, List<Class>> {
    public TOOLBOX_Router_DATA() {
        doPut("measure", cgx.class);
        doPut("driveachievement", cgw.class);
        doPut("toolbox", cgy.class);
    }

    private void doPut(String str, Class cls) {
        if (!containsKey(str)) {
            put(str, new ArrayList());
        }
        ((List) get(str)).add(cls);
    }
}
