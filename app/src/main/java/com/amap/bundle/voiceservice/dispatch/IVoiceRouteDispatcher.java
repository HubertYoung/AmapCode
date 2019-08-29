package com.amap.bundle.voiceservice.dispatch;

import proguard.annotation.KeepClassMembers;
import proguard.annotation.KeepImplementations;
import proguard.annotation.KeepName;

@KeepClassMembers
@KeepName
@KeepImplementations
public interface IVoiceRouteDispatcher extends bie {
    void addMidPois(int i, String str);

    void exitNavi(int i, String str);

    void requestRouteFootNavi(int i, String str);

    void requestRoutePlan(int i, String str);

    void requestRouteRideNavi(int i, String str);

    void searchBusLine(int i, String str);

    void searchSubwayLine(int i, String str);

    void swapStartEndPoi(int i, String str);

    void switchRouteInWalk(int i, String str);

    void switchRouteWay(int i, String str);
}
