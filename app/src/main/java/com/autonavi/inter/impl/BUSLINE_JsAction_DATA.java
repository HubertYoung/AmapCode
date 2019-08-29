package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"openBusLine"}, jsActions = {"com.autonavi.minimap.jsaction.OpenBusLineAction"}, module = "busline")
@KeepName
public final class BUSLINE_JsAction_DATA extends HashMap<String, Class<?>> {
    public BUSLINE_JsAction_DATA() {
        put("openBusLine", dmw.class);
    }
}
