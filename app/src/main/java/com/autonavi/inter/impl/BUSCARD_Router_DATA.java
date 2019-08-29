package com.autonavi.inter.impl;

import com.autonavi.bundle.buscard.module.ModuleBusCard;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class BUSCARD_Router_DATA extends HashMap<String, List<Class>> {
    public BUSCARD_Router_DATA() {
        doPut(ModuleBusCard.MODULE_NAME, ast.class);
    }

    private void doPut(String str, Class cls) {
        if (!containsKey(str)) {
            put(str, new ArrayList());
        }
        ((List) get(str)).add(cls);
    }
}
