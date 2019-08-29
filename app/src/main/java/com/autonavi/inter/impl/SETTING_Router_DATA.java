package com.autonavi.inter.impl;

import com.alipay.sdk.sys.a;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class SETTING_Router_DATA extends HashMap<String, List<Class>> {
    public SETTING_Router_DATA() {
        doPut(a.j, bda.class);
        doPut("settings", bda.class);
    }

    private void doPut(String str, Class cls) {
        if (!containsKey(str)) {
            put(str, new ArrayList());
        }
        ((List) get(str)).add(cls);
    }
}
