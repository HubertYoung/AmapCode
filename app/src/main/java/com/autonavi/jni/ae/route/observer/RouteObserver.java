package com.autonavi.jni.ae.route.observer;

import com.autonavi.jni.ae.route.route.CalcRouteResult;

public interface RouteObserver {
    void onNewRoute(int i, CalcRouteResult calcRouteResult, Object obj, boolean z);

    void onNewRouteError(int i, int i2, Object obj, boolean z);
}
