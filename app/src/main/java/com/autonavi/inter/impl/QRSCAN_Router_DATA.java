package com.autonavi.inter.impl;

import com.autonavi.minimap.bundle.qrscan.ajx.ModuleQRScan;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class QRSCAN_Router_DATA extends HashMap<String, List<Class>> {
    public QRSCAN_Router_DATA() {
        doPut(ModuleQRScan.MODULE_NAME, dbx.class);
    }

    private void doPut(String str, Class cls) {
        if (!containsKey(str)) {
            put(str, new ArrayList());
        }
        ((List) get(str)).add(cls);
    }
}
