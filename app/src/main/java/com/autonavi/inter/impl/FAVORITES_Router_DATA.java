package com.autonavi.inter.impl;

import com.alipay.mobile.h5container.api.H5Param;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class FAVORITES_Router_DATA extends HashMap<String, List<Class>> {
    public FAVORITES_Router_DATA() {
        doPut(H5Param.MENU_FAVORITES, cxo.class);
    }

    private void doPut(String str, Class cls) {
        if (!containsKey(str)) {
            put(str, new ArrayList());
        }
        ((List) get(str)).add(cls);
    }
}
