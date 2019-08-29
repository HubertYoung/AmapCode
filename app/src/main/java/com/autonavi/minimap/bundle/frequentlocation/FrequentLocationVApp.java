package com.autonavi.minimap.bundle.frequentlocation;

import com.autonavi.bundle.routecommon.model.RouteType;
import com.autonavi.common.model.POI;
import com.autonavi.jni.bedstone.model.FrequentLocationDBInfo;
import com.autonavi.minimap.ajx3.Ajx;
import com.autonavi.minimap.bundle.frequentlocation.ajx.ModuleFrequentLocation;

public class FrequentLocationVApp extends esh {
    public boolean isRegisterLifeCycle() {
        return true;
    }

    public void vAppCreate() {
        super.vAppCreate();
        chk.a = new a() {
            public final void a(POI poi, RouteType routeType) {
                FrequentLocationDBInfo frequentLocationDBInfo = new FrequentLocationDBInfo();
                frequentLocationDBInfo.trafficType = FrequentLocationVApp.b(routeType);
                frequentLocationDBInfo.poiid = poi.getId();
                frequentLocationDBInfo.name = poi.getName();
                frequentLocationDBInfo.x = poi.getPoint().x;
                frequentLocationDBInfo.y = poi.getPoint().y;
                frequentLocationDBInfo.FrequentLocation.copyFromPoi(poi);
                cyc.e().a(frequentLocationDBInfo);
            }
        };
        Ajx.getInstance().registerModule(ModuleFrequentLocation.class);
    }

    /* access modifiers changed from: private */
    public static int b(RouteType routeType) {
        try {
            if (routeType == RouteType.CAR) {
                return Integer.parseInt("302");
            }
            if (routeType == RouteType.BUS) {
                return Integer.parseInt("303");
            }
            if (routeType == RouteType.ONFOOT) {
                return Integer.parseInt("304");
            }
            if (routeType == RouteType.RIDE) {
                return Integer.parseInt("305");
            }
            if (routeType == RouteType.TRAIN) {
                return Integer.parseInt("306");
            }
            if (routeType == RouteType.TRUCK) {
                return Integer.parseInt("307");
            }
            if (routeType == RouteType.ETRIP) {
                return Integer.parseInt("308");
            }
            return Integer.parseInt("302");
        } catch (NumberFormatException unused) {
            return 0;
        }
    }
}
