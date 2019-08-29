package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.minimap.basemap.traffic.TrafficAffectOverlayManager;
import com.autonavi.minimap.map.ITrafficOverlayItem;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.basemap.traffic.TrafficAffectOverlayManager", "com.autonavi.minimap.basemap.traffic.TrafficOverlayItemImpl", "com.autonavi.minimap.basemap.traffic.net.ReleatedTrafficEventPresenterImpl"}, inters = {"com.autonavi.map.core.ITrafficAffectOverlayManager", "com.autonavi.minimap.map.ITrafficOverlayItem", "com.autonavi.minimap.basemap.traffic.IReleatedTrafficEventPresenter"}, module = "trafficevent")
@KeepName
public final class TRAFFICEVENT_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public TRAFFICEVENT_ServiceImpl_DATA() {
        put(brp.class, TrafficAffectOverlayManager.class);
        put(ITrafficOverlayItem.class, csg.class);
        put(cry.class, csm.class);
    }
}
