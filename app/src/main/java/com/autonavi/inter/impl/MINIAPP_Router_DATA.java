package com.autonavi.inter.impl;

import com.autonavi.miniapp.plugin.router.MiniAppRouter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class MINIAPP_Router_DATA extends HashMap<String, List<Class>> {
    public MINIAPP_Router_DATA() {
        doPut("applets", MiniAppRouter.class);
    }

    private void doPut(String str, Class cls) {
        if (!containsKey(str)) {
            put(str, new ArrayList());
        }
        ((List) get(str)).add(cls);
    }
}
