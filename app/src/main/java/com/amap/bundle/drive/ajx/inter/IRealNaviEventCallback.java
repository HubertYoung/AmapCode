package com.amap.bundle.drive.ajx.inter;

public interface IRealNaviEventCallback {
    boolean isRealDayNightMode();

    void onCalRoute(String str);

    void onMainPathIdUpdate(long j);

    void onSpeakerChanged(int i);

    void onTravelPointsUpdate(String str);
}
