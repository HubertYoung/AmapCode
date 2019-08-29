package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.map.core.IOverlayManager;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.amap.bundle.maplayer.OverlayManagerImpl"}, inters = {"com.autonavi.map.core.IOverlayManager"}, module = "mapbase")
@KeepName
public final class MAPBASE_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public MAPBASE_ServiceImpl_DATA() {
        put(IOverlayManager.class, xn.class);
    }
}
