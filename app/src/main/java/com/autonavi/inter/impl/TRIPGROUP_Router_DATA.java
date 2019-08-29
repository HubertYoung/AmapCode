package com.autonavi.inter.impl;

import com.autonavi.minimap.intent.BaseIntentDispatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class TRIPGROUP_Router_DATA extends HashMap<String, List<Class>> {
    public TRIPGROUP_Router_DATA() {
        doPut("dialect", dfn.class);
        doPut("searchSpecPoi", dig.class);
        doPut(BaseIntentDispatcher.HOST_OPENFEATURE, dec.class);
        doPut("schoolbus", dec.class);
        doPut("drive", dec.class);
        doPut("carRestrict", dec.class);
        doPut("truckRestrict", dec.class);
        doPut("jamrank", djx.class);
    }

    private void doPut(String str, Class cls) {
        if (!containsKey(str)) {
            put(str, new ArrayList());
        }
        ((List) get(str)).add(cls);
    }
}
