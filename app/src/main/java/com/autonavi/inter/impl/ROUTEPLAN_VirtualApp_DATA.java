package com.autonavi.inter.impl;

import com.autonavi.minimap.route.bundle.RouteVApp;
import java.util.ArrayList;
import proguard.annotation.KeepName;

@KeepName
public final class ROUTEPLAN_VirtualApp_DATA extends ArrayList<Class<?>> {
    public ROUTEPLAN_VirtualApp_DATA() {
        add(RouteVApp.class);
    }
}
