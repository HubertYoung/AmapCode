package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager;
import com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService;
import com.autonavi.bundle.uitemplate.mapwidget.impl.MapWidgetManagerImpl;
import com.autonavi.bundle.uitemplate.mapwidget.impl.MapWidgetManagerService;
import com.autonavi.minimap.ajx3.scheme.IRedesignPageLoader;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.bundle.uitemplate.page.RedesignAjxPageLoader", "com.autonavi.bundle.uitemplate.indoor.RedesignFloorWidgetService", "com.autonavi.bundle.uitemplate.mapwidget.impl.MapWidgetManagerImpl", "com.autonavi.bundle.uitemplate.mapwidget.impl.MapWidgetManagerService"}, inters = {"com.autonavi.minimap.ajx3.scheme.IRedesignPageLoader", "com.autonavi.bundle.uitemplate.indoor.IRedesignFloorWidgetService", "com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManager", "com.autonavi.bundle.uitemplate.mapwidget.IMapWidgetManagerService"}, module = "uitemplate")
@KeepName
public final class UITEMPLATE_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public UITEMPLATE_ServiceImpl_DATA() {
        put(IRedesignPageLoader.class, bem.class);
        put(bec.class, bej.class);
        put(IMapWidgetManager.class, MapWidgetManagerImpl.class);
        put(IMapWidgetManagerService.class, MapWidgetManagerService.class);
    }
}
