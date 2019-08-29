package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.bundle.routeplan.RouteSaveServiceImpl"}, inters = {"com.autonavi.map.interf.IRouteSaveService"}, module = "routeplan")
@KeepName
public final class ROUTEPLAN_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public ROUTEPLAN_ServiceImpl_DATA() {
        put(btr.class, baw.class);
    }
}
