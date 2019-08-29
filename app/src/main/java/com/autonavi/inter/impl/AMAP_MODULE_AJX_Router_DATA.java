package com.autonavi.inter.impl;

import com.autonavi.minimap.ajx3.scheme.Ajx3Router;
import com.autonavi.minimap.ajx3.scheme.Ajx3SchemeFinalRouter;
import com.autonavi.minimap.ajx3.scheme.Ajx3SchemePrepareRouter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class AMAP_MODULE_AJX_Router_DATA extends HashMap<String, List<Class>> {
    public AMAP_MODULE_AJX_Router_DATA() {
        doPut("ajx", Ajx3Router.class);
        doPut("ajx_smallbridge", Ajx3Router.class);
        doPut("ajx-activity", Ajx3Router.class);
        doPut("ajx_final_scheme", Ajx3SchemeFinalRouter.class);
        doPut("ajx_prepare_scheme", Ajx3SchemePrepareRouter.class);
    }

    private void doPut(String str, Class cls) {
        if (!containsKey(str)) {
            put(str, new ArrayList());
        }
        ((List) get(str)).add(cls);
    }
}
