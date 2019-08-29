package com.autonavi.inter.impl;

import com.autonavi.minimap.ajx3.modules.ModuleNavi;
import com.autonavi.minimap.intent.BaseIntentDispatcher;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class DRIVE_Router_DATA extends HashMap<String, List<Class>> {
    public DRIVE_Router_DATA() {
        doPut("showTraffic", mn.class);
        doPut(ModuleNavi.MODULE_NAME, mn.class);
        doPut("navi2SpecialDest", mn.class);
        doPut("keywordNavi", mn.class);
        doPut(BaseIntentDispatcher.HOST_OPENFEATURE, mn.class);
        doPut("drive", mn.class);
        doPut("edog", mn.class);
    }

    private void doPut(String str, Class cls) {
        if (!containsKey(str)) {
            put(str, new ArrayList());
        }
        ((List) get(str)).add(cls);
    }
}
