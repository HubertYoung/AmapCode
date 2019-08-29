package com.amap.bundle.drive.ajx.inter;

public interface OnRouteStateChangeListener {
    void onRouteFocusIndexChanged(int i);

    void onRouteStateChanged(int i, Object... objArr);
}
