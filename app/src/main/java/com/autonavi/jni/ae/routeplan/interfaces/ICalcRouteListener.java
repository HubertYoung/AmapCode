package com.autonavi.jni.ae.routeplan.interfaces;

public interface ICalcRouteListener {
    void onRouteError(int i, int i2);

    void onRouteSucceed(int i, String str);
}
