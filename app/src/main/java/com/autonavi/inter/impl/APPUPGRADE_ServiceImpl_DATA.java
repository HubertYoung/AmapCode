package com.autonavi.inter.impl;

import com.amap.bundle.appupgrade.impl.AppUpdateManagerImpl;
import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.minimap.basemap.inter.IAppUpDateManager;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.amap.bundle.appupgrade.impl.AppUpdateManagerImpl"}, inters = {"com.autonavi.minimap.basemap.inter.IAppUpDateManager"}, module = "appupgrade")
@KeepName
public final class APPUPGRADE_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public APPUPGRADE_ServiceImpl_DATA() {
        put(IAppUpDateManager.class, AppUpdateManagerImpl.class);
    }
}
