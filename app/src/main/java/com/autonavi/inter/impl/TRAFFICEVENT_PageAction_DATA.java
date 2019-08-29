package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.PageActionLogger;
import com.autonavi.minimap.basemap.traffic.page.TrafficMapPage;
import java.util.HashMap;
import proguard.annotation.KeepName;

@PageActionLogger(actions = {"page_action_trafficmap"}, module = "trafficevent", pages = {"com.autonavi.minimap.basemap.traffic.page.TrafficMapPage"})
@KeepName
public final class TRAFFICEVENT_PageAction_DATA extends HashMap<String, Class<?>> {
    public TRAFFICEVENT_PageAction_DATA() {
        put("page_action_trafficmap", TrafficMapPage.class);
    }
}
