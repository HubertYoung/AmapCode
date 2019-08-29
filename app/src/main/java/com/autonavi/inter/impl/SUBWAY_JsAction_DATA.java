package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.JsActionLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@JsActionLogger(actions = {"openSubway", "searchRouteDetail", "openSubwaySearch"}, jsActions = {"com.autonavi.minimap.route.subway.OpenSubWayPageAction", "com.autonavi.minimap.route.subway.OpenRouteBusDetailAction", "com.autonavi.minimap.route.subway.jsaction.OpenSubwaySearchAction"}, module = "subway")
@KeepName
public final class SUBWAY_JsAction_DATA extends HashMap<String, Class<?>> {
    public SUBWAY_JsAction_DATA() {
        put("openSubway", eid.class);
        put("searchRouteDetail", eic.class);
        put("openSubwaySearch", eih.class);
    }
}
