package com.autonavi.inter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class LIFE_Router_DATA extends HashMap<String, List<Class>> {
    public LIFE_Router_DATA() {
        doPut("scenicSpotOrder", dqh.class);
        doPut("hotelOrder", dpw.class);
    }

    private void doPut(String str, Class cls) {
        if (!containsKey(str)) {
            put(str, new ArrayList());
        }
        ((List) get(str)).add(cls);
    }
}
