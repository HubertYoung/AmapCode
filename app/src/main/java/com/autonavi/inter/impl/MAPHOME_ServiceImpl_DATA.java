package com.autonavi.inter.impl;

import com.autonavi.annotation.helper.ServiceImplLogger;
import com.autonavi.map.fragmentcontainer.page.mappage.TipsView.IGeoCodeChecker;
import java.util.HashMap;
import proguard.annotation.KeepName;

@ServiceImplLogger(impls = {"com.autonavi.minimap.bundle.maphome.suspend.GpsManagerImpl", "com.autonavi.minimap.bundle.maphome.suspend.map.MapManagerImpl", "com.autonavi.minimap.bundle.maphome.impl.ReverseGeocodeManagerImpl", "com.autonavi.minimap.bundle.maphome.MapHomeService", "com.autonavi.minimap.bundle.maphome.suspend.SuspendManagerImpl", "com.autonavi.minimap.bundle.maphome.suspend.GuideManagerImpl", "com.autonavi.minimap.bundle.maphome.suspend.GpsMapControllerImpl", "com.autonavi.minimap.bundle.maphome.suspend.SuspendEventControllerImpl", "com.autonavi.minimap.bundle.maphome.suspend.GPSBtnControllerImpl", "com.autonavi.minimap.bundle.maphome.tipsview.GeoCodeCheckerImpl", "com.autonavi.minimap.basemap.maphome.AppStartLogWrapper", "com.autonavi.minimap.bundle.maphome.suspend.SuspendWidgetHelperImpl", "com.autonavi.minimap.bundle.maphome.suspend.FloorManagerImpl"}, inters = {"com.autonavi.map.suspend.refactor.gps.IGpsManager", "com.autonavi.map.core.IMapManager", "com.autonavi.bundle.maphome.api.reverse.IReverseGeocodeManager", "com.autonavi.minimap.bundle.maphome.api.IMapHomeService", "com.autonavi.map.suspend.refactor.ISuspendManager", "com.autonavi.map.suspend.refactor.guide.IGuideManager", "com.autonavi.map.suspend.refactor.gps.IGpsMapController", "com.autonavi.map.suspend.refactor.ISuspendEventController", "com.autonavi.map.suspend.refactor.gps.IGPSBtnController", "com.autonavi.map.fragmentcontainer.page.mappage.TipsView.IGeoCodeChecker", "com.autonavi.minimap.basemap.maphome.IAppStartLog", "com.autonavi.map.suspend.manager.ISuspendWidgetHelper", "com.autonavi.map.suspend.refactor.floor.IFloorManager"}, module = "maphome")
@KeepName
public final class MAPHOME_ServiceImpl_DATA extends HashMap<Class, Class<?>> {
    public MAPHOME_ServiceImpl_DATA() {
        put(cef.class, daf.class);
        put(bro.class, dal.class);
        put(awe.class, cyy.class);
        put(cyx.class, cyu.class);
        put(cdb.class, daj.class);
        put(cek.class, dah.class);
        put(ceg.class, dag.class);
        put(cda.class, dai.class);
        put(cec.class, dae.class);
        put(IGeoCodeChecker.class, dao.class);
        put(cqb.class, cqa.class);
        put(ccu.class, dak.class);
        put(cdq.class, dad.class);
    }
}
