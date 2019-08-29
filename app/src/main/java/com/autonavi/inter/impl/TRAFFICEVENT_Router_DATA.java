package com.autonavi.inter.impl;

import com.autonavi.bundle.trafficevent.TrafficEventRouter;
import com.autonavi.bundle.trafficevent.TrafficEventRouter.TrafficEventRouterDef;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import proguard.annotation.KeepName;

@KeepName
public final class TRAFFICEVENT_Router_DATA extends HashMap<String, List<Class>> {
    public TRAFFICEVENT_Router_DATA() {
        doPut(TrafficEventRouterDef.TRAFFIC_EVENT_HOST, TrafficEventRouter.class);
        doPut("feedback", TrafficEventRouter.class);
    }

    private void doPut(String str, Class cls) {
        if (!containsKey(str)) {
            put(str, new ArrayList());
        }
        ((List) get(str)).add(cls);
    }
}
