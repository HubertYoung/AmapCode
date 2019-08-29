package com.autonavi.jni.ae.routeplan;

import com.autonavi.jni.ae.routeplan.interfaces.ICalcRouteListener;
import com.autonavi.jni.ae.routeplan.model.MiniAppConfig;
import com.autonavi.jni.ae.routeplan.model.RoutePlanWayPoint;

public class MiniAppRouteService {
    private ICalcRouteListener mCalcRouteListener;
    private long mPtr;
    private MiniAppConfig miniConfig;

    public native void abortCalcRoute(int i);

    public native void calcRoute(int i, RoutePlanWayPoint routePlanWayPoint);

    public final native void destroy();

    public final native void init(int i, String str);

    public void setCalcRouteListener(ICalcRouteListener iCalcRouteListener) {
        this.mCalcRouteListener = iCalcRouteListener;
    }

    public void init(int i, MiniAppConfig miniAppConfig) {
        if (miniAppConfig != null) {
            init(i, miniAppConfig.host);
        }
    }
}
