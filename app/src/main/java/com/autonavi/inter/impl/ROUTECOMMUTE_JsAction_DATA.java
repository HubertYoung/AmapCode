package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"getRouteCommuteType"}, jsActions = {"com.autonavi.bundle.routecommute.common.GetRouteCommuteTypeAction"}, module = "routecommute")
@KeepName
public final class ROUTECOMMUTE_JsAction_DATA extends HashMap<String, Class<?>> {
    public ROUTECOMMUTE_JsAction_DATA() {
        put("getRouteCommuteType", aze.class);
    }
}
