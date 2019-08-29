package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.minimap.bundle.splashscreen.api.ISplashManager;
import com.autonavi.minimap.manager.SplashManager;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.manager.SplashManager"}, inters = {"com.autonavi.minimap.bundle.splashscreen.api.ISplashManager"}, module = "splashscreen")
@KeepName
public final class SPLASHSCREEN_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public SPLASHSCREEN_ServiceImpl_DATA() {
        put(ISplashManager.class, SplashManager.class);
    }
}
